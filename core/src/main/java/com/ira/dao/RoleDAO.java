package com.ira.dao;

import com.ira.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class RoleDAO {
    private final SessionFactory sessionFactory;
    private Session currentSession;

    public RoleDAO(SessionFactory sessionFactory) {
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

    public Role get(int id) {
        return currentSession.get(Role.class, id);
    }

    public List<Role> getAll() {
        Query<Role> query = currentSession.createQuery("from Role", Role.class);
        return query.getResultList();
    }

    public void save(Role role) {
        currentSession.persist(role);
    }

    public void update(Role role) {
        currentSession.merge(role);
    }

    public void delete(String roleTitle) {
        Query<Role> query = currentSession.createQuery("from Role where role like lower(:role)", Role.class);
        query.setParameter("role", roleTitle);
        currentSession.remove(query.getSingleResult());
    }
}