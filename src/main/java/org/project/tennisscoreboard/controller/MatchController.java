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
import org.project.tennisscoreboard.service.OngoingMatchesService;
import org.project.tennisscoreboard.util.JspHelper;
import org.project.tennisscoreboard.util.PlayerValidationUtils;

@WebServlet("/new-match")
public class MatchController extends HttpServlet {

  public static final String PITCHER_PARAMETER_NAME = "pitcher-name";
  public static final String HOST_PARAMETER_NAME = "host-name";
  private OngoingMatchesService ongoingMatchesService;

  @Override
  public void init() {
    ServletContext servletContext = getServletContext();
    ongoingMatchesService = (OngoingMatchesService) servletContext.getAttribute(
        AppContextListener.ONGOING_MATCHES_SERVICE);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getRequestDispatcher(JspHelper.getPath("new-match")).forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String namePitcher = request.getParameter(PITCHER_PARAMETER_NAME);
    String nameHost = request.getParameter(HOST_PARAMETER_NAME);
    PlayerValidationUtils.validateName(nameHost);
    PlayerValidationUtils.validateName(namePitcher);

    UUID matchId = ongoingMatchesService.createMatch(namePitcher, nameHost);
    response.sendRedirect("/match-score?uuid=" + matchId);
  }
}