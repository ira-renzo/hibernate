package com.ira.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Person {
    @Id
    @GeneratedValue
    private int id;
    @Embedded
    private Name name;
    @Embedded
    private Address address;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    private Float gwa;
    @Temporal(TemporalType.DATE)
    private Date dateHired;
    private Boolean currentlyEmployed;
    @Embedded
    private ContactInfo contactInfo;
    @ManyToMany
    @JoinTable(name = "join_person_role")
    private Set<Role> roles = new LinkedHashSet<>();

    public int getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Float getGWA() {
        return gwa;
    }

    public void setGWA(Float gwa) {
        this.gwa = gwa;
    }

    public Date getDateHired() {
        return dateHired;
    }

    public void setDateHired(Date dateHired) {
        this.dateHired = dateHired;
    }

    public Boolean getCurrentlyEmployed() {
        return currentlyEmployed;
    }

    public void setCurrentlyEmployed(Boolean currentlyEmployed) {
        this.currentlyEmployed = currentlyEmployed;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}