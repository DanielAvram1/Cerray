package Order;

import Account.Customer;
import Account.Establishment;
import MenuItem.MenuItem;
import db.DBEntity;
import db.DBService;

import javax.swing.plaf.nimbus.State;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Order extends DBEntity {
    Date date;
    String address;
    SortedMap<String, Integer> menuItemIdList;
    String establishmentId;
    String customerId;
    boolean delivered;

    public Order(Date date, String address, String customerId, String establishmentId, SortedMap<String, Integer> menuItemIdList) {
        super();
        this.date = date;
        this.menuItemIdList = menuItemIdList;
        this.address = address;
        this.customerId = customerId;
        this.establishmentId = establishmentId;
        this.delivered = false;

        String query = "INSERT INTO ORDERS" +
                " VALUES (?, ?, ?, ?, ?, ?)";

        DBService.getInstance().execute(query, this.getId(), this.date, this.address, this.establishmentId, this.customerId, this.delivered);
    }

    public Order(ResultSet rs) throws SQLException {
        super(rs.getString("ID"));
        this.date = rs.getDate("DATE");
        this.address = rs.getString("ADDRESS");
        this.establishmentId = rs.getString("ESTABLISHMENT_ID");
        this.customerId = rs.getString("CUSTOMER_ID");
        this.delivered = rs.getBoolean("DELIVERED");

        this.menuItemIdList = new TreeMap<>();

        String query = "SELECT * " +
                "FROM ORDER_ASOC_MENU_ITEM " +
                "WHERE ORDER_ID = ?";

        ResultSet menuItemRs = DBService.getInstance().select(query, this.getId());

        while(menuItemRs.next()) {
            try {
                this.menuItemIdList.put(menuItemRs.getString("MENU_ITEM_ID"), menuItemRs.getInt("QUANTITY"));
            } catch (SQLException ignored) {}
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Establishment getEstablishment() {
        String query = "SELECT * FROM ESTABLISHMENTS WHERE ID = ?";
        ResultSet rs = DBService.getInstance().select(query, this.establishmentId);
        try {
            rs.next();
            return new Establishment(rs);
        } catch (SQLException ignored){
            return null;
        }
    }

    public Customer getReceiver() {
        String query = "SELECT * FROM CUSTOMERS WHERE ID = ?";
        ResultSet rs = DBService.getInstance().select(query, this.customerId);
        try{
            rs.next();
            return new Customer(rs);
        } catch (SQLException ignored) {
            return null;
        }
    }

    public String getAddress() {
        return address;
    }


    public void setDelivered(boolean delivered) {

        String query = "UPDATE ORDERS SET DELIVERED = TRUE WHERE ID = ?";
        DBService.getInstance().execute(query, this.getId());

        this.delivered = delivered;
    }


    @Override
    public String toString() {
        return "From:" + getEstablishment().getName() + " \tTo: " + address + "\tDate: " + date + "\t" + (delivered ? "Delivered" : "Not Delivered");
    }
}
