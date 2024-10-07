package com.task.management.users.service;

import com.task.management.dto.SignupRequestDTO;
import com.task.management.security.service.AuthService;
import com.task.management.shared.dto.ApiResponse;
import com.task.management.shared.utils.EntityDTOMapper;
import com.task.management.shared.utils.StringUtils;
import com.task.management.users.dto.CreateOrgReqDTO;
import com.task.management.users.model.Organization;
import com.task.management.users.repository.OrganizationRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final EntityDTOMapper entityDTOMapper;
    private final AuthService authService;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository, EntityDTOMapper entityDTOMapper, @Lazy AuthService authService) {
        this.organizationRepository = organizationRepository;
        this.entityDTOMapper = entityDTOMapper;
        this.authService = authService;
    }

    public ApiResponse<Object> registerOrgForSignUp(SignupRequestDTO requestDTO, HttpServletResponse response){
        Organization org = createOrg(new CreateOrgReqDTO(StringUtils.getDomainNameFromEmail(requestDTO.getUserEmail()),""));
        authService.signUp(requestDTO, response);
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Organization created successfully", entityDTOMapper.toOrgDTO(org));
    }

    public Organization createOrg(CreateOrgReqDTO createOrgReqDTO){
        return organizationRepository.save(entityDTOMapper.toOrgEntity(createOrgReqDTO));
    }

    public boolean checkOrgExists(String domainName){
        return organizationRepository.existsByDomainName(domainName);
    }

    public Organization getOrgEntity(String domainName){
        return organizationRepository.getOrganizationByDomainName(domainName);
    }

}
