package org.project.tennisscoreboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Score {

  private int point;
  private int game;
  private int set;

  public void setPoint(int point) {
    this.point = point;
  }

  public void setGame(int game) {
    this.game = game;
  }

  public void setSet(int set) {
    this.set = set;
  }
}