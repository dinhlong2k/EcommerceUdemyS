package com.ecommerceudemy.ecommerceudemy.DTO;

import com.ecommerceudemy.ecommerceudemy.model.Roles;
import java.util.HashSet;
import java.util.Set;

public class UserDTO {

    private Integer userId;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String confirmPassword;

    private String phoneNumber;

    private boolean enabled;

    private Set<Roles> listRole=new HashSet<>();

    public UserDTO() {
    }

    public UserDTO(String firstName, String lastName, String email, String password, String confirmPassword, String phoneNumber, boolean enabled, Set<Roles> listRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phoneNumber = phoneNumber;
        this.enabled = enabled;
        this.listRole = listRole;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Roles> getListRole() {
        return listRole;
    }

    public void setListRole(Set<Roles> listRole) {
        this.listRole = listRole;
    }
}
