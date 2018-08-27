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

public class AddContactToGroupTests extends TestBase {
    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().GroupPage(); //если нет группы тест1 - варганю ее.
            app.group().create(new GroupData().withName("test1").withHeader("hiiiii").withFooter("foooo"));
        }
        if (app.db().contacts().size() == 0) {
            app.gotoHomePage();
            app.contact().create(new ContactData().withFirstName("Alfa")
                    .withLastName("Beta")
                    .withAddress("Earth")
                    .withMobilePhone("+71234567890")
                    .withEmail("alfa@beta.com"));
        }
    }

    @Test
    public void testAddContactToGroup() {
        Contacts beforeContacts = app.db().contacts(); // ОБЪЕКТЫ ЦЕЛИКОМ!!!
        ContactData addingToGroupContact = beforeContacts.iterator().next();
        Groups beforeGroups = app.db().groups();
        GroupData groupForAddingContact = beforeGroups.iterator().next();
        app.gotoHomePage();
        app.contact().selectContactById(addingToGroupContact.getId());
        app.group().selectGroupToIncludeContact(groupForAddingContact);
        app.contact().addContactToGroup();
        Contacts afterContacts = app.db().contacts();
        assertThat(afterContacts, equalTo(beforeContacts.withAdded(addingToGroupContact)));//
        //String ourGroup = afterContacts
        //System.out.println("ooooh" + app.db().contacts(addingToGroupContact.getId()).getGroups());
        //assertThat(app.db().contacts().addingToGroupContact.getGroups(),equalTo(groupForAddingContact));
        //assertThat(addingToGroupContact.inGroup()),equalTo(groupForAddingContact));
    }
}
