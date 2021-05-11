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


        String query = String.format("INSERT INTO CUSTOMERS" +
                        " VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                this.getId(), this.email, this.phoneNumber, this.password, this.firstName,
                this.lastName, this.defaultAddress);
        DBService.getInstance().execute(query);
    }

    public Customer(String id, String firstName, String lastName, String email, String phoneNumber, String password, String defaultAddress) {
        super(id, email, phoneNumber, password, firstName, lastName);
        this.defaultAddress = defaultAddress;
        this.orderIdList = new ArrayList<>();
    }

    public Customer(String id, String email, String phoneNumber, String password) {
        super(id, email, phoneNumber, password, "user", "user");
        this.defaultAddress = "defaultAddress";
        this.orderIdList = new ArrayList<>();
    }

    public Customer(ResultSet rs) throws SQLException {
        super(rs.getString("ID"), rs.getString("EMAIL"), rs.getString("PHONENUMBER"),
                rs.getString("PASSWORD"), rs.getString("FIRSTNAME"),
                rs.getString("LASTNAME"));
        this.defaultAddress = rs.getString("DEFAULT_ADDRESS");
        this.orderIdList = new ArrayList<>();

        String query = "SELECT * FROM ORDERS WHERE CUSTOMER_ID = ?";
        ResultSet orderRs = DBService.getInstance().select(query, this.getId());
        while(orderRs.next()){
            try {
                this.orderIdList.add(orderRs.getString("ID"));
            } catch (SQLException ignored){}
        }

    }

    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<Order>();

        for(String orderId: this.orderIdList) {
            String query = "SELECT * FROM ORDERS WHERE ID = ?";
            ResultSet rs = DBService.getInstance().select(query, orderId);
            Order order = null;
            try {
                rs.next();
                order = new Order(rs);
            } catch (SQLException ignored) {};
            if(order != null)
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
