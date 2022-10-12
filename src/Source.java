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

    public static void main(String[] args) {
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
        System.out.println("Would you like to save your changes? (y/n): ");
        choice = PhoneBook.getIntProtected();
        if (choice == 'y' && defaultBook.exportPhoneBook("PhoneBook.txt")) {
            System.out.println("Exported Phonebook.txt");
        }
    }

}
