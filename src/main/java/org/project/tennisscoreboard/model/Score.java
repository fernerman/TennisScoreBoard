package org.project.tennisscoreboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Score {

  private int point;
  private String pointInTennisBoard;
  private int game;
  private int set;
}

