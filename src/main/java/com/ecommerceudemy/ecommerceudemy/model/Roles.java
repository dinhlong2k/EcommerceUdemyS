package com.ecommerceudemy.ecommerceudemy.model;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;

    @Column(length = 40,nullable = false,unique = true)
    private String nameRole;

    public Roles() {
    }

    public Roles(int roleId) {
        this.roleId = roleId;
    }

    public Roles(String nameRole, String description) {
        this.nameRole = nameRole;
        this.description = description;
    }

    @Column(length = 200,nullable = false)
    private String description;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.nameRole;
    }
}
