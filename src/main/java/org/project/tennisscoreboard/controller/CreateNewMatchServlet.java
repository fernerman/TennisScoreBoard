package org.project.tennisscoreboard.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.project.tennisscoreboard.exception.MatchNotFoundException;
import org.project.tennisscoreboard.exception.PlayerNotFoundException;
import org.project.tennisscoreboard.exception.PlayerNotValidNameException;
import org.project.tennisscoreboard.model.Match;
import org.project.tennisscoreboard.service.MatchService;
import org.project.tennisscoreboard.util.Checker;

@WebServlet("/new-match")
public class CreateNewMatchServlet extends HttpServlet {

  public static final String NAME_PITCHER_PARAMETER = "name";
  public static final String NAME_HOST_PARAMETER = "name2";
  private final MatchService matchService = new MatchService();

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    try {
      String namePitcher = request.getParameter(NAME_PITCHER_PARAMETER);
      String nameHost = request.getParameter(NAME_HOST_PARAMETER);
      Checker.validateNameAndSurnamePlayer(nameHost);
      Checker.validateNameAndSurnamePlayer(namePitcher);

      Optional<Match> match = matchService.createMatch(namePitcher, nameHost);
      if (match.isEmpty()) {
        throw new MatchNotFoundException("Match not found");
      }
      response.sendRedirect("/match-score?uuid=" + match.get().getId());
    } catch (PlayerNotValidNameException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("Invalid player name: " + e.getMessage());
    } catch (PlayerNotFoundException | MatchNotFoundException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      response.getWriter().write(e.getMessage());
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}