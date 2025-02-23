package org.project.tennisscoreboard.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.SneakyThrows;
import org.project.tennisscoreboard.listener.AppContextListener;
import org.project.tennisscoreboard.model.entity.Match;
import org.project.tennisscoreboard.service.FilterFinishedMatchesService;
import org.project.tennisscoreboard.util.JspHelper;

@WebServlet("/matches/*")
public class MatchesController extends HttpServlet {

  private static final String PAGE = "page";
  private static final String FILTER_BY_PLAYER_NAME = "filter_by_player_name";
  private FilterFinishedMatchesService filterFinishedMatchesService;

  @Override
  public void init() {
    ServletContext servletContext = getServletContext();
    filterFinishedMatchesService = (FilterFinishedMatchesService) servletContext.getAttribute(
        AppContextListener.FILTER_FINISHED_MATCHES_PERSISTENCE_SERVICE);
  }

  @SneakyThrows
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    List<Match> displayedMatches = filterFinishedMatchesService.findAllMatches();
    String pageNumberByString = req.getParameter(PAGE);
    String filterByPlayerName = req.getParameter(FILTER_BY_PLAYER_NAME);
    if (filterByPlayerName != null && !filterByPlayerName.isEmpty()) {
      displayedMatches = filterFinishedMatchesService.filterMatchesByName(filterByPlayerName,
          displayedMatches);
    }
    int currentPage = getCurrentPage(pageNumberByString);
    int countPages = (int) Math.ceil(
        (double) displayedMatches.size() / FilterFinishedMatchesService.MATCHES_PER_PAGE);

    displayedMatches = filterFinishedMatchesService.filterMatchesByPage(currentPage, countPages,
        displayedMatches);
    req.setAttribute("currentPage", currentPage);
    req.setAttribute("countPages", countPages);
    req.setAttribute("matches", displayedMatches);
    req.getRequestDispatcher(JspHelper.getPath("matches")).forward(req, resp);
  }

  private int getCurrentPage(String page) {
    int currentPage = 1;
    if (page != null && !page.isEmpty()) {
      currentPage = Integer.parseInt(page);
    }
    return currentPage;
  }
}
