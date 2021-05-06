package Account;

import java.util.UUID;

public class Account extends db.DBEntity {
    static int nrAccounts = 0;

    protected String email;
    protected String phoneNumber;
    protected String password;

    public Account(Account account) {
        super();
        this.email = account.email;
        this.phoneNumber = account.phoneNumber;
        this.password = account.password;
    }

    public Account( String email, String phoneNumber, String password) {
        super();
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public Account(String id, String email, String phoneNumber, String password) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public static int getNrAccounts() {
        return nrAccounts;
    }

    public static void setNrAccounts(int nrAccounts) {
        Account.nrAccounts = nrAccounts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected String toCSV() {
        return  this.id + ',' +
                this.email + ',' +
                this.phoneNumber + ',' +
                this.password;
    }


}
