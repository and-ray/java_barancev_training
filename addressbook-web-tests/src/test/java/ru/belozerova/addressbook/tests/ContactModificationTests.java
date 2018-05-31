package ru.belozerova.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.belozerova.addressbook.TestBase;
import ru.belozerova.addressbook.model.ContactData;

import java.util.Comparator;
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
        app.getContactHelper().editContact(before.size() - 1);
        ContactData contact = new ContactData(before.get(before.size() - 1).getId(),"Alfa", "Beta", "Earth", "+71234567890", "alfa@beta.com");
        app.getContactHelper().fillContactForm(contact);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size() - 1);
        before.add(contact);

        Comparator<? super ContactData> byId = (c1,c2)->Integer.compare(c1.getId(),c2.getId());
        before.sort(byId);
        after.sort(byId);

        //Assert.assertEquals(new HashSet<Object>(before),new HashSet<Object>(after));
        Assert.assertEquals(before,after);
    }
}
