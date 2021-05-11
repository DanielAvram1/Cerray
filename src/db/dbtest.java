package db;

import java.awt.image.DataBufferInt;
import java.sql.*;

public class dbtest {
    public static void main(String[] args){

        DBService.getInstance();
        String query = "INSERT INTO COURIERS VALUES ('id', 'email', 'phonenumber', 'password', 'firstname', 'lastname', 100, 'meanoftransport')";
        DBService.getInstance().execute(query);
        query = "SELECT * FROM COURIERS";
        ResultSet rs = DBService.getInstance().select(query);
        try {
            while(rs.next()){
                System.out.println(rs.getString("ID") + " " + rs.getString("EMAIL"));
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
