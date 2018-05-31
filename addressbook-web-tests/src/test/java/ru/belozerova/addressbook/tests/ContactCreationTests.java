package ru.belozerova.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.belozerova.addressbook.model.ContactData;
import ru.belozerova.addressbook.TestBase;

import java.util.List;

public class ContactCreationTests extends TestBase {


    @Test
    public void testContactCreation() {
        app.gotoHomePage();
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().initContactCreation();
        app.getContactHelper().fillContactForm(new ContactData(
                "Alfa",
                "Beta",
                "Earth",
                "+71234567890",
                "alfa@beta.com"));
        app.getContactHelper().submitContactCreation();
        app.getContactHelper().returnToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() + 1);
    }

}
