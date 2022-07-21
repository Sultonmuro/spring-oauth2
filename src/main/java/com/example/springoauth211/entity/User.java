package com.example.springoauth211.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String given_name;
    private String picture; // img url
    private String type;    // user, admin
    private String email;   // @gmail
    private String login;   // login name
    private String bio;     // bio
    private String avatar_url; // profile picture
    private String url;     // user link
private String name;

}
