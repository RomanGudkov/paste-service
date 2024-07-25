package ru.gudkov.test_task.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Paste")
@Getter
@Setter
public class Paste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "body", length = 1000)
    private String pasteBody;
    @Column(name = "time")
    private LocalDateTime expirationTime;
    private String access;
    @Column(name = "hash")
    private String hashCode;
}
