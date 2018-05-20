package ru.belozerova.addressbook.tests;

import org.testng.annotations.Test;
import ru.belozerova.addressbook.TestBase;
import ru.belozerova.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {
    @Test
    public void testContactModification() {
        app.gotoHomePage();
        app.getContactHelper().editContact();
        app.getContactHelper().fillContactForm(new ContactData("Alfa", "Beta", "Earth", "+71234567890", "alfa@beta.com"));
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToHomePage();
    }
}
