package Account;

import Delivery.Delivery;
import db.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Courier extends Worker {

    String meanOfTransport;
    List<String> deliveryIdList;

    public Courier(Account account, String firstName, String lastName, String meanOfTransport ) {
        super(account, firstName, lastName, DB.getInstance().minCourierSalary);
        this.meanOfTransport = meanOfTransport;
        this.deliveryIdList = new ArrayList<>();

    }

    public Courier(String id, String firstName, String lastName, String email, String phoneNumber, int salary, String meanOfTransport, String password) {
        super(id, email, phoneNumber, password, firstName, lastName, salary);
        this.meanOfTransport = meanOfTransport;
        this.deliveryIdList = new ArrayList<>();

    }

    public Courier(ResultSet rs) throws SQLException {
        super(rs.getString("ID"), rs.getString("EMAIL"), rs.getString("PHONENUMBER"),
                rs.getString("PASSWORD"), rs.getString("FIRSTNAME"),
                rs.getString("LASTNAME"), rs.getInt("SALARY"));
        this.meanOfTransport = rs.getString("MEAN_OF_TRANSPORT");
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
