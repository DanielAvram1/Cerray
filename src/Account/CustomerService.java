package Account;

import Order.Order;
import db.DB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CustomerService {
    private Customer customer;

    private Customer findCustomerInList(List<Customer> customerList, String email, String password) {

        for(Customer customer : customerList) {

            if(customer.email.equals(email) && customer.password.equals(password))
                return customer;
        }
        return null;
    }

    private boolean isEmailLoggedIn(List<Customer> customerList, String email) {

        for(Customer customer : customerList) {
            if(customer.email.equals(email))
                return true;
        }
        return false;
    }

    public void register() throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        List<Customer> loginCustomerList = DB.getInstance().customerList;

        boolean contRegisterSession = true;
        while(contRegisterSession) {

            boolean contEmailIntroduction = true;
            String email = "";
            while(contEmailIntroduction) {
                System.out.print("Email: ");
                email = in.readLine();

                if(isEmailLoggedIn(loginCustomerList, email)){
                    System.out.println("Acest email e deja logat! Doriti sa continuati sesiunea de inregistrare? Y/N");
                    String input = in.readLine();
                    if(input.equals("N")) {
                        contRegisterSession = false;
                        break;
                    }
                }
                else
                    contEmailIntroduction = false;
            }

            if(!contRegisterSession) break;


            System.out.print("Numarul de telefon: ");
            String phoneNumber = in.readLine();
            System.out.print("Parola: ");
            String password = in.readLine();

            System.out.print("Prenume: ");
            String firstName = in.readLine();
            System.out.print("Nume: ");
            String lastName = in.readLine();

            System.out.println("Ati introdus toate informatiile necesare. Confirmati inregistrarea? Y/N");

            String input = in.readLine();
            if(input.equals("N")) break;

            Customer customer = new Customer(firstName, lastName, email, phoneNumber, password);
            loginCustomerList.add(customer);

            this.customer = customer;
            System.out.println("Bun venit in Cerray! Sunteti un Customer inregistrat!");
        }
    }

    public void login() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        List<Customer> loginCustomerList = DB.getInstance().customerList;

        boolean contLoginSession = true;
        while(contLoginSession) {
            System.out.print("Email: ");
            String email = in.readLine();

            System.out.print("Parola: ");
            String password = in.readLine();
            Customer findCustomer = findCustomerInList(loginCustomerList, email, password);

            if(findCustomer != null) {
                this.customer = findCustomer;
                System.out.println("Customer Logat!");
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

    public void session() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Bun venit, Customer! Introduceti comanda dorita:");


        boolean contCustomerSession = true;
        while(contCustomerSession) {
            System.out.println("login\tregister\tback");
            String input = in.readLine();
            switch (input) {
                case "login" : {
                    login();
                    contCustomerSession = false;
                    break;
                }
                case "register" : {
                    register();
                    contCustomerSession = false;
                    break;
                }
                case "back" :{
                    contCustomerSession = false;
                    break;
                }

                default: {
                    System.out.println("Ati introdus comanda gresit!");
                }
            }
        }

        System.out.println("Ati intrat! Ce doriti sa faceti mai departe?:");

        contCustomerSession = true;

        while(contCustomerSession) {
            System.out.println("order\tdisplay_orders\tback");
            String input = in.readLine();
            switch (input) {
                case "order" : {
                    EstablishmentService establishmentService= new EstablishmentService();
                    Order order = establishmentService.chooseEstablishment();

                    if(order != null) {
                        this.customer.addOrder(order);
                        System.out.println("Ati facut comanda!");
                    }

                    break;
                }
                case "display_orders" : {

                    contCustomerSession = false;
                    break;
                }
                case "back" :{
                    contCustomerSession = false;
                    break;
                }

                default: {
                    System.out.println("Ati introdus comanda gresit!");
                }
            }
        }

    }
}
