package com.group14.model;
import java.util.Objects;

import com.group14.model.enums.Role;



public abstract class Person {
    private final Long id; //supplied by db, null when initialised
    private final String firstName;
    private final String lastName;
    private final String userName;
    private final String passwordHash;

    protected Person(String firstName, String lastName, String userName, String passwordHash) {
        this(null, firstName, lastName, userName, passwordHash);
    }

    protected Person(Long id, String firstName, String lastName,
                     String userName, String passwordHash) {
        this.id = id;
        this.firstName = firstName; //NOT NULL, throw custom exception probably
        this.lastName = lastName;
        this.userName = userName; //NOT NULL, db enforces uniqueness?
        this.passwordHash = passwordHash; //NOT NULL
    }

    public abstract Role getRole(); //for subclasses, employee or user

    protected Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getUserName() { return userName; }
    public String getPasswordHash() { return passwordHash; }

    public String getFullName() {
        return lastName == null || lastName.isBlank() ? firstName : firstName + " " + lastName;
    }

    //Utility functions

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person other = (Person) o;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() { return Objects.hash(getClass(), id); }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{id=" + id + ", userName='" + userName
                + "', name='" + getFullName() + "'}";
    }

}
