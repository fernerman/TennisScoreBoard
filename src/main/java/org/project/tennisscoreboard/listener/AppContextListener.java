package org.project.tennisscoreboard.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.project.tennisscoreboard.service.FilterFinishedMatchesService;
import org.project.tennisscoreboard.service.FinishedMatchesPersistenceService;
import org.project.tennisscoreboard.service.MatchScoreCalculationService;
import org.project.tennisscoreboard.service.OngoingMatchesService;

@WebListener
public class AppContextListener implements ServletContextListener {

  public static final String ONGOING_MATCHES_SERVICE = "ongoingMatchesService";
  public static final String MATCH_SCORE_CALCULATION_SERVICE = "matchScoreCalculationService";
  public static final String FINISHED_MATCHES_PERSISTENCE_SERVICE = "finishedMatchesPersistenceService";
  public static final String FILTER_FINISHED_MATCHES_PERSISTENCE_SERVICE = "filterFinishedMatchesService";

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    sce.getServletContext().log("AppContextListener run!");
    ServletContext servletContext = sce.getServletContext();
    FilterFinishedMatchesService filterFinishedMatchesService = new FilterFinishedMatchesService();
    FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();
    MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
    OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();

    servletContext.setAttribute(FILTER_FINISHED_MATCHES_PERSISTENCE_SERVICE,
        filterFinishedMatchesService);
    servletContext.setAttribute(FINISHED_MATCHES_PERSISTENCE_SERVICE,
        finishedMatchesPersistenceService);
    servletContext.setAttribute(MATCH_SCORE_CALCULATION_SERVICE,
        matchScoreCalculationService);
    servletContext.setAttribute(ONGOING_MATCHES_SERVICE,
        ongoingMatchesService);
    servletContext.log("Services successfully initialized");
  }
}
