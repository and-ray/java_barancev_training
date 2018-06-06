package ru.belozerova.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.belozerova.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("address"), contactData.getAddress());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("email"), contactData.getEmail());
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
    public void selectContactById(int id) {
        wd.findElement(By.xpath("//tr[@name='entry']//input[@id='"+id+"']")).click();
    }

    public void editContact(int index) {
        wd.findElements(By.xpath("//a/img[@title='Edit']")).get(index).click();
    }
    public void editContactById(int id) {
        wd.findElement(By.xpath("(//input[@id='"+id+"']/../following-sibling::td/a)[3]")).click();
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public boolean isThereAContact() {
        return isElementPresent(By.xpath("(//tr[@name='entry'])[1]//input"));
    }

    public void create(ContactData contacts) {
        initContactCreation();
        fillContactForm(contacts);
        submitContactCreation();
        returnToHomePage();
    }

    public int getContactCount() {
        return wd.findElements((By.xpath("//tr[@name='entry']//input"))).size();
    }

    public List<ContactData> list() {
        List<ContactData> contacts = new ArrayList<ContactData>();
        List<WebElement> elements = wd.findElements(By.xpath("//tr[@name='entry']"));
        for (WebElement element : elements) {
            List<WebElement> oneStringOfCells = element.findElements(By.cssSelector("td"));
            String name = oneStringOfCells.get(2).getText();
            //System.out.println("имя " + name);
            String lastName = oneStringOfCells.get(1).getText();
            //System.out.println("фамилия " + lastName);
            int id = Integer.parseInt(element.findElement(By.cssSelector("input")).getAttribute("value"));
            //System.out.println("id = "+id);
            ContactData contact = new ContactData().withId(id).withFirstName(name).withLastName(lastName);
            contacts.add(contact);
        }
        return contacts;
    }

    public Set<ContactData> all() {
       Set<ContactData> contacts = new HashSet<ContactData>();
        List<WebElement> elements = wd.findElements(By.xpath("//tr[@name='entry']"));
        for (WebElement element : elements) {
            List<WebElement> oneStringOfCells = element.findElements(By.cssSelector("td"));
            String name = oneStringOfCells.get(2).getText();
            //System.out.println("имя " + name);
            String lastName = oneStringOfCells.get(1).getText();
            //System.out.println("фамилия " + lastName);
            int id = Integer.parseInt(element.findElement(By.cssSelector("input")).getAttribute("value"));
            //System.out.println("id = "+id);
            ContactData contact = new ContactData().withId(id).withFirstName(name).withLastName(lastName);
            contacts.add(contact);
        }
        return contacts;
    }
    public void modify(ContactData contact) {
       editContactById(contact.getId());
       fillContactForm(contact);
       submitContactModification();
       returnToHomePage();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContacts();
        returnToHomePage();
    }
}
