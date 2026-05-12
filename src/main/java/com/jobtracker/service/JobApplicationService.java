package com.jobtracker.service;

import com.jobtracker.dto.JobApplicationRequest;
import com.jobtracker.dto.JobApplicationResponse;
import com.jobtracker.dto.SummaryResponse;
import com.jobtracker.entity.JobApplication;
import com.jobtracker.entity.User;
import com.jobtracker.entity.enums.Status;
import com.jobtracker.repository.JobApplicationRepository;
import com.jobtracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobApplicationService {
    private final JobApplicationRepository jobApplicationRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private JobApplicationResponse toResponse(JobApplication application) {
        JobApplicationResponse response = new JobApplicationResponse();
        response.setId(application.getId());
        response.setCompanyName(application.getCompanyName());
        response.setPosition(application.getPosition());
        response.setStatus(application.getStatus());
        response.setAppliedDate(application.getAppliedDate());
        response.setNotes(application.getNotes());
        response.setInterviewDate(application.getInterviewDate());
        response.setSalaryOffered(application.getSalaryOffered());
        response.setCreatedAt(application.getCreatedAt());
        response.setUpdatedAt(application.getUpdatedAt());
        return response;
    }

    public JobApplicationResponse createApplication(JobApplicationRequest request) {
        User user = getCurrentUser();
        JobApplication application = JobApplication.builder()
                .userId(user.getId())
                .companyName(request.getCompanyName())
                .position(request.getPosition())
                .status(request.getStatus() != null ? request.getStatus() : Status.APPLIED)
                .appliedDate(request.getAppliedDate())
                .notes(request.getNotes())
                .interviewDate(request.getInterviewDate())
                .salaryOffered(request.getSalaryOffered())
                .build();

        return toResponse(jobApplicationRepository.save(application));
    }

    public List<JobApplicationResponse> getAllApplications() {
        User user = getCurrentUser();
        List<JobApplication> applications = jobApplicationRepository.findByUserId(user.getId());
        return applications.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public JobApplicationResponse getApplicationById(UUID id) {
        User user = getCurrentUser();
        JobApplication application = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        if (!application.getUserId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        return toResponse(application);
    }

    public JobApplicationResponse updateApplication(UUID id, JobApplicationRequest request) {
        User user = getCurrentUser();
        JobApplication application = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        if (!application.getUserId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        application.setCompanyName(request.getCompanyName());
        application.setPosition((request.getPosition()));
        application.setStatus(request.getStatus());
        application.setAppliedDate(request.getAppliedDate());
        application.setNotes(request.getNotes());
        application.setInterviewDate(request.getInterviewDate());
        application.setSalaryOffered(request.getSalaryOffered());

        return toResponse(jobApplicationRepository.save(application));
    }

    public void deleteApplication(UUID id) {
        User user = getCurrentUser();
        JobApplication application = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        if (!application.getUserId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        jobApplicationRepository.deleteById(id);
    }

    public List<JobApplicationResponse> filterByStatus(Status status) {
        User user = getCurrentUser();
        List<JobApplication> application =
                jobApplicationRepository.findByUserIdAndStatus(user.getId(), status);
        return application.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public SummaryResponse getSummary() {
        User user = getCurrentUser();
        List<JobApplication> application = jobApplicationRepository.findByUserId(user.getId());

        long applied = application.stream()
                .filter(app -> app.getStatus() == Status.APPLIED)
                .count();
        long accepted = application.stream()
                .filter(app -> app.getStatus() == Status.ACCEPTED)
                .count();
        long offered = application.stream()
                .filter(app -> app.getStatus() == Status.OFFER)
                .count();
        long interviewed = application.stream()
                .filter(app -> app.getStatus() == Status.INTERVIEW)
                .count();
        long rejected = application.stream()
                .filter(app -> app.getStatus() == Status.REJECTED)
                .count();

        SummaryResponse response = new SummaryResponse();
        response.setApplied(applied);
        response.setAccepted(accepted);
        response.setOffer(offered);
        response.setInterview(interviewed);
        response.setRejected(rejected);
        response.setTotal(applied + accepted + offered + rejected + interviewed);

        return response;
    }
}
