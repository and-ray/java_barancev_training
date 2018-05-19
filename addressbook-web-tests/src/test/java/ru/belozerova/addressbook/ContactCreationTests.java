package ru.belozerova.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase{


    @Test
    public void testContactCreation() {
        gotoHomePage();
        initContactCreation();
        fillContactForm(new ContactData("Alfa", "Beta", "Earth", "+71234567890", "alfa@beta.com"));
        submitContactCreation();
        returnToHomePage();
    }

}
