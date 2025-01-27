package org.project.tennisscoreboard.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PLAYERS")
public class Player {

  @Id
  @GeneratedValue
  @Column(name = "ID")
  private UUID id;

  @Column(name = "NAME")
  private String name;
}