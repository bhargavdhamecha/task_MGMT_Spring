package com.task.management.users.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@Table(name = "user_master")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_sequence_generator")
    @SequenceGenerator(name = "user_sequence_generator", sequenceName="user_sequence", allocationSize = 1)
    @Column(unique = true, nullable = false)
    private long userId;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String firstName;

    @Column()
    private String lastName;

    @Column(nullable = false, unique = true)
    private String userEmail;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "org_id", nullable = false)
    private Organization organization;

    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;

}
