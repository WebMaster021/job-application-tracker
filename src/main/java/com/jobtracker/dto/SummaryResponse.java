package com.jobtracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SummaryResponse {
    private long applied;
    private long interview;
    private long offer;
    private long rejected;
    private long accepted;
    private long total;
}
