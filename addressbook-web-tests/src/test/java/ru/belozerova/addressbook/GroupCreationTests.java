package ru.belozerova.addressbook;

import org.testng.annotations.Test;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        gotoGroupPage();
        initGroupCreation();
        fillGroupForm(new Group("test1", "test2", "test3"));
        submitGroupCreation();
        returnToGroupPage();

    }

}
