package ru.belozerova.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.belozerova.addressbook.TestBase;
import ru.belozerova.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailTests extends TestBase {
    @BeforeMethod
    public void ensurePreconditions(){
        app.gotoHomePage();
        if (app.db().contacts().size()==0) {
            app.goTo().gotoHomePage();
            app.contact().create(new ContactData().withFirstName("Alfa")
                    .withLastName("Beta")
                    .withAddress("Earth Moscow Cremlin")
                    .withMobilePhone("+71234567890")
                    .withEmail("alfa@beta.com")
                    .withEmail2("alfa2@beta.com")
                    .withEmail3("alfa3@beta.com")
            );
        }
    }
    @Test
    public void testContactEmails(){
        app.gotoHomePage();
        ContactData contact = app.contact().all().iterator().next();//множество контактов. выбираем один случайный из одного =)
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));

    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(),contact.getEmail2(), contact.getEmail3())
                .stream().filter((s)->!s.equals("")).collect(Collectors.joining("\n"));

    }
}
