package org.project.tennisscoreboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScoreOutputDTO {

  private String pointInTennisBoard;
  private int game;
  private int set;
}
