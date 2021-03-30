package Account;

public abstract class Worker extends Person{

    protected int salary;

    public Worker(Account account, String firstName, String lastName, int salary) {
        super(account, firstName, lastName);
        this.salary = salary;
    }

    public Worker(String email, String phoneNumber, String password, String firstName, String lastName, int salary) {
        super(email, phoneNumber, password, firstName, lastName);
        this.salary = salary;
    }

    int getSalary() {
        return this.salary;
    }
    void setSalary(int salary) {
        this.salary = salary;
    }

}
