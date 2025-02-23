package org.project.tennisscoreboard.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MATCHES")
public class Match {

  @Id
  @GeneratedValue
  @Column(name = "ID")
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "PLAYER1", nullable = false)
  private Player pitcher;

  @ManyToOne
  @JoinColumn(name = "PLAYER2", nullable = false)
  private Player host;

  @ManyToOne
  @JoinColumn(name = "WINNER")
  private Player winner;
}