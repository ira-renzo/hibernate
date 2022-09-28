package com.ira.service;

import com.ira.dao.RoleDAO;
import com.ira.model.Role;
import org.hibernate.SessionFactory;

import java.util.List;

public class RoleService {
    private final RoleDAO roleDAO;

    public RoleService(SessionFactory sessionFactory) {
        roleDAO = new RoleDAO(sessionFactory);
    }

    public List<Role> getAll() {
        roleDAO.openAndBeginTransaction();
        List<Role> list = roleDAO.getAll();
        roleDAO.commitAndCloseSession();
        return list;
    }

    public void create(Role role) {
        roleDAO.openAndBeginTransaction();
        roleDAO.save(role);
        roleDAO.commitAndCloseSession();
    }

    public void delete(String roleTitle) {
        roleDAO.openAndBeginTransaction();
        roleDAO.delete(roleTitle);
        roleDAO.commitAndCloseSession();
    }
}