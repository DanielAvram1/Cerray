package Account;

public class Account {
    static int nrAccounts = 0;

    protected String email;
    protected String phoneNumber;
    protected String password;

    public Account(Account account) {
        this.email = account.email;
        this.phoneNumber = account.phoneNumber;
        this.password = account.password;
    }

    public Account(String email, String phoneNumber, String password) {
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
}
