package org.project.tennisscoreboard.service;

import java.util.UUID;
import org.project.tennisscoreboard.dao.MatchDao;
import org.project.tennisscoreboard.dao.PlayerDao;
import org.project.tennisscoreboard.exception.MatchNotCreatedException;
import org.project.tennisscoreboard.model.MatchScore;

public class FinishedMatchesPersistenceService {

  private final static MatchDao finishedMatchesDao = new MatchDao();
  private final static PlayerDao playerDao = new PlayerDao();

  public void save(MatchScore matchScore, UUID winnerId) throws MatchNotCreatedException {
    try {
      finishedMatchesDao.create(
          matchScore.getPlayerPitcher(),
          matchScore.getPlayerHost(),
          playerDao.findById(winnerId));
    } catch (Exception e) {
      throw new MatchNotCreatedException("Match not created");
    }
  }
}
