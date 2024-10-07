package com.task.management.projects.repository;

import com.task.management.projects.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
//    List<Project> find(long orgId);
}
