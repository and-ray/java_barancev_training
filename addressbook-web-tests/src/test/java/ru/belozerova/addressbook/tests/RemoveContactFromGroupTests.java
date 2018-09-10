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
                long now = System.currentTimeMillis();
                String unrepeatableGroupName = String.format("testSpecialGroup%s", now);
                app.group().create(new GroupData().withName(unrepeatableGroupName).withHeader("hiiiii").withFooter("foooo"));
            }
            app.gotoHomePage();

            boolean contactWithGroupExists=false;
            Contacts beforeContacts = app.db().contacts();
            for (ContactData currentContact: beforeContacts) { //ищем имеющийся в БД хотя бы 1 контакт хотя бы с 1й группой
                if  (currentContact.getGroups().size() > 0){
                    contactWithGroupExists = true;
                    break;}
            }
            if (app.db().contacts().size()==0 || !(contactWithGroupExists)) { //если контактов нет или у существующих контактов нет групп.
                app.contact().create(new ContactData().withFirstName("Alfa")  //контакт создается с группой.
                        .withLastName("Beta")
                        .withAddress("Earth")
                        .withMobilePhone("+71234567890")
                        .withEmail("alfa@beta.com").inGroup(app.db().groups().iterator().next()));
            }
        }

        @Test
        public void testRemoveContactFromGroup() {
            Contacts beforeContacts = app.db().contacts();
            ContactData contactBeforeRemoving = new ContactData();
            for (ContactData currentContact: beforeContacts) { //ищем имеющийся в БД контакт хотя бы с 1й группой
                if (currentContact.getGroups().size() > 0) {
                    contactBeforeRemoving = currentContact;
                    break;
                }
            }
         //   System.out.println("contactBeforeRemoving" + contactBeforeRemoving );
            Groups initialGroupList = contactBeforeRemoving.getGroups();
         //   System.out.println("initialGroupList = " + initialGroupList);

            GroupData ourGroup = initialGroupList.iterator().next(); //- любая.
            int id = contactBeforeRemoving.getId();
            app.gotoHomePage();
            app.group().selectGroupToCheckIncludedContacts(ourGroup);
            app.contact().selectContactById(id);
            app.contact().removeContactFromGroup();

            ContactData contactAfterAdding = app.db().contactFromDB(id);
            initialGroupList.remove(ourGroup);
          //  System.out.println("initialGroupList.remove(ourGroup) = " + initialGroupList);
          //  System.out.println("contactAfterAdding.getGroups() = " + contactAfterAdding.getGroups());
            assertThat(initialGroupList, equalTo(contactAfterAdding.getGroups()));
            }
    }

