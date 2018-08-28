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
                app.goTo().GroupPage(); //если нет группы тест1 - создаю ее.
                app.group().create(new GroupData().withName("test1").withHeader("hiiiii").withFooter("foooo"));
            }
        }

        @Test
        public void testRemoveContactFromGroup() {
            Groups groups = app.db().groups();
            GroupData ourGroup = groups.iterator().next(); //- любая, и мы ее делаем нашей =).
            app.gotoHomePage();
                ContactData removingContact = new ContactData().withFirstName("Alfa")
                        .withLastName("Beta")
                        .withAddress("Earth")
                        .withMobilePhone("+71234567890")
                        .withEmail("alfa@beta.com").inGroup(ourGroup);
            app.contact().create(removingContact);

            int id = app.db().getContactLastId(removingContact);
            app.group().selectGroupToCheckIncludedContacts(ourGroup);
            app.contact().selectContactById(id);    // ?
            app.contact().removeContactFromGroup();
            ContactData contactToCompare = new ContactData();
            Contacts afterContacts = app.db().contacts();
            for(ContactData contact: afterContacts){
                //System.out.println("contact.getId() = " + contact.getId());
                //System.out.println("addingToGroupContact.getId() = " + addingToGroupContact.getId());
                if (contact.getId() == id){
                    contactToCompare = contact;
                    break;
                }
            }
            //System.out.println("группы контакта из базы + ликвидируемая группа" + contactToCompare.inGroup(ourGroup).getGroups());
            //System.out.println("группы исходного контакта" + removingContact.getGroups());
            assertThat(contactToCompare.inGroup(ourGroup).getGroups(), equalTo(removingContact.getGroups()));
            }
    }

