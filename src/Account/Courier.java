package Account;

import Delivery.Delivery;
import db.DB;
import db.DBCSVService;

import java.util.ArrayList;
import java.util.List;

public class Courier extends Worker {

    String meanOfTransport;
    List<Delivery> deliveryList;

    public Courier(Account account, String firstName, String lastName, String meanOfTransport ) {
        super(account, firstName, lastName, DB.getInstance().minCourierSalary);
        this.meanOfTransport = meanOfTransport;
        this.deliveryList = new ArrayList<>();
        DBCSVService.getInstance().insert(this);

    }

    public Courier(String id, String firstName, String lastName, String email, String phoneNumber, int salary, String meanOfTransport, String password, List<Delivery> deliveryList) {
        super(id, email, phoneNumber, password, firstName, lastName, salary);
        this.meanOfTransport = meanOfTransport;
        this.deliveryList = deliveryList;
        // DBCSVService.getInstance().insert(this); pentru ca incurca la citirea din csv

    }

    public List<Delivery> getDeliveryList() {
        return new ArrayList<>(deliveryList);
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

    @Override
    protected String toCSV() {

        return  super.toCSV() + ',' +
                this.meanOfTransport;


    }

    static public Courier readFromCSV(String csv) {
        String[] data = csv.split(",");
        String id = data[0];
        String email = data[1];
        String phoneNumber = data[2];
        String password = data[3];
        String firstName = data[4];
        String lastName = data[5];
        int salary = Integer.parseInt(data[6]);
        String meanOfTransport = data[7];
        List<Delivery> deliveryList = DBCSVService.getInstance(4).readDeliveriesFromCSV(id);
        return new Courier(id, firstName, lastName, email, phoneNumber, salary, meanOfTransport, password, deliveryList);
    }

}
