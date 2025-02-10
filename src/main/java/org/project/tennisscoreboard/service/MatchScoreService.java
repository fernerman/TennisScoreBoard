package org.project.tennisscoreboard.service;

import org.project.tennisscoreboard.dto.WinnerLoserDTO;
import org.project.tennisscoreboard.model.Score;

public class MatchScoreService {

  private final static int[] POINT_VALUES = {0, 15, 30, 40};
  private final static String DEUCE = "DE";
  private final static String ADVANTAGE = "AD";
  private final static int MAX_GAMES_IN_SET = 6;
  private final static int MAX_POINTS_IN_TIE_BREAK = 7;
  private final static int MIN_DIFFERENCE_POINTS = 2;

  public WinnerLoserDTO updateScore(WinnerLoserDTO winnerLoserDTO) {
    Score scoreWinner = winnerLoserDTO.getWinnerScore();
    Score scoreLoser = winnerLoserDTO.getLoserScore();
    if (!isTieBreak(scoreWinner.getGame(), scoreLoser.getGame())) {
      updatePoints(scoreWinner, scoreLoser);
      updateGames(scoreWinner, scoreLoser);
    } else {
      updatePointsInTieBreak(scoreWinner, scoreLoser);
    }
    return winnerLoserDTO;
  }

  public boolean checkWinSet(Score score1, Score score2) {
    return score1.getSet() == 2 || score2.getSet() == 2;
  }

  public void updatePointsInTieBreak(Score currentPlayerScore, Score nextPlayerScore) {
    int currentWinnerPoints = currentPlayerScore.getPoint();
    int currentLostPoints = nextPlayerScore.getPoint();
    currentPlayerScore.setPoint(currentWinnerPoints + 1);
    if (checkWinTieBreak(currentWinnerPoints, currentLostPoints)) {
      currentPlayerScore.setSet(currentPlayerScore.getSet() + 1);
      currentPlayerScore.setPoint(0);
    }
  }

  public void updatePoints(Score currentPlayerScore, Score nextPlayerScore) {
    currentPlayerScore.setPoint(currentPlayerScore.getPoint() + 1);
    if (checkWinInGame(currentPlayerScore.getPoint(), nextPlayerScore.getPoint())) {
      currentPlayerScore.setGame(currentPlayerScore.getGame() + 1);
      resetPoints(currentPlayerScore, nextPlayerScore);
    }
  }

  public void updateGames(Score currentPlayerScore, Score scoreLoser) {
    int currentWinnerGames = currentPlayerScore.getGame();
    int currentLostGames = scoreLoser.getGame();
    if (checkWinInSet(currentWinnerGames, currentLostGames)) {
      currentPlayerScore.setSet(currentPlayerScore.getSet() + 1);
      resetGames(currentPlayerScore, scoreLoser);
    }
  }

  public String getFormattingPoints(Score currentPlayerScore, Score nextPlayerScore) {
    if (currentPlayerScore.getPoint() < POINT_VALUES.length) {
      return String.valueOf(POINT_VALUES[currentPlayerScore.getPoint()]);
    } else if (currentPlayerScore.getPoint() > nextPlayerScore.getPoint()) {
      return ADVANTAGE;
    } else if (currentPlayerScore.getPoint() == nextPlayerScore.getPoint()) {
      return DEUCE;
    }
    return "";
  }

  private void resetPoints(Score player1, Score player2) {
    player1.setPoint(0);
    player2.setPoint(0);
  }

  private void resetGames(Score player1, Score player2) {
    player1.setGame(0);
    player2.setGame(0);
  }

  public boolean checkWinInGame(int currentWinnerPoints, int currentLostPoints) {
    return currentWinnerPoints >= POINT_VALUES.length
        && currentWinnerPoints - currentLostPoints >= MIN_DIFFERENCE_POINTS;
  }

  public boolean checkWinInSet(int currentWinnerGames, int currentLostGames) {
    return currentWinnerGames >= MAX_GAMES_IN_SET
        && currentWinnerGames - currentLostGames >= MIN_DIFFERENCE_POINTS;
  }

  public boolean isTieBreak(int currentWinnerGames, int currentLostGames) {
    return currentWinnerGames == MAX_GAMES_IN_SET && currentLostGames == MAX_GAMES_IN_SET;
  }

  public boolean checkWinTieBreak(int currentWinnerGames, int currentLostGames) {
    return currentWinnerGames >= MAX_POINTS_IN_TIE_BREAK
        && currentWinnerGames - currentLostGames >= MIN_DIFFERENCE_POINTS;
  }
}
