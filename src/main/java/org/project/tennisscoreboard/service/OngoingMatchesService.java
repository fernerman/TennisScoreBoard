package org.project.tennisscoreboard.service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.project.tennisscoreboard.dao.PlayerDao;
import org.project.tennisscoreboard.exception.MatchNotCreatedException;
import org.project.tennisscoreboard.exception.MatchNotFoundException;
import org.project.tennisscoreboard.model.MatchScore;
import org.project.tennisscoreboard.model.Player;
import org.project.tennisscoreboard.model.Score;
import org.project.tennisscoreboard.util.SessionFactoryUtil;

public class OngoingMatchesService {

  private static final ConcurrentHashMap<UUID, MatchScore> MATCHES = new ConcurrentHashMap<>();
  private final PlayerDao playerDao = new PlayerDao();

  public MatchScore getMatchScore(UUID matchUuid) throws MatchNotFoundException {
    if (!MATCHES.containsKey(matchUuid)) {
      throw new MatchNotFoundException("Match not found");
    }
    return MATCHES.get(matchUuid);
  }

  public void deleteMatchScore(UUID matchUuid) throws MatchNotFoundException {
    if (!MATCHES.containsKey(matchUuid)) {
      throw new MatchNotFoundException("Match not found");
    }
    MATCHES.remove(matchUuid);
  }

  public UUID createMatch(String pitcherName, String hostName) throws MatchNotCreatedException {
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      Player playerPitcher = playerDao.findOrCreate(session, pitcherName);
      Player playerHost = playerDao.findOrCreate(session, hostName);

      UUID matchId = UUID.randomUUID();
      MatchScore matchScore = new MatchScore(
          playerPitcher,
          playerHost,
          new Score(),
          new Score());
      MATCHES.put(matchId, matchScore);
      transaction.commit();
      return matchId;
    } catch (Exception e) {
      throw new MatchNotCreatedException("Match not created");
    }
  }
}
