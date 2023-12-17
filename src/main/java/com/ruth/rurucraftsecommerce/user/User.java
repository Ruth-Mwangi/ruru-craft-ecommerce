package com.ruth.rurucraftsecommerce.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ruth.rurucraftsecommerce.common.BaseEntity;
import com.ruth.rurucraftsecommerce.group.UserGroup;
import com.ruth.rurucraftsecommerce.permissions.UserPermission;
import jakarta.persistence.*;

import java.util.Set;
import java.util.prefs.BackingStoreException;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "users")
public class User extends BaseEntity {


    @Column(unique = true,name = "email",nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false,unique = true)
    private String phoneNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<UserGroup> userGroups;

    public User() {
    }

    public User(Integer id, String email, String firstName, String lastName, String phoneNumber) {
        this.setId(id);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
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

    public Set<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(Set<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", username='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
