package Person;

public abstract class Worker extends Person{

    protected String jobName;
    protected int salary;

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
