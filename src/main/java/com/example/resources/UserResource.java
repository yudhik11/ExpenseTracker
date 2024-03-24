package com.example.resources;

import com.example.models.Group;
import com.example.models.User;
import com.example.models.expense.Expense;
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
    public ResponseEntity<String> addExpense(@RequestBody GroupExpense groupExpense) throws InstantiationException, IllegalAccessException {
        try {
            Expense expense = expenseTrackerService.addExpense(groupExpense);
            return ResponseEntity.ok()
                    .body(String.format(String.format("Group expense successfully added: %s", expense.toString())));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(String.format("Error processing expense: %s", groupExpense.toString()));
        }
    }

    @GetMapping("showBalanceForUser")
    public void showBalanceForUser(@RequestBody Integer id) {
        expenseTrackerService.showBalanceForUser(id);
    }

    @GetMapping("showAllBalances")
    public void showAllBalances() {
        expenseTrackerService.showAllBalances();
    }


    @PostMapping("addUserExpense")
    public ResponseEntity<String> addExpense(@RequestBody UsersExpense usersExpense) {
        try {
            Expense expense = expenseTrackerService.addExpense(usersExpense);
            return ResponseEntity.ok()
                    .body(String.format(String.format("Users expense successfully added: %s", expense.toString())));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(String.format("Error processing expense: %s", usersExpense.toString()));
        }
    }

    @PostMapping("createUser")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            User tmp = userController.createUser(user);
            expenseTrackerService.getBalanceSheet().put(user, new HashMap<>());
            return ResponseEntity.ok().body(String.format("User %s added successfully", user.toString()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(String.format("User already added: %s", user.toString()));
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
    public ResponseEntity<String> addGroup(@RequestBody Map<String, Object> requestBody) {
        try {
            Group group = userController.createGroup((Integer) requestBody.get("groupId"),
                    (List<Integer>) requestBody.get("userIds"));
            return ResponseEntity.ok().body(String.format("Group %s added successfully", group.toString()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(String.format("Group already added: %s", userController.getGroup(Integer.valueOf((String) requestBody.get("groupId")))));
        }
    }

    @GetMapping("getGroups")
    public List<Group> getGroups() {
        return userController.getGroups();
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
