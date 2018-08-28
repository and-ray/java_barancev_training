package ru.belozerova.addressbook.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.belozerova.addressbook.model.ContactData;
import ru.belozerova.addressbook.model.Contacts;
import ru.belozerova.addressbook.model.GroupData;
import ru.belozerova.addressbook.model.Groups;

import java.util.List;

public class DbHelper {

    private final SessionFactory sessionFactory;

    public DbHelper() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    }

    public Groups groups() { //сколько групп в бд
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        List<GroupData> result = session.createQuery( "from GroupData", GroupData.class ).getResultList();
        session.getTransaction().commit();
        session.close();
        return new Groups(result);
    }

    public Contacts contacts() {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        List<ContactData> result = session.createQuery( "from ContactData where deprecated = '0000-00-00'", ContactData.class ).getResultList();
        session.getTransaction().commit();
        session.close();
       // System.out.println("result: "+ result);
        return new Contacts(result);

    }

    public Contacts contacts(int id) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        List<ContactData> result = session.createQuery( "from ContactData where id = '"+id+"'", ContactData.class ).getResultList();
        session.getTransaction().commit();
        session.close();
        //System.out.println("result: "+ result);
        return new Contacts(result);

    }

    public int getContactLastId(ContactData contact){
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        List<ContactData> result = session.createQuery( "from ContactData where firstname = '"+contact.getFirstName() +
                "' and lastname = '" + contact.getLastName() + "' and address = '" + contact.getAddress() +
                "' and email = '" + contact.getEmail() + "'", ContactData.class ).getResultList();
        session.getTransaction().commit();
        session.close();
        int maxId = result.get(0).getId();
        for (int i = 0; i < result.size(); i++){
            if (result.get(i).getId() > maxId){
                maxId = result.get(i).getId();
            }
        }
        //System.out.println("maxId: "+ maxId);
        return maxId;
    }
}
