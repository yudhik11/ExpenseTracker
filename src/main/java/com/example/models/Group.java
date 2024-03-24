package com.example.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
public class Group {
    private Set<User> userList;

    @NonNull
    private Integer groupId;

    public Group(int groupId) {
        this.groupId = groupId;
        this.userList = new HashSet<>();
    }

    public Group(int groupId, Set<User> users) {
        this.groupId = groupId;
        this.userList = users;
    }

    public void addUser(User user) {
        this.userList.add(user);
    }

    public void removeUser(User user) {
        this.userList.remove(user);
    }
}
