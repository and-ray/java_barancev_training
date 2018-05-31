package ru.belozerova.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.belozerova.addressbook.TestBase;
import ru.belozerova.addressbook.model.ContactData;

import java.util.HashSet;
import java.util.List;

public class ContactModificationTests extends TestBase {
    @Test
    public void testContactModification() {
        app.gotoHomePage();
        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData(
                    "Alfa",
                    "Beta",
                    "Earth",
                    "+71234567890",
                    "alfa@beta.com"));
        }
        System.out.println("Вид до: ");
        List<ContactData> before = app.getContactHelper().getContactList();
        int our_id = before.get(before.size() - 1).getId();
        app.getContactHelper().selectContact(before.size() - 1);
        System.out.println("before.size() - 1 = "+ (before.size() - 1));
        app.getContactHelper().editContact();
        System.out.println("new contact, our_id="+our_id);
        ContactData contact = new ContactData(our_id,"Alfa", "Beta", "Earth", "+71234567890", "alfa@beta.com");
        app.getContactHelper().fillContactForm(contact);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToHomePage();
        System.out.println("Вид после: ");
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size() - 1);
        before.add(contact);
        Assert.assertEquals(new HashSet<Object>(before),new HashSet<Object>(after));
    }
}
