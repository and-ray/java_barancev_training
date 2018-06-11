package ru.belozerova.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.belozerova.addressbook.TestBase;
import ru.belozerova.addressbook.model.GroupData;
import ru.belozerova.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validGroups(){
        List<Object[]> list = new ArrayList<Object[]>(); //список из массивов, в каждом массиве - набор данных для 1 запуска
        list.add(new Object[] {new GroupData().withName("test 1").withHeader("header 1").withFooter("footer 1")});//массив из одного объекта
        list.add(new Object[] {new GroupData().withName("test 2").withHeader("header 2").withFooter("footer 2")}); // инфо о каждом объекте - в toString
        list.add(new Object[] {new GroupData().withName("test 3").withHeader("header 3").withFooter("footer 3")});
        return list.iterator(); //итератор списка
    }

    @Test(dataProvider = "validGroups")
    public void testGroupCreation(GroupData group) {
        //GroupData group = new GroupData().withName(name).withHeader(header).withFooter(footer);
        app.goTo().GroupPage();
        Groups before = app.group().all();
        app.group().create(group);
        assertThat(app.group().count(), equalTo(before.size() + 1));
        Groups after = app.group().all();
        assertThat(after,equalTo(
                before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }
    @Test
    public void testBadGroupCreation() {
        app.goTo().GroupPage();
        Groups before = app.group().all();
        GroupData group = new GroupData().withName("test1'"); //.withHeader("test2").withFooter("test3");
        app.group().create(group);
        assertThat(app.group().count(), equalTo(before.size()));
        Groups after = app.group().all();
        assertThat(after,equalTo(before));
    }
}
