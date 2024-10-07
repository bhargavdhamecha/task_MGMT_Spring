package com.task.management.tasks.model;

import com.task.management.projects.model.Project;
import com.task.management.shared.constant.TaskStatus;
import com.task.management.shared.constant.TaskType;
import com.task.management.users.model.Organization;
import com.task.management.users.model.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "task_master")
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "task_sequence_generator")
    @SequenceGenerator(name = "task_sequence_generator", sequenceName="task_sequence", allocationSize = 1)
    @Column(unique = true, nullable = false)
    private long taskId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;

    @Column
    private int originalEstimate;

    @Column
    private int remainingEstimate;

    @Column
    private long currentAssignee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskType taskType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Column
    private long reporter;
}
