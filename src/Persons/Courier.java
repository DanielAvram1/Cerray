package Persons;

public class Courier extends Worker {

    private String meanOfTransport;

    public Courier(String firstName, String lastName, String email, String phoneNumber, int salary, String meanOfTransport) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.salary = salary;

        this.jobName = "Courier";

        this.meanOfTransport = meanOfTransport;

    }

}
