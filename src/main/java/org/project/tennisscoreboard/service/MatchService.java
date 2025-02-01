package org.project.tennisscoreboard.service;

import java.sql.SQLException;
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
    Player playerPitcher = playerDao.create(pitcherName).orElseThrow(
        () -> new PlayerNotCreatedException("Player not created")
    );
    Player playerHost = playerDao.create(hostName).orElseThrow(
        () -> new PlayerNotCreatedException("Player not created")
    );

    Match match = matchDao.create(playerPitcher, playerHost).orElseThrow(
        () -> new MatchNotCreatedException("Match not created")
    );
    CollectionMatches.matches.put(
        match.getId(),
        match.getScorePitcher()
    );
    return match;
  }
}
