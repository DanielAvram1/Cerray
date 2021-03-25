package Account;

public abstract class Person extends Account{

    static int nrPersons = 0;

    protected String firstName;
    protected String lastName;

    public Person(String email, String phoneNumber, String password, String firstName, String lastName) {
        super(email, phoneNumber, password);
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    String getFirstName(){
        return this.firstName;
    }

    void setLastName(String lastName){
        this.lastName = lastName;
    }
    String getLastName(){
        return this.lastName;
    }

}
