package ru.belozerova.addressbook.tests;

import org.testng.annotations.Test;
import ru.belozerova.addressbook.TestBase;

public class GroupDeletionTests extends TestBase {//

    @Test
    public void testGroupDeletion() {
        app.gotoGroupPage();
        app.selectGroup();
        app.deleteSelectedGroups();
        app.returnToGroupPage();
    }

}
