package Account;

import Order.Order;
import db.DBCSVService;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {

    String defaultAddress;

    private static final int nrCustomers = 0;

    private List<Order> orderList;

    public Customer(Account account, String firstName, String lastName, String defaultAddress) {
        super(account, firstName, lastName);
        this.defaultAddress = defaultAddress;
        this.orderList = new ArrayList<Order>();
        DBCSVService.getInstance().insert(this);
    }

    public Customer(String id, String firstName, String lastName, String email, String phoneNumber, String password, String defaultAddress, List<Order> orderList) {
        super(id, email, phoneNumber, password, firstName, lastName);
        this.defaultAddress = defaultAddress;
        this.orderList = orderList;
    }

    public Customer(String id, String email, String phoneNumber, String password) {
        super(id, email, phoneNumber, password, "user", "user");
        this.defaultAddress = "defaultAdress";
        this.orderList = new ArrayList<Order>();
    }

    public List<Order> getOrders() {
        return new ArrayList<Order>(this.orderList);
    }

    public void addOrder(Order order) {

        this.orderList.add(order);
        DBCSVService.getInstance().insertAsoc(this, order);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public String toCSV() {

        return  super.toCSV() + ',' +
                this.defaultAddress;
    }

    public static Customer readFromCSV(String csv) {
        String[] data = csv.split(",");
        String id = data[0];
        String email = data[1];
        String phoneNumber = data[2];
        String password = data[3];
        String firstName = data[4];
        String lastName = data[5];
        String defaultAddress = data[6];

        List<Order> orderList = DBCSVService.getInstance(3).readOrdersFromCSV(id);

        return new Customer(id, firstName, lastName, email, phoneNumber, password, defaultAddress, orderList);
    }
}
