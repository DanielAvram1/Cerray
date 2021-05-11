package Account;

import Delivery.Delivery;
import db.DB;
import db.DBService;

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

        String query = String.format("INSERT INTO COURIERS" +
                " VALUES ('%s', '%s', '%s', '%s', '%s', '%s', %d, '%s')",
                        this.getId(), this.email, this.phoneNumber, this.password, this.firstName,
                                this.lastName, this.salary, this.meanOfTransport);
        DBService.getInstance().execute(query);

    }

    public Courier(String id, String firstName, String lastName, String email, String phoneNumber, int salary, String meanOfTransport, String password) throws SQLException {
        super(id, email, phoneNumber, password, firstName, lastName, salary);
        this.meanOfTransport = meanOfTransport;
        this.deliveryIdList = new ArrayList<>();


    }

    public Courier(ResultSet rs) throws SQLException {
        super(rs.getString("ID"), rs.getString("EMAIL"), rs.getString("PHONENUMBER"),
                rs.getString("PASSWORD"), rs.getString("FIRSTNAME"),
                rs.getString("LASTNAME"), rs.getInt("SALARY"));
        this.meanOfTransport = rs.getString("MEAN_OF_TRANSPORT");
        this.deliveryIdList = new ArrayList<>();

        String query = "SELECT * FROM DELIVERIES WHERE COURIER_ID = ?";
        ResultSet deliveryRs = DBService.getInstance().select(query, this.getId());
        while(deliveryRs.next()){
            try {
                this.deliveryIdList.add(deliveryRs.getString("ID"));
            } catch (SQLException ignored){}
        }

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
