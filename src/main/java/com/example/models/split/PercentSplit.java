package com.example.models.split;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class PercentSplit extends Split {
    public static String _type = "PercentSplit";
    @JsonProperty("percent")
    Double percent;
}
