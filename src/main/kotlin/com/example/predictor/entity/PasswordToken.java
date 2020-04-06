package com.example.predictor.entity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "token")
public class PasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @OneToOne(targetEntity = Users.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "users__id")
    private Users user;

    @Column(name = "expiration", nullable = false)
    private Date expiration;


    public PasswordToken() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(int hours) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, hours);
        this.expiration = now.getTime();
    }

    public boolean isExpired() {
        return new Date().after(this.expiration);
    }
}
