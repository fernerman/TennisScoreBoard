package org.project.tennisscoreboard.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import org.project.tennisscoreboard.model.Match;
import org.project.tennisscoreboard.model.MatchScore;
import org.project.tennisscoreboard.service.FinishedMatchesPersistenceService;
import org.project.tennisscoreboard.service.MatchScoreCalculationService;
import org.project.tennisscoreboard.service.OngoingMatchesService;

@WebServlet("/match-score/*")
public class MatchScoreController extends HttpServlet {

  public static final String PARAMETER_MATCH_ID = "uuid";
  public static final String PARAMETER_WINNER_ID = "winnerId";
  private final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
  private final MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
  private final FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    String matchUuidString = request.getParameter(PARAMETER_MATCH_ID);
    String winnerIdByString = request.getParameter(PARAMETER_WINNER_ID);
    validateUUID(matchUuidString, winnerIdByString);
    UUID matchUuid = UUID.fromString(matchUuidString);
    UUID winnerId = UUID.fromString(winnerIdByString);

    MatchScore matchScore = ongoingMatchesService.getMatchScore(matchUuid);
    MatchScore updateMatchScore = matchScoreCalculationService.updateMatchScore(
        matchScore,
        winnerId);
    if (!matchScoreCalculationService.isEndMatchScore(matchScore)) {
      request.setAttribute("matchResponse", updateMatchScore);
    } else {
      ongoingMatchesService.deleteMatchScore(matchUuid);
      Match match = finishedMatchesPersistenceService.save(matchScore, matchUuid);
    }
  }

  private void validateUUID(String matchUuidString, String winnerIdString) {
    if (matchUuidString == null || winnerIdString == null) {
      throw new IllegalArgumentException("Invalid parameter");
    }
  }
}
