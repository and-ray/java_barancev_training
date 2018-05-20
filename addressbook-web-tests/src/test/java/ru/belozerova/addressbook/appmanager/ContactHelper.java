package ru.belozerova.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.belozerova.addressbook.model.ContactData;

public class ContactHelper {

    private FirefoxDriver wd;

    public ContactHelper(FirefoxDriver wd) {

        this.wd = wd;
    }

    public void returnToHomePage() {
        wd.findElement(By.linkText("home")).click();
    }

    public void submitContactCreation() {
        wd.findElement(By.xpath("//div[@id='content']/form/input[21]")).click();
    }

    public void fillContactForm(ContactData contactData) {
        wd.findElement(By.name("firstname")).click();
        wd.findElement(By.name("firstname")).clear();
        wd.findElement(By.name("firstname")).sendKeys(contactData.getFirstName());
        wd.findElement(By.name("lastname")).click();
        wd.findElement(By.name("lastname")).clear();
        wd.findElement(By.name("lastname")).sendKeys(contactData.getLastName());
        wd.findElement(By.name("address")).click();
        wd.findElement(By.name("address")).clear();
        wd.findElement(By.name("address")).sendKeys(contactData.getAddress());
        wd.findElement(By.name("mobile")).click();
        wd.findElement(By.name("mobile")).clear();
        wd.findElement(By.name("mobile")).sendKeys(contactData.getMobilePhone());
        wd.findElement(By.name("email")).click();
        wd.findElement(By.name("email")).clear();
        wd.findElement(By.name("email")).sendKeys(contactData.getEmail());
    }

    public void initContactCreation() {
        wd.findElement(By.linkText("add new")).click();
    }

    public void deleteSelectedContacts() {
        String winHandleBefore = wd.getWindowHandle();
       wd.findElement(By.xpath("//div[@id='content']/form[2]/div[2]/input")).click();//нажатие на кнопку удалить
       wd.switchTo().alert().accept();
       wd.switchTo().window(winHandleBefore);
    }

    public void selectContact() {
        wd.findElement(By.xpath("(//tr[@name='entry'])[1]//input")).click(); //клик на чекбокс 1ого контакта
    }
}
