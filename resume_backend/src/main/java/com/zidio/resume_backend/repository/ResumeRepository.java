package com.zidio.resume_backend.repository;


import com.zidio.resume_backend.entity.Resume;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findByUserId(Long userId);
}
