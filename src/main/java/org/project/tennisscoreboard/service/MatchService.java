package org.project.tennisscoreboard.service;

import org.project.tennisscoreboard.dao.MatchDao;
import org.project.tennisscoreboard.dao.PlayerDao;
import org.project.tennisscoreboard.dto.CreateNewMatchDTO;
import org.project.tennisscoreboard.model.CollectionMatches;

import java.util.UUID;

public class MatchService {
    private final PlayerDao playerDao = new PlayerDao();
    private final MatchDao matchDao = new MatchDao();

    public UUID createMatch(String pitcherName, String hostName) {
        UUID playerPitcherId = playerDao.create(pitcherName);
        UUID playerHostId = playerDao.create(hostName);
        CreateNewMatchDTO createNewMatchDTO = new CreateNewMatchDTO(
                playerPitcherId,
                playerHostId,
                0
        );
        UUID uuidMatch = matchDao.create(createNewMatchDTO);
        CollectionMatches.matches.put(
                uuidMatch,
                createNewMatchDTO.getCurrentScore()
        );
        return uuidMatch;
    }
}
