package org.project.tennisscoreboard.service;

import java.sql.SQLException;
import java.util.UUID;
import org.project.tennisscoreboard.dao.MatchDao;
import org.project.tennisscoreboard.dao.PlayerDao;
import org.project.tennisscoreboard.dto.MatchScore;
import org.project.tennisscoreboard.dto.WinnerLoserDTO;
import org.project.tennisscoreboard.exception.MatchNotFoundException;
import org.project.tennisscoreboard.exception.PlayerNotCreatedException;
import org.project.tennisscoreboard.model.CollectionMatches;
import org.project.tennisscoreboard.model.Match;
import org.project.tennisscoreboard.model.Player;
import org.project.tennisscoreboard.model.Score;

public class MatchService {

  private final PlayerDao playerDao = new PlayerDao();
  private final MatchDao matchDao = new MatchDao();
  private final MatchScoreService matchScoreService = new MatchScoreService();

  public WinnerLoserDTO updateMatchScore(UUID winnerId, MatchScore matchScore)
      throws SQLException {
    WinnerLoserDTO winnerLoserDTO = matchScoreService.getWinnerAndLoserScore(
        winnerId,
        matchScore).orElseThrow(
        () -> new MatchNotFoundException("Not found match")
    );
    return matchScoreService.getNextScore(winnerLoserDTO);
  }

  public UUID createCurrentMatch(String pitcherName, String hostName) throws SQLException {
    Player playerPitcher = playerDao.create(pitcherName).orElseThrow(
        () -> new PlayerNotCreatedException("Player not created")
    );
    Player playerHost = playerDao.create(hostName).orElseThrow(
        () -> new PlayerNotCreatedException("Player not created")
    );
    UUID matchId = UUID.randomUUID();
    MatchScore matchScore = new MatchScore(playerPitcher.getId(), playerHost.getId(),
        new Score(), new Score());
    CollectionMatches.matches.put(matchId, matchScore);
    return matchId;
  }

  public Match createFinishedMatch(UUID matchId, MatchScore matchScore, UUID winnerId)
      throws SQLException {
    CollectionMatches.matches.remove(matchId);
    Player firstPlayer = playerDao.findById(matchScore.getPlayerPitcherId()).orElseThrow(
        () -> new PlayerNotCreatedException("Player not found")
    );
    Player secondPlayer = playerDao.findById(matchScore.getPlayerHostId()).orElseThrow(
        () -> new PlayerNotCreatedException("Player not found")
    );
    Player winner = playerDao.findById(winnerId).orElseThrow(
        () -> new PlayerNotCreatedException("Player not found")
    );
    return matchDao.create(firstPlayer, secondPlayer, winner)
        .orElseThrow(
            () -> new MatchNotFoundException("Match not found")
        );
  }
}
