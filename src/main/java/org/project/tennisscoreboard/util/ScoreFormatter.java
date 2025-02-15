package org.project.tennisscoreboard.util;

import org.project.tennisscoreboard.model.MatchScore;
import org.project.tennisscoreboard.model.Score;

public class ScoreFormatter {

  public static final String DEUCE = "DE";
  public static final String ADVANTAGE = "AD";
  private static final int MIN_POINTS_FOR_ADVANTAGE = 3;
  private static final int ADVANTAGE_DIFFERENCE = 1;
  private static final int[] POINT_VALUES = {0, 15, 30, 40};

  public String getFormattingPoints(MatchScore matchScore) {
    Score currentPlayerScore = matchScore.getScorePitcher();
    Score nextPlayerScore = matchScore.getScoreHost();
    int currentWinnerPoints = currentPlayerScore.getPoint();
    int currentLoserPoints = nextPlayerScore.getPoint();

    if (currentWinnerPoints >= MIN_POINTS_FOR_ADVANTAGE
        && currentLoserPoints >= MIN_POINTS_FOR_ADVANTAGE) {
      if (currentWinnerPoints - currentLoserPoints == ADVANTAGE_DIFFERENCE) {
        return ADVANTAGE;
      } else if (currentWinnerPoints == currentLoserPoints) {
        return DEUCE;
      }
    }
    return String.valueOf(POINT_VALUES[currentWinnerPoints]);
  }
}