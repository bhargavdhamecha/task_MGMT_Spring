package com.task.management.shared.utils;

import com.task.management.users.dto.CreateOrgReqDTO;
import com.task.management.users.model.Organization;
import org.springframework.stereotype.Component;

@Component
public class EntityDTOMapper {

    public Organization toOrgEntity(CreateOrgReqDTO createOrgReqDTO){
        if(createOrgReqDTO==null)
            return null;
        Organization org = new Organization();
        org.setDomainName(createOrgReqDTO.getDomainName());
        org.setDescription(createOrgReqDTO.getDescription());
        return org;
    }

    public CreateOrgReqDTO toOrgDTO(Organization organization){
        if(organization == null)
            return null;
        CreateOrgReqDTO createOrgReqDTO = new CreateOrgReqDTO();
        createOrgReqDTO.setDomainName(organization.getDomainName());
        createOrgReqDTO.setDescription(organization.getDescription());
        return createOrgReqDTO;
    }
}
