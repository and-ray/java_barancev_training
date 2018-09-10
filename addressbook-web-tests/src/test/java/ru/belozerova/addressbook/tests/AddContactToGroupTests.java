package ru.belozerova.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.belozerova.addressbook.TestBase;
import ru.belozerova.addressbook.model.ContactData;
import ru.belozerova.addressbook.model.Contacts;
import ru.belozerova.addressbook.model.GroupData;
import ru.belozerova.addressbook.model.Groups;

import java.util.Objects;

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
        if (app.db().contacts().size() == 0) {
            app.contact().create(new ContactData().withFirstName("Alfa") //контакт создается без группы
                    .withLastName("Beta")
                    .withAddress("Earth")
                    .withMobilePhone("+71234567890")
                    .withEmail("alfa@beta.com"), false);
        }
    }

    @Test
    public void testAddContactToGroup() {
        Contacts beforeContacts = app.db().contacts();
        ContactData contactBeforeAdding = beforeContacts.iterator().next();
        // System.out.println("contactBeforeAdding" + contactBeforeAdding);
        Groups initialGroupList = contactBeforeAdding.getGroups();
       // System.out.println("initialGroupList = " + initialGroupList);

        Groups beforeGroups = app.db().groups();
        GroupData groupToAdd = null;
        //System.out.println("beforeGroups.size() = " + beforeGroups.size());
        for (GroupData currentGroup: beforeGroups){
            //     System.out.println("группа " + currentGroup);
            if (!initialGroupList.contains(currentGroup)) {
                // System.out.println("в списке" + initialGroupList + "нет группы " + currentGroup);
                groupToAdd = currentGroup;
                break; //если группы нет у этого контакта - ок, профит, иначе ищем группу, которой в нем нет
            }
        }
        //если дошли до конца цикла, проштудировав все группы, и все они есть связаны с контактом, то groupToAdd =нулл:
        if (Objects.equals(groupToAdd, null)){
            //System.out.println("если сюда ушли, то все имеющиеся группы у контакта есть");
            app.goTo().GroupPage();
            long now = System.currentTimeMillis();
            String unrepeatableGroupName = String.format("testSpecialGroup%s", now);
            app.group().create(new GroupData().withName(unrepeatableGroupName).withHeader("hiiiii").withFooter("foooo"));
            groupToAdd = app.db().getGroupWithMaxIdFromDB();
        }
        //System.out.println("groupToAdd = " + groupToAdd);
        int id = contactBeforeAdding.getId();
        app.gotoHomePage();
        app.contact().selectContactById(id);
        app.group().selectGroupToIncludeContact(groupToAdd);
        app.contact().addContactToGroup();

        ContactData contactAfterAdding = app.db().contactFromDB(id);
        initialGroupList.add(groupToAdd);
        //System.out.println("initialGroupList.add(groupToAdd)" + initialGroupList);
        //System.out.println("contactAfterAdding.getGroups() = " + contactAfterAdding.getGroups());
        assertThat(initialGroupList, equalTo(contactAfterAdding.getGroups()));
    }
}
