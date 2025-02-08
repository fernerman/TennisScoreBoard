package org.project.tennisscoreboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.tennisscoreboard.model.Score;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WinnerLoserDTO {

  private Score winnerScore;
  private Score loserScore;
}
