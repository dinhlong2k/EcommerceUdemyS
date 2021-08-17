package com.ecommerceudemy.ecommerceudemy.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(length = 45,nullable = false)
    private String firstName;

    @Column(length = 45,nullable = false)
    private String lastName;

    @Column(length = 128,nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    @Size(min = 5,max = 64)
    private String password;

    private boolean enabled;

    @Column(nullable = false)
    @Size(min = 10,max = 10)
    private String phoneNumber;

    @Column(length = 200)
    private String photos;

    @ManyToMany
    @JoinTable(name = "users_roles",
                                    joinColumns = @JoinColumn(name = "userid"),
                                                    inverseJoinColumns = @JoinColumn(name = "roleid"))
    private Set<Roles> roles=new HashSet<>();

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, boolean enabled, String phoneNumber, String photos, Set<Roles> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.phoneNumber = phoneNumber;
        this.photos = photos;
        this.roles = roles;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public void addRole(Roles newRole){
        this.roles.add(newRole);
    }

    @Override
    public String toString() {
        return  " " +roles ;
    }
}
