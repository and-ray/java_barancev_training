package ru.belozerova.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.belozerova.addressbook.TestBase;
import ru.belozerova.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;

public class ContactPhoneTests extends TestBase {
    @BeforeMethod
    public void ensurePreconditions(){
        app.gotoHomePage();
        if (app.contact().all().size()==0) {
            app.contact().create(new ContactData().withFirstName("Alfa")
                    .withLastName("Beta")
                    .withAddress("Earth")
                    .withMobilePhone("+71234567890")
                    .withEmail("alfa@beta.com"));
        }
    }
    @Test
    public void testContactPhones(){
        app.gotoHomePage();
        ContactData contact = app.contact().all().iterator().next();//множество контактов. выбираем один случайный из одного =)
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
        //assertThat(contact.getMobilePhone(), equalTo(cleaned(contactInfoFromEditForm.getMobilePhone())));
        //assertThat(contact.getWorkPhone(), equalTo(cleaned(contactInfoFromEditForm.getWorkPhone())));
    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomePhone(),contact.getMobilePhone(), contact.getWorkPhone())
                .stream().filter((s)->!s.equals(""))
                .map(ContactPhoneTests::cleaned)
                .collect(Collectors.joining("\n"));

    }

    public static String cleaned(String phone) {
        return phone.replaceAll("\\s","").replaceAll("-()","");//пробел,-()
}
}
