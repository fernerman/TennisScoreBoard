package org.project.tennisscoreboard.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Table(name = "Players")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;
    @Column(name = "Name")
    private String name;
    @Transient
    private Score score;
}