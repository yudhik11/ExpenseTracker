package com.example.resources;

import com.example.models.Group;
import com.example.models.User;
import com.example.models.expense.GroupExpense;
import com.example.models.expense.UsersExpense;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class UserResource extends ResourceAbstract {

    @PostMapping("addGroupExpense")
    public void addExpense(@RequestBody GroupExpense groupExpense) throws InstantiationException, IllegalAccessException {
        expenseTrackerService.addExpense(groupExpense);
    }

    @GetMapping("showBalanceForUser")
    public void showBalanceForUser(@RequestBody Integer id) {
        expenseTrackerService.showBalanceForUser(id);
    }

    @PostMapping("addUserExpense")
    public void addExpense(@RequestBody  UsersExpense usersExpense) {
        expenseTrackerService.addExpense(usersExpense);
    }

    @PostMapping("createUser")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        Boolean added = userController.createUser(user);
        if (added) {
            expenseTrackerService.getBalanceSheet().put(user, new HashMap<>());
            return ResponseEntity.ok().body(String.format("User %s added successfully", user.toString()));
        }
        else {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("User already added");
        }
    }

    @GetMapping("getUsers")
    public List<User> getUsers() {
        return userController.getUsers();
    }

    @GetMapping("getUser")
    public User getUser(@RequestBody Integer id) {
        return userController.getUser(id);
    }

    @PutMapping("deleteUser")
    public void deleteUser(@RequestBody Integer id) {
        userController.deleteUser(id);
    }

    @PostMapping("createGroup")
    public void addGroup(@RequestBody Map<String, Object> requestBody) {
        userController.createGroup((Integer) requestBody.get("groupId"),
                (List<Integer>) requestBody.get("userIds"));
    }

    @PostMapping("createAGroup")
    public void addAGroup(@RequestBody Integer groupId, @RequestBody List<Integer> userIds) {
        userController.createGroup(groupId, userIds);
    }

    @GetMapping("getGroups")
    public List<Group> getGroups() {
        return  userController.getGroups();
    }

    @GetMapping("getGroup")
    public Group getGroup(@RequestBody Integer id) {
        return userController.getGroup(id);
    }

    @PutMapping("deleteGroup")
    public void deleteGroup(@RequestBody Integer id) {
        userController.deleteGroup(id);
    }

    @PutMapping("addUserToGroup")
    public void addUserToGroup(@RequestBody Map<String, Object> requestBody) {
        userController.addUserToGroup((Integer) requestBody.get("userId"),
                (Integer) requestBody.get("groupId"));
    }

    @PutMapping("removeUserToGroup")
    public void removeUserToGroup(@RequestBody Map<String, Object> requestBody) {
        userController.removeUserToGroup((Integer) requestBody.get("userId"),
                (Integer) requestBody.get("groupId"));
    }
}
