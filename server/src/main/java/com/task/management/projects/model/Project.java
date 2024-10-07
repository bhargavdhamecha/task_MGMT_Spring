package com.task.management.projects.model;

import com.task.management.users.model.Organization;
import com.task.management.users.model.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "project_master")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "project_sequence_generator")
    @SequenceGenerator(name = "project_sequence_generator", sequenceName="project_sequence", allocationSize = 1)
    @Column(unique = true, nullable = false)
    private long projectId;

    @Column(nullable = false)
    private String projectCode;


    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;

    @Column(nullable = false)
    private String projectTitle;

    @Column
    private String description;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
