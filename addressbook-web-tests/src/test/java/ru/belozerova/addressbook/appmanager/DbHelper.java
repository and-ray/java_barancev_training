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

    public Groups groups() {
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

}
