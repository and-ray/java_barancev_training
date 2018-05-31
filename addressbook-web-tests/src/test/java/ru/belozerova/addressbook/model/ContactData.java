package ru.belozerova.addressbook.model;

import java.util.Objects;

public class ContactData {
    private int id;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String mobilePhone;
    private final String email;

    public ContactData(String firstName, String lastName, String address, String mobilePhone, String email) {
        this.id = Integer.MAX_VALUE;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.mobilePhone = mobilePhone;
        this.email = email;
    }
    public ContactData(int id, String firstName, String lastName, String address, String mobilePhone, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.mobilePhone = mobilePhone;
        this.email = email;
    }

    public int getId() {        return id;    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactData that = (ContactData) o;
        return id == that.id &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

}
