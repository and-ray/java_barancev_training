package ru.belozerova.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.belozerova.addressbook.TestBase;
import ru.belozerova.addressbook.model.GroupData;
import ru.belozerova.addressbook.model.Groups;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validGroupsFromXml() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.xml")));
        String xml = "";
        String line = reader.readLine(); //чтение 1 строки
        while (line!=null) {
            xml += line;
            line = reader.readLine();
        }
        XStream xstream = new XStream();
        xstream.processAnnotations(GroupData.class);
        List <GroupData> groups = (List<GroupData>)xstream.fromXML(xml);

        return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
//1я часть - из потока из списка groups к каждому объекту применяем функцию - заворачиваем
// объект в массив из 1ого элемента-этого объекта
//2я часть - обратно из потока собираем-превращаем все это в список
//3я часть - итератор списка возвращаем.
    }

    @Test(dataProvider = "validGroupsFromXml")
    public void testGroupCreation(GroupData group) {
        //GroupData group = new GroupData().withName(name).withHeader(header).withFooter(footer);
        app.goTo().GroupPage();
        Groups before = app.db().groups();
        app.goTo().GroupPage();
        app.group().create(group);
        assertThat(app.group().count(), equalTo(before.size() + 1));
        Groups after = app.db().groups();
        assertThat(after,equalTo(
                before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
        verifyGroupListInUI();
    }
    @Test(enabled = false)
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
