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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DB {

    private static DB single_instance = null;

    public List<Account> accountList;
    public List<Order> orderList;
    public List<Delivery> deliveryList;

    public int minCourierSalary;
    private DB() {

        this.minCourierSalary = 4000;

        orderList = new ArrayList<>();

        accountList = new ArrayList<>();

        deliveryList = new ArrayList<>();

        List<MenuItem> menuMcDonalds = new ArrayList<MenuItem>();

        MenuItem cheeseburger = new MenuItem("Cheeseburger", 20,100);
        MenuItem chickenNuggets = new MenuItem("Chicken Nuggets", 30,100);
        MenuItem cola = new MenuItem("Cola", 15, 100);

        menuMcDonalds.add(cheeseburger);
        menuMcDonalds.add(chickenNuggets);
        menuMcDonalds.add(cola);

        Establishment mcDonalds = new Establishment("mcdonalds@gmail.com", "030303", "parolamcdonalds","McDonalds", "Padurii 10", "fast-food", "gustos", menuMcDonalds);

        List<MenuItem> menuLaPlacinte = new ArrayList<MenuItem>();

        MenuItem placintaCuBranza = new MenuItem("Placinta cu branza", 25, 100);
        MenuItem mamaliga = new MenuItem("Mamaliga", 20, 100);
        MenuItem vinRosu = new MenuItem("Vin Rosu", 35, 100);

        menuLaPlacinte.add(placintaCuBranza);
        menuLaPlacinte.add(mamaliga);
        menuLaPlacinte.add(vinRosu);

        Establishment laPlacinte = new Establishment("laPlacinte@gmail.com", "030303", "parolalaplacinte","La Placinte", "Kiev 11", "restaurant", "foarte gustos", menuLaPlacinte);

        List<MenuItem> menuTucano = new ArrayList<MenuItem>();

        MenuItem cappuccino = new MenuItem("Cappuccino", 25, 100);
        MenuItem cheesecake = new MenuItem("Cheesecake", 40, 100);
        MenuItem gingerTea = new MenuItem("Ginger Tea", 30, 100);

        menuTucano.add(cappuccino);
        menuTucano.add(cheesecake);
        menuTucano.add(gingerTea);

        Establishment tucano = new Establishment("tucano@gmail.com", "030303", "parolatucano","Tucano Coffee", "Bodoni 10", "cafenea", "scump", menuTucano);

        accountList.add(mcDonalds);
        accountList.add(laPlacinte);
        accountList.add(tucano);

        Customer daniel = new Customer("daniel@gmail.com", "068457184", "parola");
        Customer nicu = new Customer("nicu@gmail.com", "068457184", "parolaNicu");
        Customer costea = new Customer("costea@gmail.com", "068457184", "parolaCostea");

        accountList.add(daniel);
        accountList.add(nicu);
        accountList.add(costea);

        Courier paul = new Courier("Paul", "Balan", "paul@gmail.com", "06845234", 4500, "car", "parolaPaul");
        Courier dragos = new Courier("Dragos", "Rusnac", "dragos@gmail.com", "06845234", 4500, "vape car", "parolaDragos");
        Courier laura = new Courier("Laura", "Schimbator", "laura@gmail.com", "06845234", 9000, "cute bike", "parolaLaura");

        accountList.add(paul);
        accountList.add(dragos);
        accountList.add(laura);

        Order order = new Order(new Date(), "random", daniel,tucano, new ArrayList<MenuItem>());

        orderList.add(order);

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
