package org.project.tennisscoreboard.service;

import java.util.UUID;
import org.project.tennisscoreboard.model.MatchScore;
import org.project.tennisscoreboard.model.Score;

public class MatchScoreCalculationService {

  private final static int MAX_GAMES_IN_SET = 6;
  private final static int MAX_POINTS_IN_TIE_BREAK = 7;
  private final static int MIN_DIFFERENCE_POINTS = 2;
  private static final int MIN_POINTS_FOR_ADVANTAGE = 3;
  private static final int ADVANTAGE_DIFFERENCE = 1;

  public MatchScore updateMatchScore(MatchScore matchScore, UUID winnerId) {
    Score scorePlayer1 = matchScore.getPitcherScore();
    Score scorePlayer2 = matchScore.getHostScore();

    if (winnerId.equals(matchScore.getPlayerPitcher().getId())) {
      updateScore(scorePlayer1, scorePlayer2);
    } else if (winnerId.equals(matchScore.getPlayerHost().getId())) {
      updateScore(scorePlayer2, scorePlayer1);
    } else {
      throw new IllegalArgumentException("Unknown player ID: " + winnerId);
    }
    return matchScore;
  }

  private void updateScore(Score scoreWinner, Score scoreLoser) {
    if (!isTieBreak(scoreWinner, scoreLoser)) {
      updatePoints(scoreWinner, scoreLoser);
      updateGames(scoreWinner, scoreLoser);
    } else {
      updatePointsInTieBreak(scoreWinner, scoreLoser);
    }
  }

  public boolean isDeuce(Score scoreWinner, Score scoreLoser) {
    int winnerPoints = scoreWinner.getPoint();
    int loserPoints = scoreLoser.getPoint();
    return loserPoints >= MIN_POINTS_FOR_ADVANTAGE
        && winnerPoints == loserPoints;
  }

  public boolean isAdvantage(Score scoreWinner, Score scoreLoser) {
    int winnerPoints = scoreWinner.getPoint();
    int loserPoints = scoreLoser.getPoint();
    return winnerPoints >= MIN_POINTS_FOR_ADVANTAGE
        && loserPoints >= MIN_POINTS_FOR_ADVANTAGE
        && (winnerPoints - loserPoints == ADVANTAGE_DIFFERENCE);
  }

  public boolean isTieBreak(Score scoreWinner, Score scoreLoser) {
    return scoreWinner.getGame() == MAX_GAMES_IN_SET && scoreLoser.getGame() == MAX_GAMES_IN_SET;
  }

  public boolean isEndMatchScore(MatchScore matchScore) {
    return checkWinSet(matchScore.getPitcherScore(), matchScore.getHostScore());
  }

  public boolean checkWinSet(Score score1, Score score2) {
    return score1.getSet() == 2 || score2.getSet() == 2;
  }

  public void updatePointsInTieBreak(Score currentPlayerScore, Score nextPlayerScore) {
    currentPlayerScore.setPoint(currentPlayerScore.getPoint() + 1);

    int currentLostPoints = nextPlayerScore.getPoint();
    int currentWinnerPoints = currentPlayerScore.getPoint();
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

  private void resetPoints(Score player1, Score player2) {
    player1.setPoint(0);
    player2.setPoint(0);
  }

  private void resetGames(Score player1, Score player2) {
    player1.setGame(0);
    player2.setGame(0);
  }

  private boolean checkWinInGame(int currentWinnerPoints, int currentLostPoints) {
    return currentWinnerPoints >= 4
        && currentWinnerPoints - currentLostPoints >= MIN_DIFFERENCE_POINTS;
  }

  private boolean checkWinInSet(int currentWinnerGames, int currentLostGames) {
    return currentWinnerGames >= MAX_GAMES_IN_SET
        && currentWinnerGames - currentLostGames >= MIN_DIFFERENCE_POINTS;
  }

  private boolean checkWinTieBreak(int currentWinnerGames, int currentLostGames) {
    return currentWinnerGames >= MAX_POINTS_IN_TIE_BREAK
        && currentWinnerGames - currentLostGames >= MIN_DIFFERENCE_POINTS;
  }
}
