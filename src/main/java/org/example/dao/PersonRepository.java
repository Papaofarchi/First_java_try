package org.example.dao;

import org.example.entity.ChatHistory;
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

public class PersonRepository {
    @Autowired
    private EmailService emailService;
    private static final SessionFactory sessionFactoryPerson;
    private static final SessionFactory sessionFactoryPhone;
    private static final SessionFactory sessionFactoryHistory;

    static {
        Configuration configurationPerson = new Configuration();
        configurationPerson.configure("hibernate.cfg.xml");
        configurationPerson.addAnnotatedClass(Person.class);
        sessionFactoryPerson = configurationPerson.buildSessionFactory();

        Configuration configurationPhone = new Configuration();
        configurationPhone.configure("hibernate.cfg.xml");
        configurationPhone.addAnnotatedClass(PhoneDetails.class);
        sessionFactoryPhone = configurationPhone.buildSessionFactory();
        Configuration configurationHistory = new Configuration();
        configurationPhone.configure("hibernate.cfg.xml");
        configurationPhone.addAnnotatedClass(ChatHistory.class);
        sessionFactoryHistory = configurationPhone.buildSessionFactory();
    }

    private static SessionFactory getSessionFactory(String type) {
        switch (type) {

            case "person":
                return sessionFactoryPerson;

            case "phone":
                return sessionFactoryPhone;

            case "history":
                return sessionFactoryHistory;
            default:
                return null;
        }

    }

    private static Session getSession(String type) {
        switch (type) {

            case "person":
                return getSessionFactory("person").openSession();

            case "phone":
                return getSessionFactory("phone").openSession();

            case "history":
                return getSessionFactory("history").openSession();
            default:
                return null;
        }

    }

    public void savePersons(List<Person> persons) {
        Session sessionPerson = getSession("person");
        Transaction transaction = sessionPerson.beginTransaction();
        for (Person person : persons) {
            sessionPerson.save(person);
        }
        transaction.commit();
        sessionPerson.close();
    }

    public void updatePersons(List<Person> persons) {
        Session sessionPerson = getSession("person");
        Transaction transaction = sessionPerson.beginTransaction();
        for (Person person : persons) {
            sessionPerson.update(person);
        }
        transaction.commit();
        sessionPerson.close();
    }

    public void savePhoneNumbers(List<PhoneDetails> phoneNumbers) {
        Session sessionPhone = getSession("phone");
        Transaction transaction = sessionPhone.beginTransaction();
        for (PhoneDetails phoneNumber : phoneNumbers) {
            sessionPhone.save(phoneNumber);
        }
        transaction.commit();
        sessionPhone.close();
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

    public void saveOrUpdateHistory(List<ChatHistory> history) {
        Session sessionHistory = getSession("history");
        Transaction transaction = sessionHistory.beginTransaction();
        for (ChatHistory oneMessageOfHistory : history) {
            sessionHistory.saveOrUpdate(oneMessageOfHistory);
        }
        transaction.commit();
        sessionHistory.close();
    }

    public void saveOrUpdateOneMessage(ChatHistory oneMessage) {
        Session sessionHistory = getSession("history");
        Transaction transaction = sessionHistory.beginTransaction();
        sessionHistory.saveOrUpdate(oneMessage);
        transaction.commit();
        sessionHistory.close();
    }


    public List<ChatHistory> getChatHistory() {
        Session sessionHistory = getSession("history");
        List<ChatHistory> chatHistory = new ArrayList<>();
        ChatHistory oneMessageOfHistory;
        for (int i = 1; ; ) {
            oneMessageOfHistory = sessionHistory.get(ChatHistory.class, i++);
            if (oneMessageOfHistory != null) {
                chatHistory.add(oneMessageOfHistory);
            } else break;
        }
        return chatHistory;
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




