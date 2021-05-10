package db;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class DBService {

    private static DBService singleInstance = null;

    public static DBService getInstance() {
        if (singleInstance == null)
            singleInstance = new DBService();
        return singleInstance;
    }

    private String url;
    private String database;
    private String user;
    private String password;

    private Connection connection;
    private Statement statement;

    private void openConnection() throws SQLException {
        connection = DriverManager.getConnection( url + "/" + database, user, password);
    }
    private void closeConnection() throws SQLException {
        connection.close();
    }

    public DBService() {
        this.url = "jdbc:mysql://localhost:3306";
        this.database = "cerray";
        this.user = "cerray";
        this.password = "cerray";
        try{

            openConnection();

            statement = connection.createStatement();

            statement.execute("CREATE TABLE IF NOT EXISTS MENU_ITEMS (" +
                    "ID VARCHAR(255) PRIMARY KEY NOT NULL," +
                    "NAME VARCHAR(255) NOT NULL," +
                    "PRICE FLOAT(53) NOT NULL" +
                    ")");

            statement.execute("CREATE TABLE IF NOT EXISTS CUSTOMERS (" +
                    "ID VARCHAR(255) PRIMARY KEY NOT NULL," +
                    "EMAIL VARCHAR(255) NOT NULL," +
                    "PHONENUMBER VARCHAR(255)," +
                    "PASSWORD VARCHAR(255) NOT NULL," +
                    "FIRSTNAME VARCHAR(255)," +
                    "LASTNAME VARCHAR(255)," +
                    "DEFAULT_ADDRESS VARCHAR(255) NOT NULL" +
                    ")");

            statement.execute("CREATE TABLE IF NOT EXISTS ESTABLISHMENTS (" +
                    "ID VARCHAR(255) PRIMARY KEY NOT NULL," +
                    "NAME VARCHAR(255) NOT NULL," +
                    "ADDRESS VARCHAR(255) NOT NULL," +
                    "TYPE VARCHAR(255)," +
                    "DESCRIPTION VARCHAR(255)," +
                    "INCOME FLOAT(53) NOT NULL" +
                    ")");

            statement.execute("CREATE TABLE IF NOT EXISTS ORDERS (" +
                    "ID VARCHAR(255) PRIMARY KEY NOT NULL," +
                    "DATE DATE NOT NULL," +
                    "ADDRESS VARCHAR(255) NOT NULL," +
                    "ESTABLISHMENT_ID VARCHAR(255) NOT NULL," +
                    "CUSTOMER_ID VARCHAR(255) NOT NULL," +
                    "DELIVERED BOOLEAN NOT NULL," +
                    "FOREIGN KEY (ESTABLISHMENT_ID) REFERENCES ESTABLISHMENTS(ID)," +
                    "FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMERS(ID)" +
                    ")");

            statement.execute("CREATE TABLE IF NOT EXISTS COURIERS (" +
                    "ID VARCHAR(255) PRIMARY KEY NOT NULL," +
                    "EMAIL VARCHAR(255) NOT NULL," +
                    "PHONENUMBER VARCHAR(255)," +
                    "PASSWORD VARCHAR(255) NOT NULL," +
                    "FIRSTNAME VARCHAR(255)," +
                    "LASTNAME VARCHAR(255)," +
                    "SALARY INT NOT NULL," +
                    "MEAN_OF_TRANSPORT VARCHAR(255)" +
                    ")");

            statement.execute("CREATE TABLE IF NOT EXISTS DELIVERIES (" +
                    "ID VARCHAR(255) PRIMARY KEY," +
                    "ORDER_ID VARCHAR(255) NOT NULL," +
                    "COURIER_ID VARCHAR(255) NOT NULL," +
                    "PICKED_DATE DATE NOT NULL," +
                    "DELIVERY_DATE DATE NOT NULL," +
                    "FOREIGN KEY (ORDER_ID) REFERENCES ORDERS(ID)," +
                    "FOREIGN KEY (COURIER_ID) REFERENCES COURIERS(ID)" +
                    ")");

            statement.execute("CREATE TABLE IF NOT EXISTS ESTABLISHMENT_ASOC_MENU_ITEM (" +
                    "ESTABLISHMENT_ID VARCHAR(255) NOT NULL," +
                    "MENU_ITEM_ID VARCHAR(255) NOT NULL," +
                    "QUANTITY INT NOT NULL," +
                    "PRIMARY KEY (ESTABLISHMENT_ID, MENU_ITEM_ID)," +
                    "FOREIGN KEY (ESTABLISHMENT_ID) REFERENCES ESTABLISHMENTS(ID)," +
                    "FOREIGN KEY (MENU_ITEM_ID) REFERENCES MENU_ITEMS(ID)" +
                    ")");

            statement.execute("CREATE TABLE IF NOT EXISTS ORDER_ASOC_MENU_ITEM (" +
                    "ORDER_ID VARCHAR(255) NOT NULL," +
                    "MENU_ITEM_ID VARCHAR(255) NOT NULL," +
                    "QUANTITY INT NOT NULL," +
                    "PRIMARY KEY (ORDER_ID, MENU_ITEM_ID)," +
                    "FOREIGN KEY (ORDER_ID) REFERENCES ORDERS(ID)," +
                    "FOREIGN KEY (MENU_ITEM_ID) REFERENCES MENU_ITEMS(ID)" +
                    ")");

            closeConnection();
        }catch(Exception e){ System.out.println(e);}
    }

    public ResultSet select(String sql){
        try{
            openConnection();

            ResultSet rs = statement.executeQuery(sql);

            closeConnection();
            return rs;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void execute(String sql){
        try{
            openConnection();

            statement.execute(sql);

            closeConnection();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
