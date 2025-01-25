package org.project.tennisscoreboard.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(name = "MATCHES")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Match {

    @Id
    @GeneratedValue
    private UUID id;
    @OneToOne
    @JoinColumn(name = "PLAYER1", nullable = false)
    private Player pitcher;
    @OneToOne
    @JoinColumn(name = "PLAYER2", nullable = false)
    private Player host;
    @OneToOne
    @JoinColumn(name = "WINNER")
    private Player winner;
}