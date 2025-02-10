package org.project.tennisscoreboard.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;
import org.project.tennisscoreboard.dto.MatchScore;
import org.project.tennisscoreboard.dto.ScoreOutputDTO;
import org.project.tennisscoreboard.exception.MatchNotFoundException;
import org.project.tennisscoreboard.model.CollectionMatches;
import org.project.tennisscoreboard.service.MatchService;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

  public static final String PARAMETER_MATCH_ID = "uuid";
  public static final String PARAMETER_WINNER_ID = "winnerId";
  private final MatchService matchService = new MatchService();

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    try {
      String matchUuidString = request.getParameter(PARAMETER_MATCH_ID);
      String winnerIdByString = request.getParameter(PARAMETER_WINNER_ID);
      UUID matchUuid = UUID.fromString(matchUuidString);
      UUID winnerId = UUID.fromString(winnerIdByString);

      MatchScore matchScore = CollectionMatches.matches.get(matchUuid);
      if (matchScore == null) {
        throw new MatchNotFoundException("Match not found");
      }
      ScoreOutputDTO newMatchResult = matchService.updateMatchScore(matchUuid, winnerId,
          matchScore);
      //toDo сделать для фронта отправку MatchResultDTO
    } catch (IllegalArgumentException e) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Wrong format UUID");
    } catch (MatchNotFoundException e) {
      response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
    } catch (SQLException e) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    super.doGet(req, resp);
  }
}
