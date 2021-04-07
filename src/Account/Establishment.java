package Account;

import MenuItem.MenuItem;
import com.sun.source.tree.Tree;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class Establishment extends Account{
    String name;
    String address;
    String type;
    String description;
    SortedMap<MenuItem, Integer> menu;
    double income;

    public Establishment(Account account, String name, String address, String type, String description , SortedMap<MenuItem, Integer> menu) {
        super(account);
        this.name = name;
        this.address = address;
        this.type = type;
        this.description = description;
        this.menu = new TreeMap<>(menu);
        income = 0;
    }

    public Establishment(String email, String phoneNumber, String password, String name, String address, String type, String description, SortedMap<MenuItem, Integer> menu) {
        super(email, phoneNumber, password);
        this.name = name;
        this.address = address;
        this.type = type;
        this.description = description;
        this.menu = new TreeMap<>(menu);
        income = 0;
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


    public SortedMap<MenuItem, Integer> getMenu() {
        return menu;
    }

    public void setMenu(SortedMap<MenuItem, Integer> menu) {
        this.menu = menu;
    }

    public void addMenuItem(MenuItem menuItem) {
        if(menu.containsKey(menuItem)){
            Integer quan = menu.get(menuItem);
            menu.put(menuItem, quan + 1);
        }
        else
            menu.put(menuItem, 0);
    }

    public void addQuantity(MenuItem menuItem, Integer quantity){
        if(menu.containsKey(menuItem)){
            Integer quan = menu.get(menuItem);
            menu.put(menuItem, quan + quantity);
        }
        else
            menu.put(menuItem, quantity);
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
