package business;

import java.io.Serial;
import java.io.Serializable;

public abstract class Person implements Serializable {
    @Serial
    private static final long serialVersionUID = 3665880920647848288L;

    private String firstName;
    private String lastName;
    private String telephone;
    private Address address;

    public Person(String firstName, String lastName, String telephone, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephone = telephone;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTelephone() {
        return telephone;
    }

    public Address getAddress() {
        return address;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
