package com.task.management.projects.service;

import com.task.management.projects.dto.CreateProjectReqDTO;
import com.task.management.projects.model.Project;
import com.task.management.projects.repository.ProjectRepository;
import com.task.management.shared.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@Slf4j
public class ProjectService {

    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ApiResponse<?> getAllProjectsOfCurrentOrg(){
//        List<Project> ls = projectRepository.fin(1);
//        log.debug("123");=
        return new ApiResponse<>(HttpStatus.OK.value(), "",null);
    }
//
    public ApiResponse<?> createProject(CreateProjectReqDTO projectReqDTO){
        Project pr = new Project();
        pr.setProjectCode(projectReqDTO.getProjectCode());
        pr.setProjectTitle(projectReqDTO.getProjectTitle());
        pr.setDescription(projectReqDTO.getDescription());
        return new ApiResponse<>(HttpStatus.CREATED.value(), "", null);
    }
//
//    public ApiResponse<?> updateProject(){
//
//    }

}

