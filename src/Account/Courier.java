package Account;

import Delivery.Delivery;
import db.DB;

import java.util.ArrayList;
import java.util.List;

public class Courier extends Worker {

    String meanOfTransport;
    List<Delivery> deliveryList;

    public Courier(Account account, String firstName, String lastName, String meanOfTransport ) {
        super(account, firstName, lastName, DB.getInstance().minCourierSalary);
        this.meanOfTransport = meanOfTransport;
        this.deliveryList = new ArrayList<>();

    }

    public Courier(String firstName, String lastName, String email, String phoneNumber, int salary, String meanOfTransport, String password) {
        super(email, phoneNumber, password, firstName, lastName, salary);
        this.meanOfTransport = meanOfTransport;
        this.deliveryList = new ArrayList<>();

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
