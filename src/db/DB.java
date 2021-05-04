package db;

import Account.Account;
import Account.Establishment;
import Account.Courier;
import Account.Customer;
import Delivery.Delivery;
import MenuItem.MenuItem;
import Order.Order;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.*;

public class DB {

    private static DB single_instance = null;

    public List<Account> accountList;
    public List<Order> orderList;
    public List<Delivery> deliveryList;
    public List<MenuItem> menuItemList;

    public Account getAccountById(String id) {
        for(Account account: accountList) {
            if(account.getId().equals(id))
                return account;
        }
        return null;
    }

    public Order getOrderById(String id) {
        for(Order order: orderList) {
            if(order.getId().equals(id))
                return order;
        }
        return null;
    }

    public int minCourierSalary;
    private DB() {

        this.minCourierSalary = 4000;

        menuItemList = DBCSVService.getInstance(1).menuItemList;
      //  System.out.println("menu item list loaded");
        orderList = DBCSVService.getInstance(3).orderList;
       // System.out.println("order list loaded");
        deliveryList = DBCSVService.getInstance(4).deliveryList;
       // System.out.println("delivery list loaded");
        accountList = DBCSVService.getInstance(5).accountList;
       // System.out.println("account list loaded");
//        SortedMap<MenuItem, Integer> menuMcDonalds = new TreeMap<>();
//
//
//
//        MenuItem cheeseburger = new MenuItem("Cheeseburger", 20);
//        MenuItem chickenNuggets = new MenuItem("Chicken Nuggets", 30);
//        MenuItem cola = new MenuItem("Cola", 15);
//
//        menuItemList.add(cheeseburger);
//        menuItemList.add(chickenNuggets);
//        menuItemList.add(cola);
//
//        menuMcDonalds.put(cheeseburger, 1000);
//        menuMcDonalds.put(chickenNuggets, 200);
//        menuMcDonalds.put(cola, 1000);
//
//        Establishment mcDonalds = new Establishment("id1", "mcdonalds@gmail.com", "030303", "parolamcdonalds","McDonalds", "Padurii 10", "fast-food", "gustos", menuMcDonalds, 0);
//
//        SortedMap<MenuItem, Integer> menuLaPlacinte = new TreeMap<>();
//
//        MenuItem placintaCuBranza = new MenuItem("Placinta cu branza", 25);
//        MenuItem mamaliga = new MenuItem("Mamaliga", 20);
//        MenuItem vinRosu = new MenuItem("Vin Rosu", 35);
//
//        menuItemList.add(placintaCuBranza);
//        menuItemList.add(mamaliga);
//        menuItemList.add(vinRosu);
//
//        menuLaPlacinte.put(placintaCuBranza, 18823);
//        menuLaPlacinte.put(mamaliga, 200);
//        menuLaPlacinte.put(vinRosu, 1000);
//
//        Establishment laPlacinte = new Establishment("id2", "laPlacinte@gmail.com", "030303", "parolalaplacinte","La Placinte", "Kiev 11", "restaurant", "foarte gustos", menuLaPlacinte, 0);
//
//        SortedMap<MenuItem, Integer> menuTucano = new TreeMap<>();
//
//        MenuItem cappuccino = new MenuItem("Cappuccino", 25);
//        MenuItem cheesecake = new MenuItem("Cheesecake", 40);
//        MenuItem gingerTea = new MenuItem("Ginger Tea", 30);
//
//        menuItemList.add(cappuccino);
//        menuItemList.add(cheesecake);
//        menuItemList.add(gingerTea);
//
//        menuTucano.put(cappuccino, 100);
//        menuTucano.put(cheesecake, 100);
//        menuTucano.put(gingerTea, 100);
//
//        Establishment tucano = new Establishment("id3","tucano@gmail.com", "030303", "parolatucano","Tucano Coffee", "Bodoni 10", "cafenea", "scump", menuTucano, 0);
//
//        accountList.add(mcDonalds);
//        accountList.add(laPlacinte);
//        accountList.add(tucano);
//
//        Customer daniel = new Customer("id4","daniel@gmail.com", "068457184", "parola");
//        Customer nicu = new Customer("id5","nicu@gmail.com", "068457184", "parolaNicu");
//        Customer costea = new Customer("id6","costea@gmail.com", "068457184", "parolaCostea");
//
//        accountList.add(daniel);
//        accountList.add(nicu);
//        accountList.add(costea);
//
//        Courier paul = new Courier("id7","Paul", "Balan", "paul@gmail.com", "06845234", 4500, "car", "parolaPaul");
//        Courier dragos = new Courier("Dragos", "Rusnac", "dragos@gmail.com", "06845234", 4500, "vape car", "parolaDragos");
//        Courier laura = new Courier("Laura", "Schimbator", "laura@gmail.com", "06845234", 9000, "cute bike", "parolaLaura");
//
//        accountList.add(paul);
//        accountList.add(dragos);
//        accountList.add(laura);
//
//        Order order = new Order(new Date(), "random", daniel,tucano, new TreeMap<>());
//
//        orderList.add(order);

    }

    public void displayEstablishments() {

        List<Establishment> establishmentList = getEstablishmentList();

        for(int i = 0; i < establishmentList.size(); i++) {
            System.out.println(String.format("%x:\t%s", i + 1, establishmentList.get(i).getName()));
        }
    }

    public static DB getInstance() {
        if (single_instance == null)
            single_instance = new DB();
        return single_instance;
    }

    public List<Establishment> getEstablishmentList() {
        List<Establishment> establishmentList = new ArrayList<>();

        for(Account account: accountList) {
            if(account instanceof Establishment)
                establishmentList.add((Establishment)account);
        }
        return establishmentList;
    }

    public List<Courier> getCourierList() {
        List<Courier> courierList = new ArrayList<>();

        for(Account account: accountList) {
            if(account instanceof Courier)
                courierList.add((Courier) account);
        }
        return courierList;
    }

    public List<Customer> getCustomerList() {
        List<Customer> customerList = new ArrayList<>();

        for(Account account: accountList) {
            if(account instanceof Customer)
                customerList.add((Customer)account);
        }
        return customerList;
    }



}
