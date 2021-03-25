package Account;

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

        System.out.print("Email: ");
        String email = in.readLine();

        if(isEmailLoggedIn(loginCustomerList, email))
            throw new IOException("Acest email e deja logat!");

        System.out.print("Numarul de telefon: ");
        String phoneNumber = in.readLine();
        System.out.print("Parola: ");
        String password = in.readLine();

        System.out.print("Prenume: ");
        String firstName = in.readLine();
        System.out.print("Nume: ");
        String lastName = in.readLine();

        Customer customer = new Customer(firstName, lastName, email, phoneNumber, password);
        loginCustomerList.add(customer);

        this.customer = customer;


    }

    public void login() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        List<Customer> loginCustomerList = DB.getInstance().customerList;

        System.out.print("Email: ");
        String email = in.readLine();

        System.out.print("Parola: ");
        String password = in.readLine();
        Customer findCustomer = findCustomerInList(loginCustomerList, email, password);

        if(findCustomer != null) {
            this.customer = findCustomer;
            System.out.println("Customer Logat!");
        }
        else throw new Exception("Ati introdus email sau parola gresit!");


    }
}
