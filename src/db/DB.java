package db;

import Account.Establishment;
import Account.Courier;
import Account.Customer;
import MenuItem.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class DB {
    public List<Establishment> establishmentList;
    public List<Customer> customerList;
    public List<Courier> courierList;

    public DB() {

        List<MenuItem> menuMcDonalds = new ArrayList<MenuItem>();

        MenuItem cheeseburger = new MenuItem("Cheeseburger", 20);
        MenuItem chickenNuggets = new MenuItem("Chicken Nuggets", 30);
        MenuItem cola = new MenuItem("Cola", 15);

        menuMcDonalds.add(cheeseburger);
        menuMcDonalds.add(chickenNuggets);
        menuMcDonalds.add(cola);

        Establishment mcDonalds = new Establishment("mcdonalds@gmail.com", "030303", "parolamcdonalds","McDonalds", "Padurii 10", "fast-food", "gustos", menuMcDonalds);

        List<MenuItem> menuLaPlacinte = new ArrayList<MenuItem>();

        MenuItem placintaCuBranza = new MenuItem("Placinta cu branza", 25);
        MenuItem mamaliga = new MenuItem("Mamaliga", 20);
        MenuItem vinRosu = new MenuItem("Vin Rosu", 35);

        menuLaPlacinte.add(placintaCuBranza);
        menuLaPlacinte.add(mamaliga);
        menuLaPlacinte.add(vinRosu);

        Establishment laPlacinte = new Establishment("laPlacinte@gmail.com", "030303", "parolalaplacinte","La Placinte", "Kiev 11", "restaurant", "foarte gustos", menuLaPlacinte);

        List<MenuItem> menuTucano = new ArrayList<MenuItem>();

        MenuItem cappuccino = new MenuItem("Cappuccino", 25);
        MenuItem cheesecake = new MenuItem("Cheesecake", 40);
        MenuItem gingerTea = new MenuItem("Ginger Tea", 30);

        menuTucano.add(placintaCuBranza);
        menuTucano.add(mamaliga);
        menuTucano.add(vinRosu);

        Establishment tucano = new Establishment("tucano@gmail.com", "030303", "parolatucano","Tucano Coffee", "Bodoni 10", "cafenea", "scump", menuTucano);

        establishmentList.add(mcDonalds);
        establishmentList.add(laPlacinte);
        establishmentList.add(tucano);

        Customer daniel = new Customer("avramdaniel@gmail.com", "068457184", "parola");
        Customer nicu = new Customer("nicu@gmail.com", "068457184", "parolaNicu");
        Customer costea = new Customer("costea@gmail.com", "068457184", "parolaCostea");

        customerList.add(daniel);
        customerList.add(nicu);
        customerList.add(costea);

        Courier paul = new Courier("Paul", "Balan", "paul@gmail.com", "06845234", 4500, "car", "parolaPaul");
        Courier dragos = new Courier("Dragos", "Rusnac", "dragos@gmail.com", "06845234", 4500, "vape car", "parolaDragos");
        Courier laura = new Courier("Laura", "Schimbator", "laura@gmail.com", "06845234", 9000, "cute bike", "parolaLaura");

        courierList.add(paul);
        courierList.add(dragos);
        courierList.add(laura);

    }

}
