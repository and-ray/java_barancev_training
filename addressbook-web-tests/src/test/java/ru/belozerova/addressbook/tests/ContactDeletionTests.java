package ru.belozerova.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.belozerova.addressbook.TestBase;
import ru.belozerova.addressbook.model.ContactData;
import ru.belozerova.addressbook.model.Contacts;
import ru.belozerova.addressbook.model.GroupData;
import ru.belozerova.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDeletionTests extends TestBase {
    @BeforeMethod
    public void ensurePreconditions(){
        app.goTo().GroupPage();
        if (app.db().groups().size()==0) {
            app.goTo().GroupPage();
            app.group().create(new GroupData().withName("test1").withHeader("hiiiii").withFooter("foooo"));
        }
        Groups groups = app.db().groups();
        app.gotoHomePage();
        if (app.db().contacts().size()==0) {
            app.contact().create(new ContactData().withFirstName("Alfa")
                    .withLastName("Beta")
                    .withAddress("Earth")
                    .withMobilePhone("+71234567890")
                    .withEmail("alfa@beta.com").inGroup(groups.iterator().next()));
        }
    }
    @Test //(enabled = false)
    public void testContactDeletion() {
        Contacts before = app.db().contacts();// ОБЪЕКТЫ ЦЕЛИКОМ!!!
        ContactData deletedContact = before.iterator().next();
        app.gotoHomePage();
        app.contact().delete(deletedContact);
        assertThat(app.contact().count(), equalTo(before.size() - 1)); //считаю кол-во через ИФС страницы
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(deletedContact)));
        verifyContactListInUI();
    }
}
