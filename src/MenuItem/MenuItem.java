package MenuItem;

public class MenuItem implements Comparable{
    String name;
    double price;


    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public MenuItem(String name) {
        this.name = name;
        this.price = 0;
    }

    public MenuItem(MenuItem menuItem) {
        name = menuItem.getName();
        price = menuItem.getPrice();
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
}
