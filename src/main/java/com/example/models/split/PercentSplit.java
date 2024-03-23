package com.example.models.split;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PercentSplit extends Split {
    @NonNull
    Double percent;
}
