package org.project.tennisscoreboard.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(name = "PLAYERS")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private UUID id;
    @Column(name = "NAME")
    private String name;
    @Transient
    private Score score;
}