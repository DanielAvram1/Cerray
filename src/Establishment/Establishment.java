package Establishment;

public class Establishment {
    private String name;
    private String address;
    private String type;
    private String description;

    public Establishment(String name, String address, String type, String description) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Establishment{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
