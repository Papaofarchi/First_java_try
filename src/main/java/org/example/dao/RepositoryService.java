package org.example.dao;

import org.example.entity.Person;
import org.example.entity.PhoneDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class RepositoryService {
    private static final SessionFactory sessionFactoryPerson;
    private static final SessionFactory sessionFactoryPhone;

    static {
        Configuration configurationPerson = new Configuration();
        configurationPerson.configure("hibernate.cfg.xml");
        configurationPerson.addAnnotatedClass(Person.class);
        sessionFactoryPerson = configurationPerson.buildSessionFactory();

        Configuration configurationPhone = new Configuration();
        configurationPhone.configure("hibernate.cfg.xml");
        configurationPhone.addAnnotatedClass(PhoneDetails.class);
        sessionFactoryPhone = configurationPhone.buildSessionFactory();
    }

    private static SessionFactory getSessionFactory(String type) {
        if (type == "person") {
            return sessionFactoryPerson;
        }
        if (type == "phone") {
            return sessionFactoryPhone;
        }

        return null;
    }

    private static Session getSession(String type) {
        if (type == "person") {
            return getSessionFactory("person").openSession();
        }
        if (type == "phone") {
            return getSessionFactory("phone").openSession();
        }

        return null;
    }

    public void savePersons(List<Person> persons) {
        Session session = getSession("person");
        Transaction transaction = session.beginTransaction();
        for (Person person : persons) {
            session.save(person);
        }
        transaction.commit();
        session.close();
    }

    public void updatePersons(List<Person> persons) {
        Session session = getSession("person");
        Transaction transaction = session.beginTransaction();
        for (Person person : persons) {
            session.update(person);
        }
        transaction.commit();
        session.close();
    }

    public void savePhoneNumbers(List<PhoneDetails> phoneNumbers) {
        Session session = getSession("phone");
        Transaction transaction = session.beginTransaction();
        for (PhoneDetails phoneNumber : phoneNumbers) {
            session.save(phoneNumber);
        }
        transaction.commit();
        session.close();
    }

    public void setPersonProperties() {
        Session sessionPerson = getSession("person");
        Session sessionPhone = getSession("phone");
        List<Person> updatePersons = new ArrayList<>();
        String sub;
        Person updatePerson;
        for (int i = 1; ; ) {
            updatePerson = sessionPerson.get(Person.class, i++);
            if (updatePerson != null) {
                sub = updatePerson.getPhone().substring(3, 6);
                switch (sub) {
                    case "905":
                        updatePerson.setPhoneDetails(sessionPhone.get(PhoneDetails.class, 1));
                        break;
                    case "926":
                        updatePerson.setPhoneDetails(sessionPhone.get(PhoneDetails.class, 2));
                        break;
                    case "950":
                        updatePerson.setPhoneDetails(sessionPhone.get(PhoneDetails.class, 3));
                        break;
                    case "910":
                        updatePerson.setPhoneDetails(sessionPhone.get(PhoneDetails.class, 4));
                        break;
                    default:
                        updatePerson.setPhoneDetails(sessionPhone.get(PhoneDetails.class, 5));
                        break;
                }
                updatePersons.add(updatePerson);
            } else break;
        }
        updatePersons(updatePersons);
    }
}

