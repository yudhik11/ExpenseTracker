package com.example.models.split;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

//@Data
@ToString
@Setter
@Getter
@NoArgsConstructor
//@RequiredArgsConstructor
//@JsonTypeInfo(use=JsonTypeInfo.Id.CUSTOM,property = "fieldType")
//@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
//@JsonSubTypes({
//        @JsonSubTypes.Type(EqualSplit.class),
//        @JsonSubTypes.Type(PercentSplit.class) }
//)
//@JsonDeserialize
public class Split {
    protected Integer userId;
    protected Double amount;
}
