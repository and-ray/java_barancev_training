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
        //Groups groups = app.db().groups();
        app.gotoHomePage();
        if (app.db().contacts().size()==0) {
            app.contact().create(new ContactData().withFirstName("Alfa")
                    .withLastName("Beta")
                    .withAddress("Earth")
                    .withMobilePhone("+71234567890")
                    .withEmail("alfa@beta.com"));
        }
    }

    @Test
    public void testAddContactToGroup() {
    /*    Contacts beforeContacts = app.db().contacts();
        Groups beforeGroups = app.db().groups();
        ContactData contactBeforeAdding = beforeContacts.iterator().next();
        Groups initialGroupList = contactBeforeAdding.getGroups();
       if (initialGroupList.equals(beforeGroups)){
        app.group().create(new GroupData().withName("testNew").withHeader("hiiiii").withFooter("foooo"));}

  //      GroupData groupForAddingContact =// beforeGroups.iterator().next();//новая группа строго!
    //    app.gotoHomePage();

        int id = contactBeforeAdding.getId();
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
        System.out.println("список групп контакта из БД" + contactToCompare.getGroups());
        System.out.println("список групп исходного контакта + добавляемая группа" + addingToGroupContact.inGroup(groupForAddingContact).getGroups());
        assertThat(contactToCompare.getGroups(), equalTo(addingToGroupContact.inGroup(groupForAddingContact).getGroups()));
    */}
}
