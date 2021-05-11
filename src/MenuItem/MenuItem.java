package MenuItem;

import db.DBEntity;
import db.DBService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuItem extends DBEntity implements Comparable {
    String name;
    double price;


    public MenuItem(String name, double price) {
        super();
        this.name = name;
        this.price = price;

        String query = "INSERT INTO MENU_ITEMS" +
                " VALUES (?, ?, ?)";

        DBService.getInstance().execute(query, this.getId(), this.name, this.price);

    }

    public MenuItem(String name) {
        super();
        this.name = name;
        this.price = 0;

        String query = "INSERT INTO MENU_ITEMS" +
                " VALUES (?, ?, ?)";

        DBService.getInstance().execute(query, this.getId(), this.name, this.price);
    }

    public MenuItem(MenuItem menuItem) {
        name = menuItem.getName();
        price = menuItem.getPrice();
    }

    public MenuItem(ResultSet rs) throws SQLException {
        super(rs.getString("ID"));
        this.name = rs.getString("NAME");
        this.price = rs.getFloat("PRICE");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        String query = "UPDATE MENU_ITEMS SET NAME = ? WHERE ID = ?";
        DBService.getInstance().execute(query, name, this.getId());

        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {

        String query = "UPDATE MENU_ITEMS SET PRICE = ? WHERE ID = ?";
        DBService.getInstance().execute(query, price, this.getId());

        this.price = price;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        MenuItem menuItem = (MenuItem)o;

        return this.getId().compareTo(menuItem.getId());
    }
}
