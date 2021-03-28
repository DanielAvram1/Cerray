package Account;

import MenuItem.MenuItem;
import Order.Order;
import db.DB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EstablishmentService {

    private Establishment establishment;

    public Order chooseEstablishment() throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        List<Establishment> establishmentList = DB.getInstance().establishmentList;

        System.out.println("Alegeti indexul localului din care doriti sa faceti comanda, sau comanda back:");

        DB.getInstance().displayEstablishments();

        boolean contChooseEstablishmentSession = true;

        while(contChooseEstablishmentSession) {
            String input = in.readLine();
            if(input.equals("back"))
                contChooseEstablishmentSession = false;
            else {
                int idx = 0;
                try {
                    idx = Integer.parseInt(input);
                } catch (Exception e){
                    System.out.println("Nu ati introdus indexul sau comanda corect!");
                    continue;
                }

                if(idx > establishmentList.size()){
                    System.out.println("Index prea mare!");
                    continue;
                }

                establishment = establishmentList.get(idx - 1);

                System.out.println(establishment.getName());
                return makeOrder();
            }
        }
        return null;

    }

    public void displayMenu() {
        List<MenuItem> menu = establishment.menu;
        for(int i = 0; i < menu.size(); i++) {
            System.out.println(String.format("%x:\t%s\t%f", i + 1, menu.get(i).getName(), menu.get(i).getPrice(), menu.get(i).getQuantity()));
        }
    }

    public Order makeOrder() throws Exception{
        displayMenu();

        List<MenuItem> menu = establishment.menu;

        List<MenuItem> orderItems = new ArrayList<>();
        double cost = 0;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        boolean cont = true;
        while(cont) {
            input = in.readLine();
            if(input.equals("done")) {
                cont = false;
                break;
            }
            String[] args = input.split(" ");
            try {
                int idx, quan;

                idx = Integer.parseInt(args[0]);
                if(idx < 1 || idx > menu.size()) throw new Exception("prea mic sau mare indexul!");

                quan = 1;

                if(args.length == 2) {
                    idx = Integer.parseInt(args[0]);
                    quan = Integer.parseInt(args[1]);
                    if(quan < 1 || quan > 5000) throw new Exception("prea mic sau mare cantitatea!");
                }

                cost += menu.get(idx - 1).getPrice() * quan;

                MenuItem orderItem = new MenuItem(menu.get(idx - 1).getName(), menu.get(idx - 1).getPrice(), quan);
                orderItems.add(orderItem);


            } catch(Exception e) {
                System.out.println(e.getMessage() + "; pentru a termina, scrieti done");
            }
        }
        System.out.println("Costul comenzii: " + cost);
        System.out.println("Doriti sa confirmati comanda? Y/N");
        input = in.readLine();
        if(input.equals("N"))
            return null;
        return new Order(new Date(), orderItems);

    }

    private Establishment findEstablishmentInList(List<Establishment> establishmentList, String email, String password) {

        for(Establishment establishment : establishmentList) {

            if(establishment.email.equals(email) && establishment.password.equals(password))
                return establishment;
        }
        return null;
    }

    public void login() throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        List<Establishment> loginEstablishmentList = DB.getInstance().establishmentList;

        boolean contLoginSession = true;
        while(contLoginSession) {
            System.out.print("Email: ");
            String email = in.readLine();

            System.out.print("Parola: ");
            String password = in.readLine();
            Establishment findEstablishment = findEstablishmentInList(loginEstablishmentList, email, password);

            if(findEstablishment != null) {
                this.establishment = findEstablishment;
                System.out.println("Establishment Logat!");
                contLoginSession = false;
            }
            else {
                System.out.println("Ati introdus emailul sau parola gresit. Doriti sa mai incercati? Y/N");
                String input = in.readLine();
                if (input.equals("N")) {
                    contLoginSession = false;
                }
            }
        }

    }

    private boolean isEmailLoggedIn(List<Establishment> establishmentList, String email) {

        for(Establishment establishment : establishmentList) {
            if(establishment.email.equals(email))
                return true;
        }
        return false;
    }

    public void register() throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        List<Establishment> loginEstablishmentList = DB.getInstance().establishmentList;

        boolean contSession = true;
        while(contSession) {

            boolean contEmailIntroduction = true;
            String email = "";
            while(contEmailIntroduction) {
                System.out.print("Email: ");
                email = in.readLine();

                if(isEmailLoggedIn(loginEstablishmentList, email)){
                    System.out.println("Acest email e deja logat! Doriti sa continuati sesiunea de inregistrare? Y/N");
                    String input = in.readLine();
                    if(input.equals("N")) {
                        contSession = false;
                        break;
                    }
                }
                else
                    contEmailIntroduction = false;
            }

            if(!contSession) break;


            System.out.print("Numarul de telefon: ");
            String phoneNumber = in.readLine();
            System.out.print("Parola: ");
            String password = in.readLine();

            System.out.print("Denumire: ");
            String name = in.readLine();

            System.out.print("Adresa: ");
            String address = in.readLine();

            System.out.println("Tipul localului: ");
            String type = in.readLine();

            System.out.println("Descriere: ");
            String description = in.readLine();

            System.out.println("Ati introdus toate informatiile necesare. Confirmati inregistrarea? Y/N");

            String input = in.readLine();
            if(input.equals("N")) break;

            Establishment establishment = new Establishment(email, phoneNumber, password, name, address, type, description, new ArrayList<MenuItem>());
            loginEstablishmentList.add(establishment);

            this.establishment = establishment;
            System.out.println("Bun venit in Cerray! Sunteti un Establishment inregistrat!");
        }
    }

    public void addMenuItem() throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Introduceti un produs nou: ");

        System.out.println("Denumire: ");
        String name = in.readLine();

        double price = 0;
        while(true) {
            System.out.println("Pretul: ");
            String input = in.readLine();
            try {
                price = Double.parseDouble(input);
                break;

            } catch (Exception e) {
                System.out.println("Nu ati introdus pretul corect!");
            }
        }

        int quan = 0;
        while(true) {
            System.out.println("Cantitatea: ");
            String input = in.readLine();
            try {
                quan = Integer.parseInt(input);
                break;

            } catch (Exception e) {
                System.out.println("Nu ati introdus cantitatea corect!");
            }
        }

        MenuItem menuItem = new MenuItem(name, price, quan);

        this.establishment.addMenuItem(menuItem);
        System.out.println("Produsul " + name + " a fost introdus in meniul localului!");

    }

    public void session() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Bun venit, Establishment! Introduceti comanda dorita:");

        boolean contSession = true;
        while(contSession) {
            System.out.println("login\tregister\tback");
            String input = in.readLine();
            switch (input) {
                case "login" : {
                    login();
                    contSession = false;
                    break;
                }
                case "register" : {
                    register();
                    contSession = false;
                    break;
                }
                case "back" :{
                    contSession = false;
                    break;
                }

                default: {
                    System.out.println("Ati introdus comanda gresit!");
                }
            }
        }

        System.out.println("Ati intrat! Ce doriti sa faceti mai departe?:");
        contSession = true;

        while(contSession) {
            System.out.println("display_menu\tadd_item\tedit_menu\tback");
            String input = in.readLine();
            switch (input) {
                case "display_menu" : {

                    displayMenu();

                    break;
                }
                case "add_item" : {

                    this.addMenuItem();
                    break;
                }
                case "edit_menu" : {

                    contSession = false;
                    break;
                }
                case "back" :{
                    contSession = false;
                    break;
                }

                default: {
                    System.out.println("Ati introdus comanda gresit!");
                }
            }
        }

    }

}
