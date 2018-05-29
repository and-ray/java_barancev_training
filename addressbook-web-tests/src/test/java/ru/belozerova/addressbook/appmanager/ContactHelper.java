package ru.belozerova.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.belozerova.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void returnToHomePage() {
        click(By.linkText("home"));
    }

    public void submitContactCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void fillContactForm(ContactData contactData) {
        type(By.name("firstname"),contactData.getFirstName());
        type(By.name("lastname"),contactData.getLastName());
        type(By.name("address"),contactData.getAddress());
        type(By.name("mobile"),contactData.getMobilePhone());
        type(By.name("email"),contactData.getEmail());
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void deleteSelectedContacts() {
        String winHandleBefore = wd.getWindowHandle();
       click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));//нажатие на кнопку удалить
       wd.switchTo().alert().accept();
       wd.switchTo().window(winHandleBefore);
    }

    public void selectContact() {
        click(By.xpath("(//tr[@name='entry'])[1]//input")); //клик на чекбокс 1ого контакта
    }

    public void editContact() {
        click(By.xpath("//a/img[@title='Edit']"));
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public boolean isThereAContact() {
        return isElementPresent(By.xpath("(//tr[@name='entry'])[1]//input"));
    }

    public void createContact(ContactData contacts) {
        initContactCreation();
        fillContactForm(contacts);
        submitContactCreation();
        returnToHomePage();}

    public int getContactCount() {
    return wd.findElements((By.xpath("//tr[@name='entry']//input"))).size();
    }
}
