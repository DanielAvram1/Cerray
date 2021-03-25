package Account;

import MenuItem.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class Establishment extends Account{
    private String name;
    private String address;
    private String type;
    private String description;
    private List<MenuItem> menu;

    public Establishment(String email, String phoneNumber, String password, String name, String address, String type, String description, List<MenuItem> menu) {
        super(email, phoneNumber, password);
        this.name = name;
        this.address = address;
        this.type = type;
        this.description = description;
        this.menu = new ArrayList<MenuItem>(menu);
    }


    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<MenuItem> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuItem> menu) {
        this.menu = menu;
    }

    public void addMenuItem(MenuItem menuItem) {
        this.menu.add(menuItem);
    }


    @Override
    public String toString() {
        return "Establishment{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
