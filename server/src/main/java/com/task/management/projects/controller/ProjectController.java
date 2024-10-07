package com.task.management.projects.controller;

import com.task.management.projects.dto.CreateProjectReqDTO;
import com.task.management.projects.service.ProjectService;
import com.task.management.shared.constant.ApiConstant;
import com.task.management.shared.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = ApiConstant.PROJECT_CONTROLLER, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping(value = ApiConstant.CREATE_PROJECT)
    public ApiResponse<Object> createProject(@RequestBody CreateProjectReqDTO projectReqDTO){
        projectService.createProject(projectReqDTO);
        return new ApiResponse<>(HttpStatus.CREATED.value(), "", null);
    }
}
