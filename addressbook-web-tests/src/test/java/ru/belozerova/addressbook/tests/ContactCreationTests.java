package ru.belozerova.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.belozerova.addressbook.model.ContactData;
import ru.belozerova.addressbook.TestBase;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {


    @Test (enabled = false)
    public void testContactCreation() {
        app.gotoHomePage();
        List<ContactData> before = app.contact().list();
        ContactData contact = new ContactData().withFirstName("Alfa")
                .withLastName("Beta")
                .withAddress("Earth")
                .withMobilePhone("+71234567890")
                .withEmail("alfa@beta.com");
        app.contact().create(contact);
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size() + 1);

        before.add(contact);
        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(before,after);
    }
}
