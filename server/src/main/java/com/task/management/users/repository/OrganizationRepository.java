package com.task.management.users.repository;

import com.task.management.users.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository  extends JpaRepository<Organization, Long> {
    Boolean existsByDomainName(String domainName);
    Organization getOrganizationByDomainName(String domainName);
}
