package com.jobtracker.dto;

import com.jobtracker.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationRequest {
    private String companyName;
    private String position;
    private Status status;
    private LocalDate appliedDate;
    private String notes;
    private LocalDate interviewDate;
    private BigDecimal salaryOffered;
}
