package ru.belozerova.addressbook.model;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="addressbook")
public class ContactData {
    @Id
    @Column(name="id")
    private int id = Integer.MAX_VALUE;

    @Expose
    @Column(name="firstname")
    private String firstName;

    @Expose
    @Column(name="lastname")
    private String lastName;

    @Expose
    @Column(name="address")
    @Type(type="text")
    private String address;

    @Expose
    @Column(name="email")
    @Type(type="text")
    private String email;

    @Column(name="home")
    @Type(type="text")
    private String homePhone;

    @Expose
    @Column(name="mobile")
    @Type(type="text")
    private String mobilePhone;

    @Column(name="work")
    @Type(type="text")
    private String workPhone;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactData that = (ContactData) o;
        return id == that.id &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(address, that.address) &&
                Objects.equals(mobilePhone, that.mobilePhone) &&
                Objects.equals(groups, that.groups) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, lastName, address, mobilePhone, groups, email);
    }

    public ContactData inGroup(GroupData group) {
        groups.add(group);
        return this;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="address_in_groups", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<GroupData> groups = new HashSet<GroupData>();

    public Groups getGroups() {
        return new Groups(groups); //возвращаем не множество групп, а объект типа Групс, для конкретного контакта, включенного в группу.
    }


    @Transient
    private String email2;
    @Transient
    private String email3;
    @Transient
    private String allPhones;
    @Transient
    private String allEmails;

    @Column(name="photo")
    @Type(type="text")
    private String photo;

    public File getPhoto() {
        return new File(photo);
    }


    public String getAllPhones() {return allPhones; }

    public String getAllEmails() {return allEmails; }

    public int getId() {
        return id;
    }

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

    public String getHomePhone() {        return homePhone;    }

    public String getWorkPhone() {        return workPhone;    }

    public String getEmail2() {        return email2;    }

    public String getEmail3() {        return email3;    }


    public ContactData withAllPhones(String allPhones) {
        this.allPhones = allPhones;
        return this;
    }

    public ContactData withAllEmails(String allEmails) {
        this.allEmails = allEmails;
        return this;
    }

    public ContactData  withHomePhone(String homePhone) {
        this.homePhone = homePhone;
        return this;
    }

    public ContactData  withWorkPhone(String workPhone) {
        this.workPhone = workPhone;
        return this;
    }

    public ContactData withId(int id) {
        this.id = id;
        return this;
    }

    public ContactData withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ContactData withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ContactData withAddress(String address) {
        this.address = address;
        return this;
    }

    public ContactData withMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public ContactData withEmail(String email) {
        this.email = email;
        return this;
    }
    public ContactData withEmail2(String email2) {
        this.email2 = email2;
        return this;
    }
    public ContactData withEmail3(String email3) {
        this.email3 = email3;
        return this;
    }
    public ContactData withPhoto(File photo) {
        this.photo = photo.getPath();
        return this;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email ='" + email + '\'' +
                ", address ='" + address + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                '}';
    }

}
