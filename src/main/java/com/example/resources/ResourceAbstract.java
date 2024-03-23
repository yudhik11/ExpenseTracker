package com.example.resources;

import com.example.controller.ExpenseTrackerService;
import com.example.controller.UserController;

public abstract class ResourceAbstract {
    protected UserController userController = new UserController();
    protected ExpenseTrackerService expenseTrackerService = new ExpenseTrackerService(userController);
}
