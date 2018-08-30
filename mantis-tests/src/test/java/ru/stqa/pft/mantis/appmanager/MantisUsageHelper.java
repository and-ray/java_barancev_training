package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class MantisUsageHelper extends HelperBase{

        public MantisUsageHelper(ApplicationManager app) {
            super(app);
        }

        public void start(String userName, String password) {
            wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
            type(By.name("username"), userName);
            click(By.cssSelector("input[type='submit']"));
            type(By.name("password"), password);
            click(By.cssSelector("input[type='submit']"));
        }

        public void finish(String confirmationLink, String password) {
            wd.get(confirmationLink);
            type(By.name("password"),password);
            type(By.name("password_confirm"),password);
            click(By.cssSelector("span[class='bigger-110']"));// селектор, конечно, не айс, но спасибо хоть класс есть.

        }

    public void resetUserPassword(String username) {
        click(By.xpath("//a[@href=\"/mantisbt/manage_overview_page.php\"]"));
        click(By.xpath("//a[@href=\"/mantisbt/manage_user_page.php\"]"));
        click(By.xpath("//a[text()='" + username + "']"));
        click(By.xpath("//form[@id='manage-user-reset-form']//input[@type='submit']"));

    }

    public String getEmail(String userName) {
        String email = wd.findElement(By.xpath("(//div[@class='table-responsive']//following-sibling::td/a[text()='user1535660128034']/../../*)[3]")).getText();
            System.out.println("адрес пользователя: " + email);
        return email;
    }
}

