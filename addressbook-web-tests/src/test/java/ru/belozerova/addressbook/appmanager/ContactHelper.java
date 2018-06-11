package ru.belozerova.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.belozerova.addressbook.model.ContactData;
import ru.belozerova.addressbook.model.Contacts;

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
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("address"), contactData.getAddress());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("email"), contactData.getEmail());
        attach(By.name("photo"), contactData.getPhoto());

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


    public void selectContactById(int id) {
        wd.findElement(By.xpath("//tr[@name='entry']//input[@id='"+id+"']")).click();
    }


    public void InitContactModificationById(int id) {
        wd.findElement(By.xpath("//a[@href='edit.php?id="+id+"']")).click();

    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public boolean isThereAContact() {
        return isElementPresent(By.xpath("(//tr[@name='entry'])[1]//input"));
    }

   public int count() { return wd.findElements((By.xpath("//tr[@name='entry']//input"))).size();    }
/*
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
    }*/

    private Contacts contactCache = null;

    public Contacts all() { //список на главной странице контактов
        if (contactCache!=null){
            return new Contacts(contactCache);
        }
        contactCache = new Contacts();
        List<WebElement> elements = wd.findElements(By.xpath("//tr[@name='entry']"));

        for (WebElement element : elements) {
            List<WebElement> oneStringOfCells = element.findElements(By.cssSelector("td"));
            String lastName = oneStringOfCells.get(1).getText();
            String name = oneStringOfCells.get(2).getText();
            String address = oneStringOfCells.get(3).getText();
            String allEmails = oneStringOfCells.get(4).getText();
            String allPhones = oneStringOfCells.get(5).getText();
            //System.out.println("allEmails: "+allEmails);
            //System.out.println("address: "+address);
            int id = Integer.parseInt(element.findElement(By.cssSelector("input")).getAttribute("value"));
            //System.out.println("id = "+id);
            contactCache.add(new ContactData().withId(id).withFirstName(name).withLastName(lastName).
                    withAllPhones(allPhones).withAllEmails(allEmails).withAddress(address));
            //System.out.println("содержимое кеша: "+(contactCache));
        }
        return new Contacts(contactCache);
    }

    public void create(ContactData contacts) {
        initContactCreation();
        fillContactForm(contacts);
        submitContactCreation();
        contactCache = null;
        returnToHomePage();
    }

    public void modify(ContactData contact) {
       InitContactModificationById(contact.getId());
       fillContactForm(contact);
       submitContactModification();
       contactCache = null;
       returnToHomePage();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContacts();
        contactCache = null;
        returnToHomePage();
    }

    public ContactData infoFromEditForm(ContactData contact) {
        InitContactModificationById(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");

        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");
        returnToHomePage();

    return new ContactData().withId(contact.getId()).withFirstName(firstname).withLastName(lastname).
            withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work).withEmail(email).withEmail2(email2).withEmail3(email3).withAddress(address);
    }
}
