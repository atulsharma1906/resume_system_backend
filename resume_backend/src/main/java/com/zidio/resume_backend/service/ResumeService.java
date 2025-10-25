package com.zidio.resume_backend.service;

import com.zidio.resume_backend.entity.Resume;
import com.zidio.resume_backend.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // This tells Lombok to generate constructor for final fields
public class ResumeService {

    private final ResumeRepository resumeRepository; // final field will be injected

    // Generate and save a resume
    public Resume generateAndSaveResume(Long userId, String contentJson) {
        Resume resume = new Resume();
        resume.setUserId(userId);
        resume.setContentJson(contentJson);
        resume.setVersion(1);  // initial version
        resume.setTitle("Resume for user " + userId);
        resume.setSummary("Auto-generated resume");
        resume.setSkills(List.of("Java", "Spring Boot")); // example
        return resumeRepository.save(resume);
    }

    // Get all resumes for a user
    public List<Resume> getHistory(Long userId) {
        return resumeRepository.findByUserId(userId);
    }

    // Get a resume by ID
    public Resume getById(Long id) {
        return resumeRepository.findById(id).orElse(null);
    }

    // Get all resumes
    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }
}
