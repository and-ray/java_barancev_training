package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class RegistrationHelper extends HelperBase{

    public RegistrationHelper(ApplicationManager app) {
        super(app);
    }

    public void start(String userName, String email) {
       wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
       type(By.name("username"), userName);
       type(By.name("email"), email);
       click(By.cssSelector("input[type='submit']"));
    }

    public void finish(String confirmationLink, String password) {
       wd.get(confirmationLink);
       // System.out.println("1");
       type(By.name("password"),password);
       type(By.name("password_confirm"),password);
        click(By.cssSelector("span[class='bigger-110']"));// селектор, конечно, не айс, но спасибо хоть класс есть.

    }
}
