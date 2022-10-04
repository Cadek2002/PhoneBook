import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PhoneBook {
    ArrayList<contact> ContactList;
    int contactNumber;
    final int resAmount = 5;


    //Constructors:
    PhoneBook() {
        ContactList = new ArrayList<>(0);
    } //default constructor is default
    PhoneBook(String fileName) {
        loadPhoneBook(fileName);
    }
    PhoneBook(int initialSize) {
        contactNumber = initialSize;
        ContactList = new ArrayList<>(initialSize);
    } //initializes empty contact list of specified size, not sure why this is remotely useful

    //static methods
    public static String getLine(FileReader in) {
        String line = "";
        char ch;
        try {
            ch = (char) in.read();
            while (ch != '\n' && in.ready()) {
                line += ch;
                ch = (char) in.read();
                //System.out.println(name.ready());
            }
            return line;
        }
        catch (java.io.IOException e) {
            System.out.println("File Reader IO Exception");
            return "";
        }
    }
    //NOT my code, added for testing before my final code is written
    public static double JaroWrinklerScore(final String s1, final String s2) {

        /**
         * Applies the Jaro-Winkler distance algorithm to the given strings, providing information about the
         * similarity of them.
         *
         * @param s1 The first string that gets compared. May be <code>null</node> or empty.
         * @param s2 The second string that gets compared. May be <code>null</node> or empty.
         * @return The Jaro-Winkler score (between 0.0 and 1.0), with a higher value indicating larger similarity.
         *
         * @author Thomas Trojer <thomas@trojer.net>
         */

        // lowest score on empty strings
        if (s1 == null || s2 == null || s1.isEmpty() || s2.isEmpty()) {
            return 0;
        }
        // highest score on equal strings
        if (s1.equals(s2)) {
            return 1;
        }
        // some score on different strings
        int prefixMatch = 0; // exact prefix matches
        int matches = 0; // matches (including prefix and ones requiring transpostion)
        int transpositions = 0; // matching characters that are not aligned but close together
        int maxLength = Math.max(s1.length(), s2.length());
        int maxMatchDistance = Math.max((int) Math.floor(maxLength / 2.0) - 1, 0); // look-ahead/-behind to limit transposed matches
        // comparison
        final String shorter = s1.length() < s2.length() ? s1 : s2;
        final String longer = s1.length() >= s2.length() ? s1 : s2;
        for (int i = 0; i < shorter.length(); i++) {
            // check for exact matches
            boolean match = shorter.charAt(i) == longer.charAt(i);
            if (match) {
                if (i < 4) {
                    // prefix match (of at most 4 characters, as described by the algorithm)
                    prefixMatch++;
                }
                matches++;
                continue;
            }
            // check fro transposed matches
            for (int j = Math.max(i - maxMatchDistance, 0); j < Math.min(i + maxMatchDistance, longer.length()); j++) {
                if (i == j) {
                    // case already covered
                    continue;
                }
                // transposition required to match?
                match = shorter.charAt(i) == longer.charAt(j);
                if (match) {
                    transpositions++;
                    break;
                }
            }
        }
        // any matching characters?
        if (matches == 0) {
            return 0;
        }
        // modify transpositions (according to the algorithm)
        transpositions = (int) (transpositions / 2.0);
        // non prefix-boosted score
        double score = 0.3334 * (matches / (double) longer.length() + matches / (double) shorter.length() + (matches - transpositions)
                / (double) matches);
        if (score < 0.7) {
            return score;
        }
        // we already have a good match, hence we boost the score proportional to the common prefix
        double boostedScore = score + prefixMatch * 0.1 * (1.0 - score);
        return boostedScore;
    }

    //public methods
    public void loadPhoneBook(String importFileName) {
        try {
            FileReader importFile = new FileReader(importFileName);
            contactNumber = Integer.parseInt(getLine(importFile).trim());
            ContactList = new ArrayList(contactNumber);
            while(importFile.ready()) {
                ContactList.add(new contact(getLine(importFile), getLine(importFile), getLine(importFile), getLine(importFile)));
            }
        }
        catch (FileNotFoundException e) {
            System.out.printf("Input File: %s not found.\n", importFileName);
        }

        catch (IOException e) {
            System.out.printf("Output File: %s not found.\n", importFileName);
        }
    }
    //accessors
    public contact get(int index) {
        return ContactList.get(index);
    }
    public String getName(int index) {
        return get(index).getName();
    }
    public String getNumber(int index) {
        return get(index).getNumber();
    }
    public String getAddress(int index) {
        return get(index).getAddressLine1() + "\n" + get(index).getAddressLine2();
    }


    public int getContactNumber() {
        return contactNumber;
    }
    //modifiers
    public void addContact(contact c) {
        ContactList.add(c);
    }

    //search parameters
    int[] lookUpName(String key) {
        int index = 0;
        int comparisonIndex;
        float comparison;
        int[] results = new int[resAmount];
        float[] comparisons = new float [resAmount];
        String log;

        key = key.trim();
        for (int i = 0; i < resAmount; i++) comparisons[i] = Integer.MAX_VALUE;

        while (index < contactNumber) {

            //sets comparison to the string similarity algorithim, trimming off the excess length of either the key or the current name depending on which is longer
            comparison = (float)(ContactList.get(index).getName().length() > key.length() ? JaroWrinklerScore(key, getName(index).substring(0, key.length())) : JaroWrinklerScore(key.substring(0, getName(index).length()), getName(index)));

            //optimize algorithm to only sort when need to
            if (comparison < comparisons[resAmount-1]) {
                //System.out.println("Found new comparison at Index" + index);
                comparisonIndex = resAmount-1;

                while (comparison < comparisons[comparisonIndex] && comparisonIndex > 0) comparisonIndex--;

                for (int i = resAmount-1; i > comparisonIndex; i--) {
                    //System.out.println("\tShifting Index: " + i);
                    comparisons[i] = comparisons[i-1];
                    results[i] = results[i-1];
                }
                comparisons[comparisonIndex] = comparison;
                results[comparisonIndex] = index;
            }
            index++;
        }
        for (int i = 0; i < resAmount; i++) {
            //log = String.valueOf(i) + ": " + ContactList.get(results[i]).getName());
            System.out.printf("%d %s %f \n", i, getName(results[i]), 234.234);
        }
        return results;
    }

    contact lookUpAddress() {
        return null;
    }
    contact lookUpNumber() {
        return null;
    }
    public int lookUpInterface(Scanner in) {
        int choice;
        int[] results;

        System.out.print("\tSearch Contact By:\n\t1) Name\n\t2) Address\n\t3) Phone Number\n\t4) Cancel\nInsert Option: ");

        switch (in.nextInt()) {

            case 1:
                in.nextLine();
                System.out.print("Enter Contact Name: ");
                results = lookUpName(in.nextLine());
/**
                for (int i = 0; i < resAmount; i++) {
                    System.out.printf("\t%d: %s\n", i, ContactList.get(results[i]).getName());
                }
 **/
        }
        return 0;
    }
}
