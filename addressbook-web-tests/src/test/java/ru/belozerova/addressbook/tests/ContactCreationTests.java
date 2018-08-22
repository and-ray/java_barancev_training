package ru.belozerova.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.belozerova.addressbook.TestBase;
import ru.belozerova.addressbook.model.ContactData;
import ru.belozerova.addressbook.model.Contacts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {
    @DataProvider
    public Iterator<Object[]> validContactsFromJson() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")));
        String json = "";
        String line = reader.readLine(); //чтение 1 строки
        while (line!=null) {
            json += line;
            line = reader.readLine();
        }
        Gson gson = new Gson();
        List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>(){}.getType());
        // из-за типа данных приходится указывать всю конструкцию - список из объектов.

        return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
//1я часть - из потока из списка groups к каждому объекту применяем функцию - заворачиваем
// объект в массив из 1ого элемента-этого объекта
//2я часть - обратно из потока собираем-превращаем все это в список
//3я часть - итератор списка возвращаем.
    }

    @Test(dataProvider = "validContactsFromJson")
    public void testContactCreation(ContactData contact) {
        app.gotoHomePage();
        Contacts before = app.db().contacts();
        //File photo = new File("src/test/resources/icon001.png");
        app.goTo().gotoHomePage();
        app.contact().create(contact);
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((g)-> g.getId()).max().getAsInt()))));
    }

    @Test (enabled = false)
    public void testCurrentDir() { //определяем текущую папку.
        //File currentDir = new File(".");
        //System.out.println(currentDir.getAbsolutePath());
        File photo = new File("src/test/resources/icon001.png");
        System.out.println(photo.getAbsolutePath());
        System.out.println(photo.exists());
    }
}
