package ru.belozerova.addressbook.tests;

import org.testng.annotations.Test;
import ru.belozerova.addressbook.TestBase;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {

        app.gotoHomePage();
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContacts();
        app.getContactHelper().returnToHomePage();
    }

}
