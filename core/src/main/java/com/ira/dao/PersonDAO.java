package com.ira.dao;

import com.ira.model.Person;
import com.ira.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class PersonDAO {
    private final SessionFactory sessionFactory;
    private Session currentSession;

    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void openAndBeginTransaction() {
        currentSession = sessionFactory.openSession();
        currentSession.beginTransaction();
    }

    public void commitAndCloseSession() {
        currentSession.getTransaction().commit();
        currentSession.close();
    }

    public Person get(int id) {
        return currentSession.get(Person.class, id);
    }

    public List<Person> getAll() {
        Query<Person> query = currentSession.createQuery("from Person", Person.class);
        return query.getResultList();
    }

    public void save(Person person) {
        currentSession.persist(person);
    }

    public void update(Person person) {
        currentSession.merge(person);
    }

    public void delete(Person person) {
        currentSession.remove(person);
    }

    public Role queryRole(String roleTitle) {
        Query<Role> query = currentSession.createQuery("from Role where role like lower(:role)", Role.class);
        query.setParameter("role", roleTitle);
        return query.getSingleResult();
    }
}