package com.task.management.users.controller;

import com.task.management.dto.SignupRequestDTO;
import com.task.management.shared.constant.ApiConstant;
import com.task.management.shared.dto.ApiResponse;
import com.task.management.users.service.OrganizationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = ApiConstant.ORG_CONTROLLER, produces = MediaType.APPLICATION_JSON_VALUE)
public class OrganizationController {
    private final OrganizationService orgService;

    public OrganizationController(OrganizationService orgService) {
        this.orgService = orgService;
    }

    @PostMapping(value = ApiConstant.REGISTER_ORG)
    public ApiResponse<Object> registerOrgNSignUp(@RequestBody SignupRequestDTO signupRequestDTO, HttpServletResponse response){
        return orgService.registerOrgForSignUp(signupRequestDTO, response);
    }
}
