package com.task.management.users.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "org_master")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "org_sequence_generator")
    @SequenceGenerator(name = "org_sequence_generator", sequenceName="org_sequence", allocationSize = 1)
    @Column(unique = true, nullable = false)
    private long orgId;

    @Column(nullable = false, unique = true)
    private String domainName;

    @Column
    private String description;

    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;

    @OneToMany(mappedBy = "organization")
    private Set<User> users;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
