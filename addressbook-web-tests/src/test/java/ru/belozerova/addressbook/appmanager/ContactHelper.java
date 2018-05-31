package ru.belozerova.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.belozerova.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

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

    public void selectContact(int index) {
        wd.findElements(By.xpath("//tr[@name='entry']//input")).get(index).click();
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

    public List<ContactData> getContactList() {
        List<ContactData> contacts = new ArrayList<ContactData>();
        List <WebElement> elements = wd.findElements(By.xpath("//tr[@name='entry']//td)"));
        int i=1;
        for (WebElement element: elements)
        {
            String lastName = element.get(i).getText();
            String firstName = element.get.getText();
            int id = Integer.parseInt(element.findElement(By.xpath("//tr[@name='entry']//input")).getAttribute("value"));
            ContactData contact = new ContactData(id, firstName,lastName,null,null,null);
            contacts.add(contact);
            i+=10;
        }
        return contacts;
    }
}
