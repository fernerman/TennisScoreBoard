package org.project.tennisscoreboard.service;

import java.sql.SQLException;
import java.util.Optional;
import org.project.tennisscoreboard.dao.MatchDao;
import org.project.tennisscoreboard.dao.PlayerDao;
import org.project.tennisscoreboard.exception.PlayerNotFoundException;
import org.project.tennisscoreboard.model.CollectionMatches;
import org.project.tennisscoreboard.model.Match;
import org.project.tennisscoreboard.model.Player;

public class MatchService {

  private final PlayerDao playerDao = new PlayerDao();
  private final MatchDao matchDao = new MatchDao();

  public Optional<Match> createMatch(String pitcherName, String hostName) throws SQLException {
    Optional<Player> playerPitcher = playerDao.create(pitcherName);
    Optional<Player> playerHost = playerDao.create(hostName);
    if (playerPitcher.isPresent() && playerHost.isPresent()) {
      Match match = matchDao.create(playerPitcher.get(), playerHost.get());
      CollectionMatches.matches.put(
          match.getId(),
          match.getScore()
      );
      return Optional.of(match);
    }
    throw new PlayerNotFoundException("Player not found");
  }
}
