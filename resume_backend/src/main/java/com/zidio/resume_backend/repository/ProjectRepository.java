package com.zidio.resume_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zidio.resume_backend.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
	List<Project> findByUserId(Long userId);

}