package org.project.tennisscoreboard.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.tennisscoreboard.model.MatchScore;
import org.project.tennisscoreboard.model.Player;
import org.project.tennisscoreboard.model.Score;

class MatchScoreCalculationServiceTest {

  private MatchScoreCalculationService matchScoreCalculationService;
  private MatchScore matchScore;
  private UUID player1Id;
  private UUID player2Id;

  @BeforeEach
  void setUp() {
    matchScoreCalculationService = new MatchScoreCalculationService();
    player1Id = UUID.randomUUID();
    player2Id = UUID.randomUUID();
    Score score1 = new Score(0, 0, 0);
    Score score2 = new Score(0, 0, 0);
    Player player1 = new Player(player1Id, "Player 1");
    Player player2 = new Player(player2Id, "Player 2");
    matchScore = new MatchScore(player1, player2, score1, score2);
  }

  @Test
  void testUpdateMatchScore_Player1WinsPoint() {
    matchScoreCalculationService.updateMatchScore(matchScore, player1Id);
    assertEquals(1, matchScore.getScorePitcher().getPoint());
    assertEquals(0, matchScore.getScoreHost().getPoint());
  }

  @Test
  void testUpdateMatchScore_Player2WinsPoint() {
    matchScoreCalculationService.updateMatchScore(matchScore, player2Id);
    assertEquals(0, matchScore.getScorePitcher().getPoint());
    assertEquals(1, matchScore.getScoreHost().getPoint());
  }

  @Test
  void testUpdateMatchScore_PlayerWinsGame() {
    Score score1 = new Score(3, 0, 0);
    Score score2 = new Score(2, 0, 0);
    matchScore.setScorePitcher(score1);
    matchScore.setScoreHost(score2);

    matchScoreCalculationService.updateMatchScore(matchScore, player1Id);
    assertEquals(1, matchScore.getScorePitcher().getGame());
    assertEquals(0, matchScore.getScorePitcher().getPoint());
    assertEquals(0, matchScore.getScoreHost().getPoint());
  }

  @Test
  void testDeuce() {
    Score scorePlayer1 = new Score(3, 0, 0);
    Score scorePlayer2 = new Score(3, 0, 0);

    matchScore.setScorePitcher(scorePlayer1);
    matchScore.setScoreHost(scorePlayer2);

    assertTrue(matchScoreCalculationService.isDeuce(
            matchScore.getScorePitcher(), matchScore.getScoreHost()),
        "deuce matchScore should be true");
  }

  @Test
  void testAdvantage() {
    Score scorePlayer1 = new Score(3, 0, 0);
    Score scorePlayer2 = new Score(3, 0, 0);

    matchScore.setScorePitcher(scorePlayer1);
    matchScore.setScoreHost(scorePlayer2);
    matchScoreCalculationService.updateMatchScore(matchScore, player1Id);
    assertTrue(
        matchScoreCalculationService.isAdvantage(matchScore.getScorePitcher(),
            matchScore.getScoreHost()),
        "player1 has advantage and should be deuce is true");

    matchScoreCalculationService.updateMatchScore(matchScore, player1Id);
    assertEquals(1, matchScore.getScorePitcher().getGame());
  }

  @Test
  void testUpdateMatchScore_PlayerWinsSet() {
    Score score1 = new Score(3, 5, 0);
    Score score2 = new Score(2, 4, 0);
    matchScore.setScorePitcher(score1);
    matchScore.setScoreHost(score2);

    matchScoreCalculationService.updateMatchScore(matchScore, player1Id);
    assertEquals(1, matchScore.getScorePitcher().getSet());
    assertEquals(0, matchScore.getScorePitcher().getPoint());
    assertEquals(0, matchScore.getScoreHost().getGame());
  }

  @Test
  void testUpdateMatchScore_PlayerWinsSetInTieBreak() {
    Score score1 = new Score(6, 6, 0);
    Score score2 = new Score(5, 6, 0);
    matchScore.setScorePitcher(score1);
    matchScore.setScoreHost(score2);
    matchScoreCalculationService.updateMatchScore(matchScore, player1Id);
    assertEquals(1, matchScore.getScorePitcher().getSet());
  }

  @Test
  void testEndMatch() {
    Score score1 = new Score(6, 6, 2);
    Score score2 = new Score(5, 5, 2);
    matchScore.setScorePitcher(score1);
    matchScore.setScoreHost(score2);
    matchScoreCalculationService.updateMatchScore(matchScore, player1Id);
    assertTrue(matchScoreCalculationService.isEndMatchScore(matchScore));
  }

  @Test
  void testUpdateMatchScore_UnknownPlayerId() {
    // Проверка на неизвестного игрока
    UUID unknownPlayerId = UUID.randomUUID();

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> {
          matchScoreCalculationService.updateMatchScore(matchScore, unknownPlayerId);
        });
    assertEquals("Unknown player ID: " + unknownPlayerId, exception.getMessage());
  }
}