package Delivery;

import Account.Customer;
import Account.Establishment;
import Order.Order;
import Account.Courier;
import db.DB;
import db.DBEntity;
import db.DBService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Delivery extends DBEntity {
    String orderId;
    String courierId;
    Date pickedDate;
    Date deliveryDate;

    public Delivery(String orderId) {
        super();
        this.orderId = orderId;
        this.courierId = null;
        this.pickedDate = null;
        this.deliveryDate = null;
    }

    public Delivery(String orderId, String courierId, Date pickedDate, Date deliveryDate) {
        this.orderId = orderId;
        this.courierId = courierId;
        this.pickedDate = pickedDate;
        this.deliveryDate = deliveryDate;

        String query = "INSERT INTO DELIVERIES" +
                        " VALUES (?, ?, ?, ?, ?)";

        DBService.getInstance().execute(query, this.getId(), this.orderId, this.courierId, this.pickedDate, this.deliveryDate);

    }

    public Delivery(ResultSet rs) throws SQLException {
        super(rs.getString("ID"));
        this.orderId = rs.getString("ORDER_ID");
        this.courierId = rs.getString("COURIER_ID");
        this.pickedDate = rs.getDate("PICKED_DATE");
        this.deliveryDate = rs.getDate("DELIVERY_DATE");
    }

    public Order getOrder() throws SQLException {
        String query = "SELECT * FROM ORDERS WHERE ID = ?";
        ResultSet rs = DBService.getInstance().select(query, this.orderId);
        rs.next();
        return new Order(rs);
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Courier getCourier() {
        String query = "SELECT * FROM COURIERS WHERE ID = ?";
        ResultSet rs = DBService.getInstance().select(query, this.courierId);
        try{
            rs.next();
            return new Courier(rs);
        }catch (SQLException ignored) {
            return null;
        }
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {

        this.deliveryDate = deliveryDate;

        String query = "UPDATE DELIVERIES SET DELIVERY_DATE = ?";
        DBService.getInstance().execute(query, new Date());
    }


    @Override
    public String toString() {
        try {
            return "From: " + getOrder().getEstablishment().getAddress() +
                    "\tTo: " + getOrder().getAddress() +
                    "\tBy: " + getCourier().getEmail() +
                    "\tPicked: " + this.pickedDate +
                    "\tDelivered: " + (deliveryDate == null ? "Not yet delivered" : deliveryDate);
        } catch (SQLException throwables) {
            return "This Delivery does not have an Order, Establishment or a Courier.";
        }
    }
}
