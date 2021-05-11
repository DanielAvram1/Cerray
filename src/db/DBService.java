package db;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.Date;

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
        statement = connection.createStatement();
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
                    "EMAIL VARCHAR(255) NOT NULL," +
                    "PHONENUMBER VARCHAR(255)," +
                    "PASSWORD VARCHAR(255) NOT NULL," +
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
                    "FOREIGN KEY (ESTABLISHMENT_ID) REFERENCES ESTABLISHMENTS(ID) ON DELETE CASCADE," +
                    "FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMERS(ID) ON DELETE CASCADE" +
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
                    "DELIVERY_DATE DATE," +
                    "FOREIGN KEY (ORDER_ID) REFERENCES ORDERS(ID) ON DELETE CASCADE," +
                    "FOREIGN KEY (COURIER_ID) REFERENCES COURIERS(ID) ON DELETE CASCADE" +
                    ")");

            statement.execute("CREATE TABLE IF NOT EXISTS ESTABLISHMENT_ASOC_MENU_ITEM (" +
                    "ESTABLISHMENT_ID VARCHAR(255) NOT NULL," +
                    "MENU_ITEM_ID VARCHAR(255) NOT NULL," +
                    "QUANTITY INT NOT NULL," +
                    "PRIMARY KEY (ESTABLISHMENT_ID, MENU_ITEM_ID)," +
                    "FOREIGN KEY (ESTABLISHMENT_ID) REFERENCES ESTABLISHMENTS(ID) ON DELETE CASCADE," +
                    "FOREIGN KEY (MENU_ITEM_ID) REFERENCES MENU_ITEMS(ID) ON DELETE CASCADE" +
                    ")");

            statement.execute("CREATE TABLE IF NOT EXISTS ORDER_ASOC_MENU_ITEM (" +
                    "ORDER_ID VARCHAR(255) NOT NULL," +
                    "MENU_ITEM_ID VARCHAR(255) NOT NULL," +
                    "QUANTITY INT NOT NULL," +
                    "PRIMARY KEY (ORDER_ID, MENU_ITEM_ID)," +
                    "FOREIGN KEY (ORDER_ID) REFERENCES ORDERS(ID) ON DELETE CASCADE," +
                    "FOREIGN KEY (MENU_ITEM_ID) REFERENCES MENU_ITEMS(ID) ON DELETE CASCADE" +
                    ")");


        }catch(Exception e){ System.out.println(e);}
    }

    public ResultSet select(String sql){
        try{
            ResultSet rs = statement.executeQuery(sql);

            return rs;
        } catch (SQLException e) {
            System.out.println("select with no args " + e.getMessage());
        }
        return null;
    }


    public ResultSet select(String sql, Object ...args){
        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            int cnt = 1;
            for(Object arg: args) {

                if (arg instanceof String)
                    pstmt.setString(cnt, (String)arg);
                if(arg instanceof Date)
                    pstmt.setDate(cnt, (java.sql.Date)arg);
                if(arg instanceof Integer)
                    pstmt.setInt(cnt, (Integer)arg);
                if(arg instanceof Double)
                    pstmt.setDouble(cnt, (Double)arg);
                if(arg instanceof Boolean)
                    pstmt.setBoolean(cnt, (Boolean)arg);

                if(arg == null)
                    pstmt.setNull(cnt, Types.NULL);
                cnt++;
            }
            return pstmt.executeQuery();

        } catch (SQLException e) {
            System.out.println("select with args " + e.getMessage());
        }
        return null;
    }


    public void execute(String sql){
        try{
            statement.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void execute(String sql, Object ...args){
        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            int cnt = 1;
            for(Object arg: args) {
                if (arg instanceof String)
                    pstmt.setString(cnt, (String)arg);
                if(arg instanceof Date)
                    pstmt.setDate(cnt,new java.sql.Date(((Date)arg).getTime()));
                if(arg instanceof Integer)
                    pstmt.setInt(cnt, (Integer)arg);
                if(arg instanceof Double)
                    pstmt.setDouble(cnt, (Double)arg);
                if(arg instanceof Boolean)
                    pstmt.setBoolean(cnt, (Boolean)arg);

                if(arg == null)
                    pstmt.setNull(cnt, Types.NULL);
                cnt++;
            }
            pstmt.execute();

        } catch (SQLException e) {
            System.out.println(args[0] + e.getMessage());
        }
    }


}
