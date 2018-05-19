package ru.belozerova.addressbook;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {

        gotoHomePage();
        selectContact();
        deleteSelectedGroup();
        returnToHomePage();
    }

}
