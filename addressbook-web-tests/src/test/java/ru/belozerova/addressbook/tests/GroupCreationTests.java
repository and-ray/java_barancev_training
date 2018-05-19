package ru.belozerova.addressbook.tests;

import org.testng.annotations.Test;
import ru.belozerova.addressbook.model.GroupData;
import ru.belozerova.addressbook.TestBase;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        app.gotoGroupPage();
        app.initGroupCreation();
        app.fillGroupForm(new GroupData("test1", "test2", "test3"));
        app.submitGroupCreation();
        app.returnToGroupPage();

    }

}
