package com.ira.service;

import com.ira.dao.PersonDAO;
import com.ira.model.Person;
import com.ira.model.Role;
import org.hibernate.SessionFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PersonService {
    private final PersonDAO personDAO;

    public PersonService(SessionFactory sessionFactory) {
        personDAO = new PersonDAO(sessionFactory);
    }

    public Person getById(int id) {
        personDAO.openAndBeginTransaction();
        Person person = personDAO.get(id);
        personDAO.commitAndCloseSession();
        return person;
    }

    public void create(Person person) {
        personDAO.openAndBeginTransaction();
        personDAO.save(person);
        personDAO.commitAndCloseSession();
    }

    public void update(Person person) {
        personDAO.openAndBeginTransaction();
        personDAO.update(person);
        personDAO.commitAndCloseSession();
    }

    public void delete(Person person) {
        personDAO.openAndBeginTransaction();
        personDAO.delete(person);
        personDAO.commitAndCloseSession();
    }

    public void updateRole(Person person, String roleTitle) {
        personDAO.openAndBeginTransaction();
        Role role = personDAO.queryRole(roleTitle);
        person = personDAO.get(person.getId());
        person.getRoles().add(role);
        personDAO.commitAndCloseSession();
    }

    public List<Person> sortBy(Comparator<? super Person> comparator) {
        personDAO.openAndBeginTransaction();
        List<Person> sortedList = personDAO.getAll().stream()
                .sorted(comparator)
                .collect(Collectors.toList());
        personDAO.commitAndCloseSession();
        return sortedList;
    }
}