package org.project.tennisscoreboard.service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.project.tennisscoreboard.exception.MatchNotFoundException;
import org.project.tennisscoreboard.model.MatchScore;
import org.project.tennisscoreboard.model.Score;
import org.project.tennisscoreboard.model.entity.Player;
import org.project.tennisscoreboard.util.SessionFactoryUtil;

public class OngoingMatchesService {

  private static final ConcurrentHashMap<UUID, MatchScore> MATCHES = new ConcurrentHashMap<>();
  private static final PlayerService playerService = new PlayerService();

  @SneakyThrows
  public UUID createMatch(String pitcherName, String hostName) {
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      Player playerPitcher = playerService.getOrCreatePlayer(session, pitcherName);
      Player playerHost = playerService.getOrCreatePlayer(session, hostName);
      UUID uuidMatch = createAndStoreMatch(playerPitcher, playerHost);
      transaction.commit();
      return uuidMatch;
    }
  }

  @SneakyThrows
  public MatchScore getMatchScore(UUID matchUuid) {
    if (!MATCHES.containsKey(matchUuid)) {
      throw new MatchNotFoundException("Match not found");
    }
    return MATCHES.get(matchUuid);
  }

  @SneakyThrows
  public void deleteMatchScore(UUID matchUuid) {
    if (!MATCHES.containsKey(matchUuid)) {
      throw new MatchNotFoundException("Match not found");
    }
    MATCHES.remove(matchUuid);
  }

  private UUID createAndStoreMatch(Player pitcher, Player host) {
    UUID matchId = UUID.randomUUID();
    MatchScore matchScore = new MatchScore(pitcher, host, new Score(), new Score());
    MATCHES.put(matchId, matchScore);
    return matchId;
  }
}
