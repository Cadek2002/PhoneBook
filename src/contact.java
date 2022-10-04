import java.io.FileWriter;

public class contact {
    String name;
    String addressLine1;
    String addressLine2;
    String number;


    //constructors
    public contact() {
        name = null;
        addressLine1 = null;
        addressLine2 = null;
        number = null;
    }
    public contact(String na, String a1, String a2, String nu) {
        name = na;
        addressLine1 = a1;
        addressLine2 = a2;
        number =nu;
    } //sets name address and number to specified values

    //modifiers and accessors


    public String getName() {
        return name;
    }
    public String getAddressLine2() {
        return addressLine2;
    }
    public String getAddressLine1() {
        return addressLine1;
    }
    public String getNumber() {
        return number;
    }

    public void setName(String s) {name = s;}
    public void setAddressLine1(String s) {addressLine1 = s;}
    public void setAddressLine2(String s) {addressLine2 = s;}
    public void setNumber(String s) {number = s;}

    public void display() {
        System.out.printf("Contact Info:\nName:\t%s\nAddress:%s\n\t\t%s\nPhone Number:\t%s", name, addressLine1, addressLine2, number);
        //System.out.println("help");
    }
    public void export (FileWriter output) {
        try {
            output.write(name);
            output.write(addressLine1);
            output.write(addressLine2);
            output.write(number);
        }
        catch (java.io.IOException e) {
            System.out.println("Exception: Unable to access output file");
        }
    }


}
