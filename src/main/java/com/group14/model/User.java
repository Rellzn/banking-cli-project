package com.group14.model;

import com.group14.model.enums.Role;

public class User extends Person{
    public User (String firstName, String lastName, String userName, String passwordHash) {
        super(null, firstName, lastName, userName, passwordHash);
    }

    public User(Long userId, String firstName, String lastName,
                String userName, String passwordHash ) {
        super(userId, firstName, lastName, userName, passwordHash);
    }

    public Long getUserId() { return getId(); }

    @Override
    public Role getRole() { return Role.USER; }
}
