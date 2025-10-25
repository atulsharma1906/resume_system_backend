package com.zidio.resume_backend.util;

import com.zidio.resume_backend.dto.ResumeDTO;
import com.zidio.resume_backend.entity.Resume;

public class ResumeGenerator {

	 public static ResumeDTO generateResumeDTO(Resume resume) {
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
