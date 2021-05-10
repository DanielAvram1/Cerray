package db;

import Account.Account;
import Account.Establishment;
import Account.Courier;
import Account.Customer;
import Delivery.Delivery;
import MenuItem.MenuItem;
import Order.Order;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class DB {

    private static DB single_instance = null;

    public List<Account> accountList;
    public List<Order> orderList;
    public List<Delivery> deliveryList;

    public int minCourierSalary;
    private DB() {
//
        this.minCourierSalary = 4000;
//
//        orderList = new ArrayList<>();
//
//        accountList = new ArrayList<>();
//
//        deliveryList = new ArrayList<>();
//
//        SortedMap<MenuItem, Integer> menuMcDonalds = new TreeMap<>();
//
//        MenuItem cheeseburger = new MenuItem("Cheeseburger", 20);
//        MenuItem chickenNuggets = new MenuItem("Chicken Nuggets", 30);
//        MenuItem cola = new MenuItem("Cola", 15);
//
//        menuMcDonalds.put(cheeseburger, 1000);
//        menuMcDonalds.put(chickenNuggets, 200);
//        menuMcDonalds.put(cola, 1000);
//
//        Establishment mcDonalds = new Establishment("mcdonalds@gmail.com", "030303", "parolamcdonalds","McDonalds", "Padurii 10", "fast-food", "gustos", menuMcDonalds);
//
//        SortedMap<MenuItem, Integer> menuLaPlacinte = new TreeMap<>();
//
//        MenuItem placintaCuBranza = new MenuItem("Placinta cu branza", 25);
//        MenuItem mamaliga = new MenuItem("Mamaliga", 20);
//        MenuItem vinRosu = new MenuItem("Vin Rosu", 35);
//
//        menuLaPlacinte.put(placintaCuBranza, 18823);
//        menuLaPlacinte.put(mamaliga, 200);
//        menuLaPlacinte.put(vinRosu, 1000);
//
//        Establishment laPlacinte = new Establishment("laPlacinte@gmail.com", "030303", "parolalaplacinte","La Placinte", "Kiev 11", "restaurant", "foarte gustos", menuLaPlacinte);
//
//        SortedMap<MenuItem, Integer> menuTucano = new TreeMap<>();
//
//        MenuItem cappuccino = new MenuItem("Cappuccino", 25);
//        MenuItem cheesecake = new MenuItem("Cheesecake", 40);
//        MenuItem gingerTea = new MenuItem("Ginger Tea", 30);
//
//        menuTucano.put(cappuccino, 100);
//        menuTucano.put(cheesecake, 100);
//        menuTucano.put(gingerTea, 100);
//
//        Establishment tucano = new Establishment("tucano@gmail.com", "030303", "parolatucano","Tucano Coffee", "Bodoni 10", "cafenea", "scump", menuTucano);
//
//        accountList.add(mcDonalds);
//        accountList.add(laPlacinte);
//        accountList.add(tucano);
//
//        Customer daniel = new Customer("daniel@gmail.com", "068457184", "parola");
//        Customer nicu = new Customer("nicu@gmail.com", "068457184", "parolaNicu");
//        Customer costea = new Customer("costea@gmail.com", "068457184", "parolaCostea");
//
//        accountList.add(daniel);
//        accountList.add(nicu);
//        accountList.add(costea);
//
//        Courier paul = new Courier("Paul", "Balan", "paul@gmail.com", "06845234", 4500, "car", "parolaPaul");
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
