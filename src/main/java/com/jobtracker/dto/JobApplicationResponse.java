package com.jobtracker.dto;

import com.jobtracker.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class JobApplicationResponse {
    private UUID id;
    private String companyName;
    private String position;
    private Status status;
    private LocalDate appliedDate;
    private String notes;
    private LocalDate interviewDate;
    private BigDecimal salaryOffered;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
