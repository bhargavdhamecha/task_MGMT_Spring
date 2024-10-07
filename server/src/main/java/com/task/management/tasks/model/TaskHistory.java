package com.task.management.tasks.model;

import jakarta.persistence.*;

@Entity
public class TaskHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "history_sequence_generator")
    @SequenceGenerator(name = "history_sequence_generator", sequenceName="history_sequence", allocationSize = 1)
    @Column(unique = true, nullable = false)
    private long historyId;



}
