package Account;

import Order.Order;
import db.DBService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {

    String defaultAddress;

    private static final int nrCustomers = 0;

    private List<String> orderIdList;

    public Customer(Account account, String firstName, String lastName, String defaultAddress) {
        super(account, firstName, lastName);
        this.defaultAddress = defaultAddress;
        this.orderIdList = new ArrayList<>();
    }

    public Customer(String id, String firstName, String lastName, String email, String phoneNumber, String password, String defaultAddress) {
        super(id, email, phoneNumber, password, firstName, lastName);
        this.defaultAddress = defaultAddress;
        this.orderIdList = new ArrayList<>();
    }

    public Customer(String id, String email, String phoneNumber, String password) {
        super(id, email, phoneNumber, password, "user", "user");
        this.defaultAddress = "defaultAdress";
        this.orderIdList = new ArrayList<>();
    }

    public Customer(ResultSet rs) throws SQLException {
        super(rs.getString("ID"), rs.getString("EMAIL"), rs.getString("PHONENUMBER"),
                rs.getString("PASSWORD"), rs.getString("FIRSTNAME"),
                rs.getString("LASTNAME"));
        this.defaultAddress = rs.getString("DEFAULT_ADDRESS");
    }

    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<Order>();

        for(String orderId: this.orderIdList) {
            String query = "SELECT * FROM ORDERS WHERE ID = " + orderId;
            ResultSet rs = DBService.getInstance().select(query);
            Order order = null;
            try {
                order = new Order(rs);
            } catch (SQLException ignored) {};
            orders.add(order);
        }

        return orders;
    }

    public void addOrder(Order order) {
        this.orderIdList.add(order.getId());
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
}
