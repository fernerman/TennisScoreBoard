package org.project.tennisscoreboard.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.project.tennisscoreboard.exception.PlayerInvalidNameException;
import org.project.tennisscoreboard.service.MatchService;
import org.project.tennisscoreboard.util.PlayerValidationUtils;

@WebServlet("/new-match")
public class CreateNewMatchServlet extends HttpServlet {

  public static final String PITCHER_PARAMETER_NAME = "pitcher-name";
  public static final String HOST_PARAMETER_NAME = "host-name";
  private final MatchService matchService = new MatchService();

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    try {
      String namePitcher = request.getParameter(PITCHER_PARAMETER_NAME);
      String nameHost = request.getParameter(HOST_PARAMETER_NAME);
      PlayerValidationUtils.validateName(nameHost);
      PlayerValidationUtils.validateName(namePitcher);

      UUID matchId = matchService.createCurrentMatch(namePitcher, nameHost);
      response.sendRedirect("/match-score?uuid=" + matchId);
    } catch (PlayerInvalidNameException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("Invalid player name: " + e.getMessage());
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}