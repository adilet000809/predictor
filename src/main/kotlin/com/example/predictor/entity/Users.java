package com.example.predictor.entity;

import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class Users extends BaseEntity {

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "score", nullable = false, columnDefinition = "int default 100")
    private int score;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private Set<Roles> roles;

    public Users(){

    }

    public Users(String email, String password, String fullName, Set<Roles> roles) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.roles = roles;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
