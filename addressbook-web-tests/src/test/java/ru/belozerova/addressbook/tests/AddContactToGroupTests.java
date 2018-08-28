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
            app.goTo().GroupPage(); //если нет группы тест1 - создаю ее.
            app.group().create(new GroupData().withName("test1").withHeader("hiiiii").withFooter("foooo"));
        }
    }

    @Test
    public void testAddContactToGroup() {
        ContactData addingToGroupContact = new ContactData().withFirstName("Alfa")
                .withLastName("Beta")
                .withAddress("Earth")
                .withMobilePhone("+71234567890")
                .withEmail("alfa@beta.com");
        app.contact().create(addingToGroupContact);

        Groups beforeGroups = app.db().groups();
        GroupData groupForAddingContact = beforeGroups.iterator().next();
        app.gotoHomePage();

        int id = app.db().getContactLastId(addingToGroupContact);
        app.contact().selectContactById(id);
        app.group().selectGroupToIncludeContact(groupForAddingContact);
        app.contact().addContactToGroup();
        Contacts afterContacts = app.db().contacts();
        ContactData contactToCompare = new ContactData();
        for(ContactData contact: afterContacts){
            //System.out.println("contact.getId() = " + contact.getId());
            //System.out.println("addingToGroupContact.getId() = " + addingToGroupContact.getId());
            if (contact.getId() == id){
                contactToCompare = contact;
                break;
            }
        }
        assertThat(contactToCompare.getGroups(), equalTo(addingToGroupContact.inGroup(groupForAddingContact).getGroups()));
    }
}
