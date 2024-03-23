package com.example.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private String name;
    private Integer userId;

    public User() {
    }

    public User(String name, int id) {
        this.name = name;
        this.userId = id;
    }
}
