package com.ira;

import com.ira.exceptions.ActionCancelledException;
import com.ira.exceptions.UserExitException;
import com.ira.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;

public class App {
    private final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public App() {
//        initDatabase();
        Menu menu = new Menu(sessionFactory);
        while (true) {
            try {
                menu.trigger();
            } catch (ActionCancelledException exception) {
                System.out.println("\nReturning To Menu\n");
            } catch (UserExitException exception) {
                System.out.println("Bye");
                return;
            }
        }
    }

    private void initDatabase() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String[] roles = {"admin", "dev", "qa", "ba"};
        Arrays.stream(roles).forEach(role -> session.persist(new Role(role)));

        session.getTransaction().commit();
        session.close();
    }

    public static void main(String[] args) {
        new App();
    }
}