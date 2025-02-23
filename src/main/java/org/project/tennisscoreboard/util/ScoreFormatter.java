package org.project.tennisscoreboard.util;

import lombok.experimental.UtilityClass;
import org.project.tennisscoreboard.model.Score;
import org.project.tennisscoreboard.service.MatchScoreCalculationService;

@UtilityClass
public class ScoreFormatter {

  public static final String DEUCE = "40";
  public static final String ADVANTAGE = "AD";
  private static final int[] POINT_VALUES = {0, 15, 30, 40};
  private static final MatchScoreCalculationService serviceScore = new MatchScoreCalculationService();

  public String getFormattingPoints(Score currentPlayerScore, Score nextPlayerScore) {
    int currentWinnerPoints = currentPlayerScore.getPoint();

    if (serviceScore.isTieBreak(currentPlayerScore, nextPlayerScore)) {
      return String.valueOf(currentPlayerScore.getPoint());
    } else if (serviceScore.isAdvantage(currentPlayerScore, nextPlayerScore)) {
      return ADVANTAGE;
    } else if (serviceScore.isDeuce(currentPlayerScore, nextPlayerScore)
        || currentPlayerScore.getPoint() >= 3) {
      return DEUCE;
    }
    return String.valueOf(POINT_VALUES[currentWinnerPoints]);
  }
}