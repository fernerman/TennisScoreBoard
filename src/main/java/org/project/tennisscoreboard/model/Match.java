package org.project.tennisscoreboard.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
  private UUID id;
  @OneToOne
  @JoinColumn(name = "PLAYER_PITCHER_ID", nullable = false)
  private Player pitcher;
  @OneToOne
  @JoinColumn(name = "PLAYER_HOST_ID", nullable = false)
  private Player host;
  @OneToOne
  @JoinColumn(name = "WINNER_ID")
  private Player winner;
  @Transient
  private Score score;
}