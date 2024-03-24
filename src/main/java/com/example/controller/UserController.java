package com.example.controller;

import com.example.models.Group;
import com.example.models.User;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class UserController {
    protected Map<Integer, User> userMap;
    protected Map<Integer, Group> groups;

    public UserController() {
        userMap = new HashMap<>();
        groups = new HashMap<>();

    }

    public User createUser(User user) {
        log.info(String.format("Submitted request to add user with id: %d", user.getUserId()));
        if (userMap.containsKey(user.getUserId())) {
            log.error(String.format("User with id: %d already exists: '%s'", user.getUserId(), user.toString()));
            throw new RuntimeException(String.format("User already added: %s", user.toString()));
        } else {
            userMap.put(user.getUserId(), user);
            log.info(String.format("User with id: %d successfully added: '%s'", user.getUserId(), user.toString()));
            return user;
        }
    }

    public Group createGroup(int groupId, List<Integer> userIds) {
        log.info(String.format("Submitted request to add user with id: %d", groupId));
        if (groups.containsKey(groupId)) {
            log.error(String.format("Group with id: %d already exists: '%s'", groupId, groups.get(groupId).toString()));
            throw new RuntimeException(String.format("Group already added: '%s'", groups.get(groupId).toString()));
        } else {
            List<User> users = Lists.newArrayList();
            for (Integer id : userIds) {
                if (userMap.containsKey(id)) {
                    users.add(userMap.get(id));
                }
            }
            Group group = new Group(groupId, Sets.newHashSet(users));
            groups.put(groupId, group);
            log.info(String.format("Group with id: %d successfully added: '%s'", group.getGroupId(), group.toString()));
            return group;
        }
    }

    public void addUserToGroup(int userId, int groupId) {
        if (!userMap.containsKey(userId) || !groups.containsKey(groupId)) {
            log.error(String.format("User id: %d or GroupId: %d does not exists", userId, groupId));
            return;
        }
        User user = userMap.get(userId);
        Group group = groups.get(groupId);
        group.addUser(user);
        System.out.println(String.format("User with id: '%s' successfully added to group with id: '%s'", userId, groupId));
    }

    public void removeUserToGroup(int userId, int groupId) {
        if (!userMap.containsKey(userId) || !groups.containsKey(groupId)) {
            log.error(String.format("User id: %d or GroupId: %d does not exists", userId, groupId));
            return;
        }
        User user = userMap.get(userId);
        Group group = groups.get(groupId);
        group.removeUser(user);
        System.out.println(String.format("User with id: '%s' successfully removed from group with id: '%s'", userId, groupId));
    }

    public void recordExpense() {

    }

    public void deleteUser(int id) {
        if (userMap.containsKey(id))
            userMap.remove(id);
    }

    public void deleteGroup(int id) {
        if (groups.containsKey(id))
            groups.remove(id);
    }

    public User getUser(int id) {
        return userMap.values().stream().filter(u -> u.getUserId() == id).findFirst().orElse(null);
    }

    public Group getGroup(int id) {
        return groups.values().stream().filter(u -> u.getGroupId() == id).findFirst().orElse(null);
    }

    public List<User> getUsers() {
        return userMap.values().stream().collect(Collectors.toList());
    }

    public List<Group> getGroups() {
        return groups.values().stream().collect(Collectors.toList());
    }

}
