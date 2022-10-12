import java.io.*;
import java.util.*;

public class PhoneBook {
    ArrayList<contact> ContactList;
    HashMap<String, Integer> phoneNumberLookupTable;

    int contactNumber;
    final int resAmount = 5;

    //Constructors:
    PhoneBook() {
        ContactList = new ArrayList<>(0);
    }           //default constructor is default
    PhoneBook(String fileName) {
        loadPhoneBook(fileName);
    }     //the only constructor that should get used
    PhoneBook(int initialSize) {
        contactNumber = initialSize;
        ContactList = new ArrayList<>(initialSize);
        phoneNumberLookupTable = new HashMap((int)(initialSize * 1.75));
    }                            //initializes empty contact list of specified size when user is creating a new phone book from scratch

    //static methods
    public static void generatePhoneBook(String names, String addresses, String numbers, String outputFileName) {
        int n = 0;  //tracker for number of contacts
        try {
            FileReader name = new FileReader(names);        //name file
            FileReader address = new FileReader(addresses); //address file
            FileReader number = new FileReader(numbers);    //number file
            RandomAccessFile phoneBook = new RandomAccessFile(outputFileName, "rw");    //open file (read/write)

            //leave space for contact count at the beginning of file
            phoneBook.write(("          \n".getBytes()));

            //write name, address, and number to phonebook until one of the files runs out of lines, keep track of how many times this has happened
            while (name.ready() && address.ready() && number.ready()) {
                phoneBook.write((getLine(name) + "\n").getBytes());
                phoneBook.write((getLine(address) + "\n").getBytes());
                phoneBook.write((getLine(address) + "\n").getBytes());
                phoneBook.write((getLine(number) + "\n").getBytes());
                n++;
            }

            //go back to the beginning and add the contact number to the top
            phoneBook.seek(0);
            phoneBook.write(String.valueOf(n).getBytes());

            //close everything
            name.close();
            address.close();
            number.close();
            phoneBook.close();
        }
        catch (IOException e) {
            System.out.println("IO Error: " + e);
        }
    } //Merges separate input files for name, address, and number and merges them into one file
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
    } //returns a line from a FileReader
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
    } //NOT my code, added for testing before my final algo is written

    //protected int input statements
    public static int getIntProtected() {
        Scanner in = new Scanner(System.in);
        try {
            return in.nextInt();
        }
        catch (InputMismatchException e) {
            System.out.println("Invalid Input");
        }
        return -1;
    }
    public static int getIntProtected(int l, int h) {

        Scanner in = new Scanner(System.in);
        int c;
        try {
            c = in.nextInt();
            in.nextLine();
            if ( l > c || h < c)
                throw new InputMismatchException("Out of Expected Range");
            return c;
        }
        catch (InputMismatchException e) {
            System.out.println(e);
        }
        return -1;
    }

    //public methods
    public void loadPhoneBook(String importFileName) {
        try {
            int n = 0;
            FileReader importFile = new FileReader(importFileName);
            contactNumber = Integer.parseInt(getLine(importFile).trim());
            ContactList = new ArrayList(contactNumber);
            //declare the hash map to 1.75 x the number of contacts in the phone book
            phoneNumberLookupTable = new HashMap<>((int)(contactNumber * 1.75));

            //pass increments of 4 lines into the contact constructor and add that to the contact list
            while(importFile.ready()) {
                ContactList.add(new contact(getLine(importFile).trim(), getLine(importFile), getLine(importFile), getLine(importFile)));
                phoneNumberLookupTable.put(ContactList.get(n).rawNumber, n);
                n++;
            }
            //update n just in case the value at the top of the file is incorrect
            contactNumber = n;
        }
        catch (FileNotFoundException e) {
            System.out.printf("Input File: %s not found.\n", importFileName);
        }
        catch (IOException e) {
            System.out.printf("Output File: %s not found.\n", importFileName);
        }
    }
    public boolean exportPhoneBook(String exportFileName) {
        try {
            File oldFile = new File (exportFileName);
            oldFile.delete();
            FileWriter out = new FileWriter(exportFileName);
            out.write(contactNumber + "\n");
            for (int i = 0; i < contactNumber; i++) {
                out.write(getName(i).trim() + "\n");
                out.write(getAddress(i).stringRep() + "\n");
                out.write(getNumber(i).trim() + "\n");
            }
            out.close();

            return true;
        }
        catch (java.io.IOException e) {
            return false;
        }
    }

    //accessors
    public contact          get(int index) {
        return ContactList.get(index);
    }
    public String           getName(int index) {
        //System.out.println("Debug Name: " + get(index).getName());
        return get(index).getName();
    }
    public String           getNumber(int index) {
        return get(index).getNumber();
    }
    public contact.Address  getAddress(int index) {
        return get(index).getAddress();
    }
    public int              getBookSize() {
        return contactNumber;
    }

    //modifiers
    public void     addContact(contact c) {
        ContactList.add(c);
    }

    //search parameters
    int[] lookUpName(String key) {
        int index = 0;                                  //main loop iterative variable
        int comparisonIndex;                            //minor loop iterative variable
        float comparison;                               //value of current the current index's names' comparison value
        int[] results = new int[resAmount];             //tracks the ContactLists index of the top names
        float[] comparisons = new float [resAmount];    //tracks the Comparison values for the top names

        key = key.trim(); //prepare key for comparison

        while (index < contactNumber) {

            //sets comparison to the string similarity algorithm, trimming off the excess length of either the key or the current name depending on which is longer
            comparison = (float)(ContactList.get(index).getName().length() > key.length() ? JaroWrinklerScore(key, getName(index).substring(0, key.length())) : JaroWrinklerScore(key.substring(0, getName(index).length()), getName(index)));

            //if the current comparison is better than the worst result in the list find its spot and kick the worst result out
            if (comparison > comparisons[resAmount-1]) {
                comparisonIndex = 0;
                //loop through the current top results starting from the top and move down the list until a result has a lower comparison rating
                while (comparison < comparisons[comparisonIndex] && comparisonIndex < resAmount-1) comparisonIndex++;

                //shift all the lower similarity results down one space to make room for the new results
                for (int i = resAmount-1; i > comparisonIndex; i--) {
                    comparisons[i] = comparisons[i-1];
                    results[i] = results[i-1];
                }
                //insert the result (and track its index in the ArrayList)
                comparisons[comparisonIndex] = comparison;
                results[comparisonIndex] = index;
            }
            index++;
        }
        //debug printing
        /*
        System.out.println("Debug Comparisons: ");
        for (int i = 0; i < resAmount; i++) {
            System.out.printf("%d: %s %f\n", i, getName(results[i]).trim(), comparisons[i]);
        }
        */
        return results;
    }
    contact lookUpAddress() {
        return null;
    }
    contact lookUpNumber(String key) {
        //return the contact at the index specified by the hashmap when inserting the key
        return ContactList.get(phoneNumberLookupTable.get(key));
    }

    public int lookUpInterface(Scanner in) {
        int choice;
        String userInput;
        String processedInput = "";
        int[] results;
        System.out.print("\tSearch Contact By:\n\t1) Name\n\t2) Address\n\t3) Phone Number\n\t4) Cancel\nInsert Option: ");

        switch (in.nextInt()) {
            case 1 -> {
                choice = -1;
                in.nextLine();
                System.out.print("\tEnter Contact Name: ");
                results = lookUpName(in.nextLine());

                System.out.println("\tSearch Results: ");
                for (int i = 0; i < resAmount; i++) {
                    System.out.printf("\t\t%d: %s\n", i+1, getName(results[i]).trim());
                }
                do {
                    System.out.printf("\t\tEnter The index of the desired result (1-%d): ", resAmount);
                    choice = getIntProtected(0, resAmount);
                } while (choice == -1);

                get(results[choice-1]).display();
            }
            case 2 -> {

            }
            case 3 -> {
                choice = -1;
                in.nextLine();
                System.out.print("Enter Contact Number: ");
                userInput = in.nextLine();

                for (int i = userInput.length()-1; i >=0; i--) {
                    if (Character.isDigit(userInput.charAt(i))) {
                        processedInput += userInput.charAt(i);
                    }
                }
                lookUpNumber(processedInput).display();
            }
        }

        return 0;

    }
}
