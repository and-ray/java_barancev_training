package ru.belozerova.addressbook;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.belozerova.addressbook.appmanager.ApplicationManager;

public class TestBase {

    protected final ApplicationManager app = new ApplicationManager();

    @BeforeMethod
    public void setUp() throws Exception {
        app.init();
    }

    @AfterMethod
    public void tearDown() {
        app.stop();
    }

}
