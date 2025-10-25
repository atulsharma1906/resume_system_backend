package com.zidio.resume_backend.controller;


import com.zidio.resume_backend.dto.ResumeDTO;

import com.zidio.resume_backend.entity.Resume;
import com.zidio.resume_backend.service.ResumeService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {
	@Autowired
    private  ResumeService resumeService ;

    @PostMapping("/generate")
    public ResponseEntity<ResumeDTO> generateResume(@RequestParam Long userId,
                                                    @RequestBody String contentJson) {
        Resume resume = resumeService.generateAndSaveResume(userId, contentJson);
        return ResponseEntity.ok(mapToDTO(resume));
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<ResumeDTO>> getHistory(@PathVariable Long userId) {
        List<ResumeDTO> dtos = resumeService.getHistory(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResumeDTO> getResume(@PathVariable Long id) {
        Resume resume = resumeService.getById(id);
        if (resume != null) {
            return ResponseEntity.ok(mapToDTO(resume));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ResumeDTO>> getAllResumes() {
        List<ResumeDTO> dtos = resumeService.getAllResumes()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    private ResumeDTO mapToDTO(Resume resume) {
        ResumeDTO dto = new ResumeDTO();
        dto.setId(resume.getId());
        dto.setUserId(resume.getUserId());
        dto.setTitle(resume.getTitle());
        dto.setVersion(resume.getVersion());
        dto.setContentJson(resume.getContentJson());
        dto.setSummary(resume.getSummary());
        dto.setSkills(resume.getSkills());
        return dto;
    }
}