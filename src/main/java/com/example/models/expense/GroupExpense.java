package com.example.models.expense;

import com.example.models.split.Split;
import com.example.models.split.SplitType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class GroupExpense extends Expense {
    private Integer groupId;

    public GroupExpense(SplitType splitType, List<Split> splits, String label, Integer paidByUserId, Double amount, Integer groupId) {
        super(splitType, splits, label, paidByUserId, amount);
        this.groupId = groupId;
    }
}
