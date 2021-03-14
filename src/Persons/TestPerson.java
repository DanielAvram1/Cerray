package Persons;

public class TestPerson {
    public static void main(String[] args) {
        Worker worker = new Courier("Nelu", "Cemartan", "nelu@gmail.com", "068457184", 4000, "Car");

        System.out.println(worker.getJobName());
    }
}