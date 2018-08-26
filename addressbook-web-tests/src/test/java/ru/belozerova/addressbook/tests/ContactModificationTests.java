package ru.belozerova.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.belozerova.addressbook.TestBase;
import ru.belozerova.addressbook.model.ContactData;
import ru.belozerova.addressbook.model.Contacts;
import ru.belozerova.addressbook.model.GroupData;
import ru.belozerova.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase {
    @BeforeMethod
    public void ensurePreconditions(){
        app.goTo().GroupPage();
        if (app.db().groups().size()==0) {
            app.goTo().GroupPage();
            app.group().create(new GroupData().withName("test1").withHeader("hiiiii").withFooter("foooo"));
        }
        Groups groups = app.db().groups();
        app.gotoHomePage();
        if (app.db().contacts().size() == 0)  {
            app.goTo().gotoHomePage();
            app.contact().create(new ContactData().withFirstName("Alfa")
                    .withLastName("Beta")
                    .withAddress("Earth")
                    .withMobilePhone("+71234567890")
                    .withEmail("alfa@beta.com").inGroup(groups.iterator().next()));
        }
    }
    @Test //(enabled = false)
    public void testContactModification() {
        Groups groups = app.db().groups();
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId())
                .withFirstName("Alfa")
                .withLastName("Beta")
                .withAddress("Earth")
                .withMobilePhone("+71234567890")
                .withEmail("alfa@beta.com")
                .inGroup(groups.iterator().next());

        app.goTo().gotoHomePage();
        app.contact().modify(contact);
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
        verifyContactListInUI();
        }
}
