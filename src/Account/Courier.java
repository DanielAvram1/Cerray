package Account;

public class Courier extends Worker {

    private String meanOfTransport;

    public Courier(String firstName, String lastName, String email, String phoneNumber, int salary, String meanOfTransport, String password) {
        super(email, phoneNumber, password, firstName, lastName, "Courier", salary);
        this.meanOfTransport = meanOfTransport;

    }

    @Override
    public String toString() {
        return "Courier{" +
                "meanOfTransport='" + meanOfTransport + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", jobName='" + jobName + '\'' +
                ", salary=" + salary +
                '}';
    }
}
