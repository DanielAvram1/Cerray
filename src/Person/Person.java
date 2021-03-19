package Person;

public abstract class Person {

    static int nrPersons = 0;

    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;

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

    void setEmail(String email){
        this.email = email;
    }
    String getEmail(){
        return this.email;
    }

    void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    String getPhoneNumber() {
        return this.phoneNumber;
    }


}
