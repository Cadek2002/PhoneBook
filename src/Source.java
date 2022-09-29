import java.util.Scanner;
import java.util.Vector;
import java.io.*;

/**
 Todo:
    1) Add 2nd Address line field to contact and update loadPhoneBook to account for this
    2) implement basic lookup function based on linear search for each field
    3) implement file updating
        b) implement insert/delete element functions
    4) Implement outer-loop for loading/generating phonebooks in program
    OPTIONAL:
    5) add 3 sets of binary/AVL trees (with each field of contacts acting as the key) for more efficient search
    6) implement multithreading to loadPhoneBook method

 **/


public class Source {
    //reads individual characters from a FileReader until it finds the newline character or the end of the file
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
    public static void generatePhoneBook(String namesFile, String addressFile, String numbersFile, String outputFileName) {
        try {
            System.out.println("Importing names: " + namesFile);
            FileReader name = new FileReader(namesFile);
            System.out.println("Importing addresses: " + addressFile);
            FileReader address = new FileReader(addressFile);
            System.out.println("Importing numbers: " + numbersFile);
            FileReader number = new FileReader(numbersFile);
            System.out.println("Generating output file: " + outputFileName);
            FileWriter phoneBook = new FileWriter(outputFileName);

            char currentChar = (char)-1;
            String line;
            int i = 0;

            while (name.ready() && address.ready() && number.ready()) {
                //System.out.println((name.ready() && address.ready() && number.ready()));
                System.out.println("Contact: " + i++);
                //read next name
                line = getLine(name);
                phoneBook.write(line + "\n");
                System.out.println(line);

                //read next address

                line = getLine(address);
                phoneBook.write(line + "\n");
                System.out.println(line);

                //read next second address line
                    line = getLine(address);
                phoneBook.write(line + "\n");
                System.out.println(line);


                //read next phone number
                line = getLine(number);
                phoneBook.write(line + "\n");
                System.out.println(line);
            }

            name.close();
            address.close();
            number.close();
            phoneBook.close();
        }
        catch (java.io.FileNotFoundException e) {
            System.out.println("One or more files do not exist");
            return;
        }
        catch (java.io.IOException e) {
            System.out.println("Output file could not be generated, check to make sure it does not already exist");
        }
    }

    public static Vector<contact> loadPhoneBook(String importFileName) {
        try {
            FileReader importFile = new FileReader(importFileName);
            String entryNumber = getLine(importFile);
            System.out.println(entryNumber);
            Vector<contact> phoneBook = new Vector(Integer.parseInt(entryNumber.trim()));

            while(importFile.ready()) {
                phoneBook.add(new contact(getLine(importFile), (getLine(importFile)+getLine(importFile)), getLine(importFile)));
            }
            return phoneBook;
        }
        catch (java.io.FileNotFoundException e) {
            System.out.printf("File %s not found.\n", importFileName);
            return null;
        }
        catch (java.io.IOException e) {
            System.out.printf("File %s not found.\n", importFileName);
            return null;
        }
    }

    public static void main(String[] args) {
        //generatePhoneBook("Names.txt", "Addresses.txt", "Phone Numbers.txt", "PhoneBook.txt");


        try {

            FileWriter exportFile = new FileWriter("PhoneBook2.txt");
            Scanner input = new Scanner(System.in);
            int choice;

            Vector<contact> phoneBook = loadPhoneBook("PhoneBook.txt");

            phoneBook.lastElement().display();

            do {
                System.out.print("Menu:\n\t1) Lookup Contact\n\t2) Insert New Contact\n\t3) Delete Contact\n\t4) Exit");
                choice = input.nextInt();
                input.nextLine();


                switch (choice) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }


            } while (choice != 4);




        }
        catch (java.io.IOException e) {
            System.out.println("Exception: Unable to Open import file");
        }


    }
}
