package org.project.tennisscoreboard.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.project.tennisscoreboard.listener.AppContextListener;
import org.project.tennisscoreboard.model.MatchScore;
import org.project.tennisscoreboard.service.FinishedMatchesPersistenceService;
import org.project.tennisscoreboard.service.MatchScoreCalculationService;
import org.project.tennisscoreboard.service.OngoingMatchesService;
import org.project.tennisscoreboard.util.JspHelper;

@WebServlet("/match-score/*")
public class MatchScoreController extends HttpServlet {

  public static final String PARAMETER_MATCH_ID = "uuid";
  public static final String PARAMETER_WINNER_ID = "winnerId";
  public static final String ATTRIBUTE_MATCH_ID = "matchUuid";
  public static final String ATTRIBUTE_MATCH = "match";
  private OngoingMatchesService ongoingMatchesService;
  private MatchScoreCalculationService matchScoreCalculationService;
  private FinishedMatchesPersistenceService finishedMatchesPersistenceService;

  @Override
  public void init() {
    ServletContext servletContext = getServletContext();
    ongoingMatchesService = (OngoingMatchesService) servletContext.getAttribute(
        AppContextListener.ONGOING_MATCHES_SERVICE);
    matchScoreCalculationService = (MatchScoreCalculationService) servletContext.getAttribute(
        AppContextListener.MATCH_SCORE_CALCULATION_SERVICE
    );
    finishedMatchesPersistenceService = (FinishedMatchesPersistenceService) servletContext.getAttribute(
        AppContextListener.FINISHED_MATCHES_PERSISTENCE_SERVICE);
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse resp)
      throws ServletException, IOException {
    String matchUuidString = request.getParameter(PARAMETER_MATCH_ID);
    if (matchUuidString == null) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Match ID is required");
      return;
    }
    UUID matchUuid = UUID.fromString(matchUuidString);
    MatchScore matchScore = ongoingMatchesService.getMatchScore(matchUuid);
    request.setAttribute(ATTRIBUTE_MATCH, matchScore);
    request.setAttribute(ATTRIBUTE_MATCH_ID, matchUuid);
    request.getRequestDispatcher(JspHelper.getPath("match-score")).forward(request, resp);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String matchUuidString = request.getParameter(PARAMETER_MATCH_ID);
    String winnerIdByString = request.getParameter(PARAMETER_WINNER_ID);
    if (matchUuidString == null || winnerIdByString == null) {
      throw new IllegalArgumentException("Invalid parameter");
    }
    UUID matchUuid = UUID.fromString(matchUuidString);
    UUID winnerId = UUID.fromString(winnerIdByString);

    MatchScore matchScore = ongoingMatchesService.getMatchScore(matchUuid);
    MatchScore updateMatchScore = matchScoreCalculationService.updateMatchScore(matchScore,
        winnerId);
    request.setAttribute(ATTRIBUTE_MATCH, updateMatchScore);
    if (!matchScoreCalculationService.isEndMatchScore(matchScore)) {
      response.sendRedirect(request.getContextPath() + "/match-score?uuid=" + matchUuidString);
    } else {
      finishedMatchesPersistenceService.save(matchScore, winnerId);
      ongoingMatchesService.deleteMatchScore(matchUuid);
      request.getRequestDispatcher(JspHelper.getPath("final-score")).forward(request, response);
    }
  }
}
