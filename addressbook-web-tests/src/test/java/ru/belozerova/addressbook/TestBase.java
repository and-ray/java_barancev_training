package ru.belozerova.addressbook;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.belozerova.addressbook.appmanager.ApplicationManager;
import ru.belozerova.addressbook.model.ContactData;
import ru.belozerova.addressbook.model.Contacts;
import ru.belozerova.addressbook.model.GroupData;
import ru.belozerova.addressbook.model.Groups;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBase {

    protected static final ApplicationManager app
            = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite
    public void tearDown() {
        app.stop();

    }

    public void verifyGroupListInUI() {
        if (Boolean.getBoolean("verifyUI")) {
            Groups dbGroups = app.db().groups();
            Groups uiGroups = app.group().all();
            assertThat(uiGroups, equalTo(dbGroups.stream()
                    .map((g) -> new GroupData().withId(g.getId()).withName(g.getName()))
                    .collect(Collectors.toSet())));
        }
    }
    public void verifyContactListInUI() {
        if (Boolean.getBoolean("verifyUI")) {
            Contacts dbContacts = app.db().contacts();
            //System.out.println("db" + dbContacts);
            Contacts uiContacts = app.contact().all();
            //System.out.println("ui" + uiContacts);
            assertThat(uiContacts, equalTo(dbContacts.stream()
                    .map((g) -> new ContactData().withId(g.getId()).withFirstName(g.getFirstName())
                    .withLastName(g.getLastName()).withAllPhones(g.getAllPhones()).withAllEmails(g.getAllEmails()).withAddress(g.getAddress()))
                    .collect(Collectors.toSet())));
        }
    }
}
