package ru.belozerova.addressbook.tests;

import org.testng.annotations.Test;
import ru.belozerova.addressbook.TestBase;
import ru.belozerova.addressbook.model.ContactData;
import ru.belozerova.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {


    @Test //(enabled = false)
    public void testContactCreation() {
        app.gotoHomePage();
        Contacts before = app.contact().all();
        File photo = new File("src/test/resources/icon001.png");
        ContactData contact = new ContactData().withFirstName("Alfa")
                .withLastName("Beta")
                .withAddress("Earth")
                .withMobilePhone("+71234567890")
                .withEmail("alfa@beta.com").withPhoto(photo);
        app.contact().create(contact);
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((g)-> g.getId()).max().getAsInt()))));
    }
    @Test (enabled = false)
    public void testCurrentDir() { //определяем текущую папку.
        //File currentDir = new File(".");
        //System.out.println(currentDir.getAbsolutePath());
        File photo = new File("src/test/resources/icon001.png");
        System.out.println(photo.getAbsolutePath());
        System.out.println(photo.exists());

    }
}
