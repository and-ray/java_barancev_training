package ru.belozerova.addressbook.tests;

import org.testng.annotations.Test;
import ru.belozerova.addressbook.model.ContactData;
import ru.belozerova.addressbook.TestBase;

public class ContactCreationTests extends TestBase {


    @Test
    public void testContactCreation() {
        app.gotoHomePage();
        app.getContactHelper().initContactCreation();
        app.getContactHelper().fillContactForm(new ContactData(
                "Alfa",
                "Beta",
                "Earth",
                "+71234567890",
                "alfa@beta.com"));
        app.getContactHelper().submitContactCreation();
        app.getContactHelper().returnToHomePage();
    }

}
