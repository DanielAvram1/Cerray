package Persons;

import java.io.PrintStream;

public class Customer extends Person {

    private static final int nrCustomers = 0;

    public Customer(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Customer(String email, String phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

}
