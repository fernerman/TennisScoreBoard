package org.project.tennisscoreboard.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Table(name = "Matches")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Match {
    @UuidGenerator
    @Id
    private UUID id;
    @OneToOne
    @JoinColumn(name = "Player1", nullable = false)
    private Player pitcher;
    @OneToOne
    @JoinColumn(name = "Player2", nullable = false)
    private Player host;
    @OneToOne
    @JoinColumn(name = "Winner", nullable = false)
    private Player winner;
}