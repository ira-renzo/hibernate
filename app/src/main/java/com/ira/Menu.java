package com.ira;

import com.ira.exceptions.ActionCancelledException;
import com.ira.exceptions.UserExitException;
import com.ira.model.*;
import com.ira.service.PersonService;
import com.ira.service.RoleService;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import org.hibernate.SessionFactory;

import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("SameParameterValue")
public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final PersonService personService;
    private final RoleService roleService;

    public Menu(SessionFactory sessionFactory) {
        personService = new PersonService(sessionFactory);
        roleService = new RoleService(sessionFactory);
    }

    public void trigger() throws ActionCancelledException, UserExitException {
        System.out.println("CREATE PERSON");
        System.out.println("DELETE PERSON");
        System.out.println("UPDATE PERSON");
        System.out.println("LIST PERSON");
        System.out.println("ADD PERSON ROLE");
//        System.out.println("DELETE PERSON ROLE\n");
//        System.out.println("ADD CONTACT");
        System.out.println("UPDATE CONTACT");
//        System.out.println("DELETE CONTACT\n");
        System.out.println("ADD ROLE");
//        System.out.println("UPDATE ROLE");
        System.out.println("DELETE ROLE");
        System.out.println("LIST ROLE\n");
        System.out.println("EXIT");
        System.out.println();
        String menuOption = askString("Enter Choice: ").toUpperCase();
        printDivider();

        switch (menuOption) {
            case "CREATE PERSON":
                createPerson();
                break;
            case "DELETE PERSON":
                deletePerson();
                break;
            case "UPDATE PERSON":
                updateInfo();
                break;
            case "LIST PERSON":
                listPerson();
                break;
            case "ADD PERSON ROLE":
                addPersonRole();
                break;
//            case "DELETE PERSON ROLE":
//                break;
//            case "ADD CONTACT":
//                break;
            case "UPDATE CONTACT":
                updateContactInfo();
                break;
//            case "DELETE CONTACT":
//                break;
            case "ADD ROLE":
                addRole();
                break;
//            case "UPDATE ROLE":
//                break;
            case "DELETE ROLE":
                deleteRole();
                break;
            case "LIST ROLE":
                listRole();
                break;
            case "EXIT":
                throw new UserExitException();
            default:
                System.out.println("Wrong Menu Option");
        }

        printDivider();
    }

    private void createPerson() throws ActionCancelledException {
        Person person = new Person();
        updateName(person);
        personService.create(person);
    }

    private void deletePerson() throws ActionCancelledException {
        Person person = askFromId();
        personService.delete(person);
    }

    private void updateInfo() throws ActionCancelledException {
        Person person = askFromId();

        System.out.println("NAME");
        System.out.println("ADDRESS");
        System.out.println("BIRTHDAY");
        System.out.println("GWA");
        System.out.println("DATE HIRED");
        System.out.println("CURRENTLY EMPLOYED\n");
        System.out.println("EXIT");
        System.out.println();

        switch (askString("Enter Choice: ").toUpperCase()) {
            case "NAME":
                updateName(person);
                break;
            case "ADDRESS":
                updateAddress(person);
                break;
            case "BIRTHDAY":
                updateBirthday(person);
                break;
            case "GWA":
                updateGWA(person);
                break;
            case "DATE HIRED":
                updateDateHired(person);
                break;
            case "CURRENTLY EMPLOYED":
                updateCurrentlyEmployed(person);
                break;
            case "EXIT":
                break;
            default:
                System.out.println("Invalid Person Property");
        }

        personService.update(person);
    }

    private void updateName(Person person) throws ActionCancelledException {
        Name name = new Name();
        name.setLastName(askString("Enter Last Name: "));
        name.setFirstName(askString("Enter First Name: "));
        name.setMiddleName(askString("Enter Middle Name: "));
        name.setSuffix(askString("Enter Suffix Name: "));
        name.setTitle(askString("Enter Title: "));
        person.setName(name);
    }

    private void updateAddress(Person person) throws ActionCancelledException {
        Address address = new Address();
        address.setStreetNo(askString("Enter Street Number: "));
        address.setBarangay(askString("Enter Barangay: "));
        address.setCity(askString("Enter City: "));
        address.setZipCode(askString("Enter Zip Code: "));
        person.setAddress(address);
    }

    private void updateBirthday(Person person) throws ActionCancelledException {
        Calendar.Builder calendar = new Calendar.Builder();
        int year = askPositiveInt("Enter Year Number: ");
        int month = askPositiveInt("Enter Month Number: ");
        int day = askPositiveInt("Enter Day of Month Number: ");
        calendar.setDate(year, month, day);
        person.setBirthday(calendar.build().getTime());
    }

    private void updateGWA(Person person) throws ActionCancelledException {
        person.setGWA(askFloat("Enter GWA: "));
    }

    private void updateDateHired(Person person) throws ActionCancelledException {
        Calendar.Builder calendar = new Calendar.Builder();
        int year = askPositiveInt("Enter Year Number: ");
        int month = askPositiveInt("Enter Month Number: ");
        int day = askPositiveInt("Enter Day of Month Number: ");
        calendar.setDate(year, month, day);
        person.setDateHired(calendar.build().getTime());
    }

    private void updateCurrentlyEmployed(Person person) throws ActionCancelledException {
        person.setCurrentlyEmployed(askBoolean("Is Person Currently Employed (Boolean): "));
    }

    private void updateContactInfo() throws ActionCancelledException {
        Person person = askFromId();
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setLandline(askString("Enter Landline: "));
        contactInfo.setMobileNumber(askString("Enter Mobile Number: "));
        contactInfo.setEmail(askString("Enter Mobile Email: "));
        person.setContactInfo(contactInfo);
    }

    private void addPersonRole() throws ActionCancelledException {
        try {
            personService.updateRole(askFromId(), askString("Role Keyword: "));
        } catch (NoResultException exception) {
            System.out.println("Role Title Does Not Exist");
        }
    }

    private void listPerson() {
        List<Person> sortedList = personService.sortBy(Comparator.comparing(Person::getGWA).reversed());
        System.out.println("\nSorted By GWA\n");
        sortedList.forEach(person -> System.out.printf("%s, %s\n", person.getName().getFirstName(), person.getGWA()));

        sortedList = personService.sortBy(Comparator.comparing(Person::getDateHired).reversed());
        System.out.println("\nSorted By Date Hired\n");
        sortedList.forEach(person -> System.out.printf("%s, %s\n", person.getName().getFirstName(), person.getDateHired()));

        sortedList = personService.sortBy(Comparator.comparing((Person person) ->
                person.getName().getLastName()).reversed());
        System.out.println("\nSorted By Last Name\n");
        sortedList.forEach(person -> {
            System.out.printf("%s, %s\n", person.getName().getFirstName(), person.getName().getLastName());
        });
    }

    private void addRole() throws ActionCancelledException {
        try {
            roleService.create(new Role(askString("Enter New Role Keyword: ")));
        } catch (PersistenceException exception) {
            System.out.println("\nRole Already Exists");
        }
    }

    private void deleteRole() throws ActionCancelledException {
        try {
            roleService.delete(askString("Enter Role Keyword to Delete: "));
        } catch (NoResultException exception) {
            System.out.println("\nInvalid Keyword");
        }
    }

    private void listRole() {
        roleService.getAll().forEach(role -> System.out.println(role.getRole()));
    }

    private Person askFromId() throws ActionCancelledException {
        while (true) {
            Person person = personService.getById(askInt("Enter Person ID: "));
            if (person == null) {
                System.out.println("\nInvalid ID\n");
            } else {
                return person;
            }
        }
    }

    private int askPositiveInt(String prompt) throws ActionCancelledException {
        while (true) {
            int intInput = askInt(prompt);
            if (intInput <= 0) System.out.println("\nOnly Positive Values Are Allowed\n");
            else return intInput;
        }
    }

    private int askInt(String prompt) throws ActionCancelledException {
        while (true) {
            String stringInput = askString(prompt);
            try {
                return Integer.parseInt(stringInput);
            } catch (NumberFormatException exception) {
                System.out.println("\nWrong Type\n");
            }
        }
    }

    private boolean askBoolean(String prompt) throws ActionCancelledException {
        while (true) {
            String stringInput = askString(prompt);
            try {
                return Boolean.parseBoolean(stringInput);
            } catch (NumberFormatException exception) {
                System.out.println("\nWrong Type\n");
            }
        }
    }

    private float askFloat(String prompt) throws ActionCancelledException {
        while (true) {
            String stringInput = askString(prompt);
            try {
                return Float.parseFloat(stringInput);
            } catch (NumberFormatException exception) {
                System.out.println("\nWrong Type\n");
            }
        }
    }

    private String askString(String prompt) throws ActionCancelledException {
        System.out.print(prompt);
        String inputString = scanner.nextLine().trim();
        if (inputString.equalsIgnoreCase("MENU")) throw new ActionCancelledException();
        else return inputString;
    }

    private void printDivider() {
        System.out.println("\n========================================\n");
    }
}