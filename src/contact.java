import java.io.FileWriter;

public class contact {

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

            //find the end of the first token, the street number
            while (Character.isDigit(l1.charAt(currentIndex++)));
            //set the street address to this number, the suffix incrementer skips over the space
            streetNumber = Integer.valueOf(l1.substring(0, currentIndex-1));
            savedIndex = ++currentIndex;
            //find the start of the room number (designated by a '(' character) OR the end of the line
            while (currentIndex < l1.length() && l1.charAt(currentIndex) != '(') currentIndex++;
            //if there is a room number, set the corresponding variable to the remaining data in the string, ignoring the last character as it should be a ')'
            roomNumber = (currentIndex < l1.length() ? l1.substring(currentIndex, l1.length()-1):null);
            //reset index to read next line
            currentIndex = 0;
            //read line 2 to comma (indicating end of city token) and set variable accordingly
            while (l2.charAt(currentIndex) != ',' & currentIndex++ < l2.length());
            city = l2.substring(0, currentIndex);
            //skip over the ', ' characters and save start of state string;
            currentIndex +=2 ;

            //Since state will always be 2-characters, we don't need to loop through the rest of the string to find the start/end of tokens
            state = l2.substring(currentIndex, currentIndex+2);
            zip = Integer.valueOf(l2.substring(currentIndex+3, l2.length()));
        }

        public String stringRep() {
            return line1 + "\n" + line2;
        }

        public void debugDisplay() {
            System.out.printf("Street address: %d %s %s\nCity: %s\nState: %s\nZip: %d", streetNumber, streetName, roomNumber, city, state, zip);
        }

    }
    String name;
    String number;
    Address address;

    //constructors
    public contact() {
        name = null;

        address = null;
        number = null;
    }
    public contact(String na, String a1, String a2, String nu) {
        name =      na;
        address =   new Address(a1, a2);
        number =    nu;

        address.debugDisplay();
    } //sets name address and number to specified values

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
    public String addressString() {
        return address.stringRep();
    }

    public void setName(String s) {name = s;}
    public void setNumber(String s) {number = s;}

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
