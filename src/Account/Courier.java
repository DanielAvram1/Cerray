package Account;

import db.DB;

public class Courier extends Worker {

    private String meanOfTransport;

    public Courier(Account account, String firstName, String lastName, String meanOfTransport ) {
        super(account, firstName, lastName, DB.getInstance().minCourierSalary);
        this.meanOfTransport = meanOfTransport;

    }

    public Courier(String firstName, String lastName, String email, String phoneNumber, int salary, String meanOfTransport, String password) {
        super(email, phoneNumber, password, firstName, lastName, salary);
        this.meanOfTransport = meanOfTransport;

    }

    @Override
    public String toString() {
        return "Courier{" +
                "meanOfTransport='" + meanOfTransport + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", salary=" + salary +
                '}';
    }
}
