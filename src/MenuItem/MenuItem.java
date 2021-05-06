package MenuItem;

import db.DBCSVService;
import db.DBEntity;

import java.util.UUID;

public class MenuItem extends DBEntity implements Comparable  {

    String name;
    double price;


    public MenuItem(String name, double price) {
        super();
        this.name = name;
        this.price = price;
        DBCSVService.getInstance().insert(this);
    }

    public MenuItem(String name) {
        super();
        this.name = name;
        this.price = 0;
        DBCSVService.getInstance().insert(this);
    }

    public MenuItem(MenuItem menuItem) {
        super();
        name = menuItem.getName();
        price = menuItem.getPrice();
        DBCSVService.getInstance().insert(this);
    }

    public MenuItem(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
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
        return this.name.compareTo(menuItem.name);
    }

    @Override
    protected String toCSV() {
        return  id + ',' +
                name + ',' +
                price;
    }

    static public MenuItem readFromCSV(String csv) {
        String[] data = csv.split(",");
        String id = data[0];
        String name = data[1];
        double price = Double.parseDouble(data[2]);
        return new MenuItem(id, name, price);
    }
}
