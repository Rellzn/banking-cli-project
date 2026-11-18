package com.group14.model;

import com.group14.model.enums.Role;

public class Employee extends Person{
    public Employee (String firstName, String lastName, String userName, String passwordHash) {
        super(null, firstName, lastName, userName, passwordHash);
    }

    public Employee(Long employeeId, String firstName, String lastName,
                    String userName, String passwordHash ) {
        super(employeeId, firstName, lastName, userName, passwordHash);
    }

    public Long getEmployeeId() { return getId(); }

    @Override
    public Role getRole() { return Role.EMPLOYEE; }
}
