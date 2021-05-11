package MenuItem;

import db.DBEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuItem extends DBEntity implements Comparable {
    String name;
    double price;


    public MenuItem(String name, double price) {
        super();
        this.name = name;
        this.price = price;
    }

    public MenuItem(String name) {
        super();
        this.name = name;
        this.price = 0;
    }

    public MenuItem(MenuItem menuItem) {
        name = menuItem.getName();
        price = menuItem.getPrice();
    }

    public MenuItem(ResultSet rs) throws SQLException {
        super(rs.getString("ID"));
        this.name = rs.getString("NAME");
        this.price = rs.getDouble("PRICE");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
