import java.util.Scanner;
import java.io.*;

/**
 Todo:
    1) Implement Address class to contact class to better search through addresses
    2) implement basic lookup function based on linear search for each field
        b) address lookup
        c) phone number lookup
    3) implement file updating
        b) implement insert/delete element functions
    4) Implement outer-loop for loading/generating phonebooks in program
    OPTIONAL:
    5) add sets of binary/AVL trees (with each field of contacts acting as the key) for more efficient searching
    6) implement multithreading to loadPhoneBook method because it looks cool
 **/

public class Source {

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

            String line;
            int i = 0;

            while (name.ready() && address.ready() && number.ready()) {
                //System.out.println((name.ready() && address.ready() && number.ready()));
                System.out.println("Contact: " + i++);
                //read next name
                line = PhoneBook.getLine(name);
                phoneBook.write(line + "\n");
                System.out.println(line);

                //read next address
                line = PhoneBook.getLine(address);
                phoneBook.write(line + "\n");
                System.out.println(line);

                //read next second address line
                line = PhoneBook.getLine(address);
                phoneBook.write(line + "\n");
                System.out.println(line);

                //read next phone number
                line = PhoneBook.getLine(number);
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
        }
        catch (java.io.IOException e) {
            System.out.println("Output file could not be generated, check to make sure it does not already exist");
        }
    }
    public static void main(String[] args) {
        //generatePhoneBook("Names.txt", "Addresses.txt", "Phone Numbers.txt", "PhoneBook.txt");
        try {
            FileWriter exportFile = new FileWriter("PhoneBook2.txt");
            Scanner input = new Scanner(System.in);
            int choice;
            PhoneBook defaultBook = new PhoneBook("PhoneBook.txt");

            do {
                System.out.print("Menu:\n\t1) Lookup Contact\n\t2) Insert New Contact\n\t3) Delete Contact\n\t4) Exit\nInsert Option: ");
                choice = input.nextInt();
                input.nextLine();

                switch (choice) {
                    case 1:
                        defaultBook.lookUpInterface(input);
                        break;
                    case 2:
                        break;
                    case 3:
                        System.out.print("Insert index of contact: ");
                        defaultBook.get(input.nextInt()).display();
                        input.nextLine();
                        break;
                }


            } while (choice != 4);

        }
        catch (java.io.IOException e) {
            System.out.println("Exception: Unable to Open import file");
        }


    }

}
