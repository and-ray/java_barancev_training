package ru.belozerova.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.belozerova.addressbook.model.GroupData;
import ru.belozerova.addressbook.TestBase;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        app.getNavigationHelper().gotoGroupPage();
        List<GroupData> before = app.getGroupHelper().getGroupList();
        GroupData group = new GroupData("test1", "test2", "test3");
        app.getGroupHelper().initGroupCreation();
        app.getGroupHelper().fillGroupForm(group);
        app.getGroupHelper().submitGroupCreation();
        app.getGroupHelper().returnToGroupPage();
        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size() + 1);


        //компаратор, л.4.9
        //group.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
        before.add(group);

        Comparator<? super GroupData> byId = (g1, g2)->Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(before,after);
    }

}
