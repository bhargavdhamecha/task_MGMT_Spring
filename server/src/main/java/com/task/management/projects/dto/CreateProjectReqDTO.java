package com.task.management.projects.dto;

import lombok.Data;

@Data
public class CreateProjectReqDTO {
    private String projectCode;
    private String projectTitle;
    private String description;
}
