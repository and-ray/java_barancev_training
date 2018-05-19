package ru.belozerova.addressbook.tests;

import org.testng.annotations.Test;
import ru.belozerova.addressbook.TestBase;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {

        app.gotoHomePage();
        app.selectContact();
        app.deleteSelectedGroup();
        app.returnToHomePage();
    }

}
