package com.example.controller;

import com.example.models.User;
import com.example.models.expense.Expense;
import com.example.models.expense.GroupExpense;
import com.example.models.expense.UsersExpense;
import com.example.models.split.PercentSplit;
import com.example.models.split.Split;
import com.example.models.split.SplitType;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Setter
public class ExpenseTrackerService {
    private List<Expense> expenses = Lists.newArrayList();
    private Map<User, Map<User, Double>> balanceSheet = new HashMap<>();
    private UserController userController;
    public ExpenseTrackerService(UserController userController) {
        this.userController = userController;
    }

    public void addExpense(Expense expense) {
        log.info(String.format("Submitted request to add expense : %s", expense.toString()));
        System.out.println(expense.toString());
        List<Integer> userIdList;
        if (expense instanceof GroupExpense) {
            Set<User> userList = this.userController.groups.get(((GroupExpense) expense).getGroupId())
                    .getUserList();
            userIdList = userList.stream()
                    .map(u -> u.getUserId())
                    .collect(Collectors.toList());
        }
        else {
            userIdList = ((UsersExpense) expense).getUserIds();
            userIdList = userIdList.stream()
                    .filter(u -> userController.userMap.containsKey(u.getClass()))
                    .collect(Collectors.toList());
        }
        resolveExpense((Expense) expense, userIdList);
    }

    private void resolveExpense(Expense expense, List<Integer> userIdList) {
        SplitType splitType = expense.getSplitType();
        int n_users = expense.getSplits().size();
        boolean validate = true;
        switch (splitType) {
            case EQUAL: {
                Double splitPerPerson = expense.getAmount() / Double.valueOf(n_users);
                for (Split split : expense.getSplits()) {
                    if (Objects.nonNull(split.getAmount())) {
                        validate &= Math.abs(splitPerPerson - split.getAmount()) < 1e-1;
                    } else {
                        split.setAmount(splitPerPerson);
                    }
                }
                if (!validate) {
                    log.error(String.format("Per user share = (%f / %f) ~= %f is different that provided amount", expense.getAmount(), expense.getSplits().size(), splitPerPerson));
                    return;
                }
                break;
            }
            case EXACT: {
                Double totalPaid = 0.0;
                for (Split split : expense.getSplits()) {
                    if (Objects.isNull(split.getAmount())) {
                        log.error(String.format("Amount not specified in case of exact split"));
                        return;
                    }
                    totalPaid += split.getAmount();
                }
                validate &= Math.abs(totalPaid - expense.getAmount()) < 1e-2;
                if (!validate) {
                    log.error(String.format("Sum of total share %f is different that provided amount %f", totalPaid, expense.getAmount()));
                    return;
                }
                break;
            }
            case PERCENT: {
                Double totalPercent = 0.0;
                for (Split split : expense.getSplits()) {
                    if (!(split instanceof PercentSplit)) {
                        log.error(String.format("Percent not provided amount for splitType percent"));
                        return;
                    }
                    double percentShare = ((PercentSplit) split).getPercent();
                    totalPercent += percentShare;
                    Double personShare = expense.getAmount() * percentShare / 100.0;
                    if (Objects.nonNull(split.getAmount())) {
                        validate &= Math.abs(personShare - split.getAmount()) < 1e-2;
                    }
                    split.setAmount(personShare);
                }
                validate &= Math.abs(totalPercent - 100.0) < 1e-2;
                if (!validate) {
                    log.error(String.format("Sum of total percent %f do not add up to 100", totalPercent));
                    return;
                }
                break;
            }
        }
        expenses.add(expense);
        processExpense(expense);
    }

    private void processExpense(Expense expense) {
        User paidBy = userController.getUser(expense.getPaidByUserId());
        for (Split split : expense.getSplits()) {
            User paidTo = userController.getUser(split.getUserId());
            Map<User, Double> balances = balanceSheet.get(paidBy);
            if (!balances.containsKey(paidTo)) {
                balances.put(paidTo, 0.0);
            }
            balances.put(paidTo, balances.get(paidTo)+split.getAmount());
            balances = balanceSheet.get(paidTo);

            if (!balances.containsKey(paidBy)) {
                balances.put(paidBy, 0.0);
            }
            balances.put(paidBy, balances.get(paidBy) - split.getAmount());
        }
    }

    public void showBalanceForUser(Integer userId) {
        User thisUser = userController.userMap.get(userId);
        if (thisUser == null){
            System.out.println("User doesn't exist");
            return;
        }
        for (Entry<User, Double> userBalance : balanceSheet.get(thisUser).entrySet()) {
            //We don't want to print balance to self, which will be Zero ideally
            if (userBalance.getKey()!=thisUser) {
                printBalances(userId, userBalance);
            }
        }
    }

    private void printBalances(Integer userId, Entry<User, Double> userBalance) {
        if (userBalance.getValue()!= 0) {
            if (userBalance.getValue()<0) {
                System.out.println(userController.userMap.get(userId).getName()+" owes "+Math.abs(userBalance.getValue())+" to "+userBalance.getKey().getName());
                return;
            }
            System.out.println(userBalance.getKey().getName()+" owes "+Math.abs(userBalance.getValue())+" to "+ userController.userMap.get(userId).getName());
            return;
        }
        System.out.println("No balances for "+userId);
    }

    public void showAllBalances () {
    }
}
