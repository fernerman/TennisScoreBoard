package org.project.tennisscoreboard.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.project.tennisscoreboard.dao.MatchDao;
import org.project.tennisscoreboard.model.entity.Match;


public class FilterFinishedMatchesService {

  public static final int MATCHES_PER_PAGE = 3;
  private final MatchDao matchDao = new MatchDao();

  @SneakyThrows
  public List<Match> findAllMatches() {
    return matchDao.findAll();
  }

  @SneakyThrows
  public List<Match> filterMatchesByName(String filterByName, List<Match> matches) {
    return matches.stream()
        .filter(match ->
            filterByName.equals(match.getPitcher().getName()) ||
                filterByName.equals(match.getHost().getName()))
        .collect(Collectors.toList());
  }

  public List<Match> filterMatchesByPage(int currentNumberPage, int countPages,
      List<Match> matches) {
    if (currentNumberPage < 1 || currentNumberPage > countPages) {
      throw new IllegalArgumentException("currentPage is out of range");
    }
    int totalMatches = matches.size();
    int startIndex = (currentNumberPage - 1) * MATCHES_PER_PAGE;
    int endIndex = Math.min(startIndex + MATCHES_PER_PAGE, totalMatches);
    return matches.subList(startIndex, endIndex);
  }
}
