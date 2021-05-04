package Account;

import MenuItem.MenuItem;
import db.DBCSVService;

import java.awt.*;
import java.io.IOException;
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
        DBCSVService.getInstance().insert(this);
    }

    public Establishment(String id, String email, String phoneNumber, String password, String name, String address, String type, String description, SortedMap<MenuItem, Integer> menu, double income) {
        super(id, email, phoneNumber, password);
        this.name = name;
        this.address = address;
        this.type = type;
        this.description = description;
        this.menu = new TreeMap<>(menu);
        this.income = income;
       // DBCSVService.getInstance().insert(this);
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
        return new TreeMap<>(menu);
    }

    public void setMenu(SortedMap<MenuItem, Integer> menu) {
        this.menu = menu;
    }

    public void addMenuItem(MenuItem menuItem) {
        if(menu.containsKey(menuItem)){
            Integer quan = menu.get(menuItem);
            menu.put(menuItem, quan + 1);
            try {
                DBCSVService.getInstance().deleteById("data/establishment_menuItem.csv", id, 0);
            } catch(IOException e) {
                System.out.println(e.getMessage());
            }
            
        }
        else
            menu.put(menuItem, 0);
        DBCSVService.getInstance().insertAsoc(this, menuItem);
    }

    public void addQuantity(MenuItem menuItem, Integer quantity){
        if(menu.containsKey(menuItem)){
            Integer quan = menu.get(menuItem);
            menu.put(menuItem, quan + quantity);
            try {
                DBCSVService.getInstance().deleteById("data/establishment_menuItem.csv", id, 0);
            } catch(IOException e) {
                System.out.println(e.getMessage());
            }

        }
        else
            menu.put(menuItem, quantity);
        DBCSVService.getInstance().insertAsoc(this, menuItem);
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

    public String toCSV() {
        return  super.toCSV() + ',' +
                this.name + ',' +
                this.address + ',' +
                this.type + ',' +
                this.description + ',' +
                this.income;
    }

    public static Establishment readFromCSV(String csv) {
        String[] data = csv.split(",");
        String id = data[0];
        String email = data[1];
        String phoneNumber = data[2];
        String password = data[3];
        String name = data[4];
        String address = data[5];
        String type = data[6];
        String description = data[7];
        double income = Double.parseDouble(data[8]);

        SortedMap<MenuItem, Integer>menu = DBCSVService.getInstance(1).readMenuFromCSV(id, "establishment_menuItem");

        return new Establishment(id, email, phoneNumber, password, name, address, type, description, menu, income);
    }
}
