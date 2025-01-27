package org.project.tennisscoreboard.service;

import java.sql.SQLException;
import java.util.Optional;
import org.project.tennisscoreboard.dao.MatchDao;
import org.project.tennisscoreboard.dao.PlayerDao;
import org.project.tennisscoreboard.exception.MatchNotCreatedException;
import org.project.tennisscoreboard.exception.PlayerNotCreatedException;
import org.project.tennisscoreboard.model.CollectionMatches;
import org.project.tennisscoreboard.model.Match;
import org.project.tennisscoreboard.model.Player;

public class MatchService {

  private final PlayerDao playerDao = new PlayerDao();
  private final MatchDao matchDao = new MatchDao();

  public Match createMatch(String pitcherName, String hostName) throws SQLException {
    Optional<Player> playerPitcher = playerDao.create(pitcherName);
    Optional<Player> playerHost = playerDao.create(hostName);
    if (playerPitcher.isPresent() && playerHost.isPresent()) {
      Optional<Match> matchOptional = matchDao.create(playerPitcher.get(), playerHost.get());
      if (matchOptional.isPresent()) {
        Match match = matchOptional.get();
        CollectionMatches.matches.put(
            match.getId(),
            match.getScorePitcher()
        );
        return match;
      }
      throw new MatchNotCreatedException("Match not created");
    }
    throw new PlayerNotCreatedException("Player not created");
  }
}
