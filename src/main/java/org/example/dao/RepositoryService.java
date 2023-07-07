package org.example.dao;

import org.example.entity.Person;
import org.example.entity.PhoneDetails;
import org.example.service.EmailService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class RepositoryService {
    @Autowired
    private EmailService emailService;
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

    public List<Person> getPersons() {
        Session sessionPerson = getSession("person");
        List<Person> getPersons = new ArrayList<>();
        Person getPerson;
        for (int i = 1; ; ) {
            getPerson = sessionPerson.get(Person.class, i++);
            if (getPerson != null) {
                getPersons.add(getPerson);
            } else break;
        }
        return getPersons;
    }

    public List<PhoneDetails> getPhoneDetails() {
        Session sessionPhone = getSession("phone");
        List<PhoneDetails> getPhones = new ArrayList<>();
        PhoneDetails getPhone;
        for (int i = 1; ; ) {
            getPhone = sessionPhone.get(PhoneDetails.class, i++);
            if (getPhone != null) {
                getPhones.add(getPhone);
            } else break;
        }
        return getPhones;
    }

    public void setPersonProperties() {
        List<Person> updatedPersons = getPersons();
        List<PhoneDetails> phoneDetails = getPhoneDetails();
        String sub;
        for (Person updatedPerson : updatedPersons) {
            sub = updatedPerson.getPhone().substring(3, 6);
            switch (sub) {
                case "905":
                    updatedPerson.setPhoneDetails(phoneDetails.get(0));
                    updatedPerson.setEmail(emailService.generateEmail(updatedPerson));
                    break;
                case "926":
                    updatedPerson.setPhoneDetails(phoneDetails.get(1));
                    updatedPerson.setEmail(emailService.generateEmail(updatedPerson));
                    break;
                case "950":
                    updatedPerson.setPhoneDetails(phoneDetails.get(2));
                    updatedPerson.setEmail(emailService.generateEmail(updatedPerson));
                    break;
                case "910":
                    updatedPerson.setPhoneDetails(phoneDetails.get(3));
                    updatedPerson.setEmail(emailService.generateEmail(updatedPerson));
                    break;
                default:
                    updatedPerson.setPhoneDetails(phoneDetails.get(4));
                    updatedPerson.setEmail("none");
                    break;
            }
        }
        updatePersons(updatedPersons);
    }
}




