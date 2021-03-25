package Account;

public abstract class Worker extends Person{

    protected String jobName;
    protected int salary;

    public Worker(String email, String phoneNumber, String password, String firstName, String lastName, String jobName, int salary) {
        super(email, phoneNumber, password, firstName, lastName);
        this.jobName = jobName;
        this.salary = salary;
    }

    String getJobName() {
        return this.jobName;
    }

    int getSalary() {
        return this.salary;
    }
    void setSalary(int salary) {
        this.salary = salary;
    }

}
