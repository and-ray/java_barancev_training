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

public class RemoveContactFromGroupTests extends TestBase {
        @BeforeMethod
        public void ensurePreconditions() {
            if (app.db().groups().size() == 0) {
                app.goTo().GroupPage(); //если нет группы тест1 - варганю ее.
                app.group().create(new GroupData().withName("test1").withHeader("hiiiii").withFooter("foooo"));
            }
        }

        @Test
        public void testRemoveContactFromGroup() {
            Groups groups = app.db().groups();
            Contacts beforeContacts = app.db().contacts();
            GroupData ourGroup = groups.iterator().next(); //- все, а не только наша.
            app.gotoHomePage();
                ContactData removingContact = new ContactData().withFirstName("Alfa")
                        .withLastName("Beta")
                        .withAddress("Earth")
                        .withMobilePhone("+71234567890")
                        .withEmail("alfa@beta.com").inGroup(ourGroup);
            app.contact().create(removingContact);

            removingContact = beforeContacts.iterator().next();
            app.group().selectGroupToCheckIncludedContacts(ourGroup);
            //app.contact().selectContactById(contacts.iterator().next());    // ?
            app.contact().removeContactFromGroup();

            app.gotoHomePage();
            Contacts afterContacts = app.db().contacts();
            assertThat(afterContacts, equalTo(beforeContacts.without(removingContact)));
           // assertThat(removingContact.getGroups(), !equals(ourGroup));
        }
    }

