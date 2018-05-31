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
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(before.size() - 1);
        app.getContactHelper().editContact();
        ContactData contact = new ContactData(before.get(before.size() - 1).getId(),"Alfa", "Beta", "Earth", "+71234567890", "alfa@beta.com");
        app.getContactHelper().fillContactForm(contact);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size() - 1);
        before.add(contact);
        Assert.assertEquals(new HashSet<Object>(before),new HashSet<Object>(after));
    }
}
