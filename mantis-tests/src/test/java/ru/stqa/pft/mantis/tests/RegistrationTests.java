package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;

public class RegistrationTests extends TestBase{

    @Test
    public static void testRegistration(){
        app.registration().start("user1", "user1@localhost.localdomain");
        //app.registration().stop();
    }
}
