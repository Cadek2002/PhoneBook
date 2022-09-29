import org.w3c.dom.ls.LSOutput;

import java.io.FileWriter;

public class contact {
    String name;
    String address;
    String number;


    //constructors
    public contact() {
        name = null;
        address = null;
        number = null;
    }
    public contact(String na, String a, String nu) {
        name = na;
        address =a;
        number =nu;
    } //sets name address and number to specified values

    //modifiers and accessors
    public String getName() {return name;}
    public String getAddress() {return address;}
    public String getNumber() {return number;}

    public void setName(String s) {name = s;}
    public void setAddress(String s) {name = s;}
    public void setNumber(String s) {name = s;}

    public void display() {
        System.out.printf("Contact Info:\nName: %s\nAddress: %s\nPhone Number: %s", name, address, number);
    }
    public void export (FileWriter output) {
        try {
            output.write(name);
            output.write(address);
            output.write(number);
        }
        catch (java.io.IOException e) {
            System.out.println("Exception: Unable to access output file");
        }
    }


}
