package com.example.models.expense;

import com.example.models.split.Split;
import com.example.models.split.SplitType;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public abstract class Expense {
    protected SplitType splitType;
    protected List<Split> splits;
    protected String label;
    protected Integer paidByUserId;
    protected Double amount;
}
