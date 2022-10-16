import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class contact {

    public class badInputError extends Exception {
        String message;
        badInputError(String s) {
            message = s;
        }
        String throwable() {
            return message;
        }
    }
    public class Address {

        //parsed variables
        int streetNumber;
        String streetName;
        String roomNumber;
        String city;
        String state;
        int zip;

        //full string for output;
        String line1;
        String line2;

        Address (String l1, String l2) {
            //prep strings for reading
            l1 = l1.trim();
            l2 = l2.trim();

            //record the string variation of the object
            line1 = l1;
            line2 = l2;

            int currentIndex = 0;
            int savedIndex;

            //Parse the lines into separate tokens/variables

            try {
                //find the end of the first token, the street number

                while (currentIndex < l1.length() && Character.isDigit(l1.charAt(currentIndex++))) ;
                //set the street address to this number, the suffix incrementer skips over the space
                streetNumber = Integer.valueOf(l1.substring(0, currentIndex - 1));
                savedIndex = currentIndex++;
                //find the start of the room number (designated by a '(' character) OR the end of the line
                while (currentIndex < l1.length() && l1.charAt(currentIndex) != '(') currentIndex++;
                streetName = line1.substring(savedIndex, currentIndex);
                //if there is a room number, set the corresponding variable to the remaining data in the string, ignoring the last character as it should be a ')'
                roomNumber = (currentIndex < l1.length() ? l1.substring(currentIndex, l1.length()) : null);
                //reset index to read next line
                currentIndex = 0;
                //read line 2 to comma (indicating end of city token) and set variable accordingly
                while (l2.charAt(currentIndex) != ',' && currentIndex < l2.length()) currentIndex++;
                city = l2.substring(0, currentIndex);
                //skip over the ', ' characters and save start of state string;
                currentIndex += 2;

                //Since state will always be 2-characters, we don't need to loop through the rest of the string to find the start/end of tokens
                state = l2.substring(currentIndex, currentIndex + 2);
                zip = Integer.valueOf(l2.substring(currentIndex + 3, l2.length()));
            }
            catch (NumberFormatException e) {
                streetNumber = 0;
                zip = 0;
            }
            catch (StringIndexOutOfBoundsException e) {
                streetName = "";
                city = "";
            }
        } //tokenizes two address lines and puts the appropriate data in each field

        public String stringRep() {
            return line1 + "\n" + line2;
        } //returns the address in the form of a single string

        public void debugDisplay() {
            System.out.printf("Street address: %d %s %s\nCity: %s\nState: %s\nZip: %d\n", streetNumber, streetName, roomNumber, city, state, zip);
        } //outputs the value of all fields

        public boolean isValid() {
            return (streetNumber > 0 && zip > 0 && !streetName.equals("") && !city.equals("") && state.length() == 2);
        } //checks if all fields have a valid value (except for room number as its not requited)

    }
    String name;
    String number;
    String rawNumber;
    Address address;

    //constructors
    public contact() {
        name = null;
        address = null;
        number = null;
        rawNumber = null;
    }
    public contact(String na, String a1, String a2, String nu) {
        setName(na);
        address =   new Address(a1, a2);
        setNumber(nu);

        //address.debugDisplay();
    } //sets name address and number to specified values

    public contact(Scanner in) {
        String input;
        System.out.print("Insert Contact Name: ");
        setName(in.nextLine());

        System.out.print("Address Example:\n\t56849 Dark St. (Room 2)\n\tSouthfield, CA 77895\n");
        setAddress(in.nextLine().trim(), in.nextLine().trim());

        while (!address.isValid()) {
            System.out.println("Invalid Address please insert a valid address: ");
            setAddress(in.nextLine().trim(), in.nextLine().trim());
        }

        System.out.print("Insert Phone Number (xxx) xxx-xxxx: ");
        setNumber(in.nextLine());

        while (number == null) {
            System.out.print("Error: Invalid Number Insert a valid number or 'No' to leave the field blank: ");
            setNumber(in.nextLine());
        }
    }


    //modifiers and accessors


    public String getName() {
        return name;
    }
    public Address getAddress() {
        return address;
    }
    public String getNumber() {
        return number;
    }
    public String getRawNumber() {
        return rawNumber;
    }
    public String getAddressString() {
        return address.stringRep();
    }

    public void setName(String s) {name = s;}
    public void setNumber(String s) {
        number = s;
        rawNumber = "";
        int digit = 0;
        for (int i = s.length()-1; i >=0 && rawNumber.length() < 10; i--) {
            if (Character.isDigit(s.charAt(i))) {
                rawNumber += s.charAt(i);
            }
        }

        if (rawNumber.length() != 10) {
            rawNumber = null;
            number = null;
        }
        else {
            //Ensure uniform format of number string
            number = "(" + rawNumber.substring(0, 3) + ") " + rawNumber.substring(4, 7) + "-" + rawNumber.substring(7, 10);
            System.out.println(number);
        }
    }

    public void setAddress(String l1, String l2) {
        address = new Address(l1, l2);
    }

    public void display() {
        System.out.printf("Contact Info:\nName:\t\t%s\nAddress:\t%s\n\t\t\t%s\nNumber:\t\t%s\n", name.trim(), address.line1.trim(), address.line2.trim(), number.trim());
        //System.out.println("help");
    }
    public void export (FileWriter output) {
        try {
            output.write(name);
            output.write(address.stringRep());
            output.write(number);
        }
        catch (java.io.IOException e) {
            System.out.println("Exception: Unable to access output file");
        }
    }


}
