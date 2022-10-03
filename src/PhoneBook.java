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
        int comparison, comparisonIndex;
        int[] results = new int[resAmount];
        int[] comparisons = new int [resAmount];

        key = key.trim();
        for (int i = 0; i < resAmount; i++) comparisons[i] = Integer.MAX_VALUE;

        while (index < contactNumber) {
            if (key.length() > ContactList.get(index).getName().length()) {
                comparison = Math.abs(ContactList.get(index).getName().compareToIgnoreCase(key.substring(0, ContactList.get(index).getName().length())));
            }
            else {
                comparison = Math.abs(ContactList.get(index).getName().substring(0, key.length()).compareToIgnoreCase(key));
            }
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
            System.out.println("Debug: ");
            System.out.println( i + ": " + ContactList.get(results[i]).getName() + "Comparison Value: " + comparisons[i]);
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
