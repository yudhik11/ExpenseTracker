package com.example.models.expense;

import com.example.models.split.Split;
import com.example.models.split.SplitType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class UsersExpense extends Expense {
    private List<Integer> userIds;

    public UsersExpense(SplitType splitType, List<Split> splits, String label, Integer paidByUserId, Double amount, List<Integer> userIds) {
        super(splitType, splits, label, paidByUserId, amount);
        this.userIds = userIds;
    }
}
