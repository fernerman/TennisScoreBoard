package org.project.tennisscoreboard.service;

import java.util.Optional;
import java.util.UUID;
import org.project.tennisscoreboard.dto.MatchScore;
import org.project.tennisscoreboard.dto.WinnerLoserDTO;
import org.project.tennisscoreboard.model.Score;

public class MatchScoreService {

  private final static String[] POINTS_NAMES = new String[]{"0", "15", "30", "40"};
  private final static String DEUCE = "DE";
  private final static String ADVANTAGE = "AD";
  private final static int MAX_GAMES_IN_SET = 6;
  private final static int MAX_POINTS_IN_TIE_BREAK = 7;
  private final static int MIN_DIFFERENCE_POINTS = 2;

  public WinnerLoserDTO getNextScore(WinnerLoserDTO winnerLoserDTO) {
    Score scoreWinner = winnerLoserDTO.getWinnerScore();
    Score scoreLoser = winnerLoserDTO.getLoserScore();
    if (!checkTieBreak(scoreWinner.getGame(), scoreLoser.getGame())) {
      addNextPoint(scoreWinner, scoreLoser);
      addNextGame(scoreWinner, scoreLoser);
      addNextSet(scoreWinner, scoreLoser);
    } else {
      addNextPointInTieBreak(scoreWinner, scoreLoser);
    }
    return winnerLoserDTO;
  }

  public boolean isEndMatch(WinnerLoserDTO winnerLoserDTO) {
    return winnerLoserDTO.getWinnerScore().getSet() == 2;
  }

  public void addNextPointInTieBreak(Score currentPlayerScore, Score nextPlayerScore) {
    int currentWinnerPoints = currentPlayerScore.getPoint();
    int currentLostPoints = nextPlayerScore.getPoint();
    currentPlayerScore.setPoint(currentWinnerPoints + 1);
    if (currentWinnerPoints >= MAX_POINTS_IN_TIE_BREAK
        && currentWinnerPoints - currentLostPoints >= MIN_DIFFERENCE_POINTS) {
      currentPlayerScore.setSet(currentPlayerScore.getSet() + 1);
      currentPlayerScore.setPoint(0);
    }
    currentPlayerScore.setPointInTennisBoard(String.valueOf(currentPlayerScore.getPoint()));
  }


  public void addNextPoint(Score currentPlayerScore, Score nextPlayerScore) {
    currentPlayerScore.setPoint(currentPlayerScore.getPoint() + 1);
    if (currentPlayerScore.getPoint() < POINTS_NAMES.length) {
      currentPlayerScore.setPointInTennisBoard(POINTS_NAMES[currentPlayerScore.getPoint()]);
    } else if (currentPlayerScore.getPoint() > nextPlayerScore.getPoint()) {
      currentPlayerScore.setPointInTennisBoard(ADVANTAGE);
      nextPlayerScore.setPointInTennisBoard("");
    } else if (currentPlayerScore.getPoint() == nextPlayerScore.getPoint()) {
      currentPlayerScore.setPointInTennisBoard(DEUCE);
      nextPlayerScore.setPointInTennisBoard(DEUCE);
    }
  }

  public void addNextGame(Score currentPlayerScore, Score scoreLoser) {
    int currentWinnerPoints = currentPlayerScore.getPoint();
    int currentLostPoints = scoreLoser.getPoint();
    if (checkWinInGame(currentWinnerPoints, currentLostPoints)) {
      currentPlayerScore.setGame(currentPlayerScore.getGame() + 1);
      currentPlayerScore.setPoint(0);
      scoreLoser.setPoint(0);
    }
  }

  public boolean checkWinInGame(int currentWinnerPoints, int currentLostPoints) {
    return currentWinnerPoints >= POINTS_NAMES.length
        && currentWinnerPoints - currentLostPoints >= MIN_DIFFERENCE_POINTS;
  }

  public boolean checkWinInSet(int currentWinnerGames, int currentLostGames) {
    return currentWinnerGames >= MAX_GAMES_IN_SET
        && currentWinnerGames - currentLostGames >= MIN_DIFFERENCE_POINTS;
  }

  public boolean checkTieBreak(int currentWinnerGames, int currentLostGames) {
    return currentWinnerGames == MAX_GAMES_IN_SET && currentLostGames == MAX_GAMES_IN_SET;
  }

  public void addNextSet(Score scorePitcher, Score scoreHost) {
    int currentPitcherGames = scorePitcher.getGame();
    int currentHostGames = scoreHost.getGame();
    if (checkWinInSet(currentPitcherGames, currentHostGames)) {
      scorePitcher.setSet(scorePitcher.getSet() + 1);
      scorePitcher.setGame(0);
      scoreHost.setGame(0);
    }
  }

  public Optional<WinnerLoserDTO> getWinnerAndLoserScore(UUID winnerId, MatchScore match) {
    if (winnerId.equals(match.getPlayerPitcherId())) {
      return Optional.of(new WinnerLoserDTO(match.getScorePitcher(), match.getScoreHost()));
    } else if (winnerId.equals(match.getPlayerHostId())) {
      return Optional.of(new WinnerLoserDTO(match.getScoreHost(), match.getScorePitcher()));
    }
    return Optional.empty();
  }
}
