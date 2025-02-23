package org.project.tennisscoreboard.service;

import org.hibernate.Session;
import org.project.tennisscoreboard.dao.PlayerDao;
import org.project.tennisscoreboard.model.entity.Player;

public class PlayerService {

  private final PlayerDao playerDao = new PlayerDao();

  public Player getOrCreatePlayer(Session session, String playerName) {
    return playerDao.getPlayerByName(session, playerName)
        .orElseGet(() -> playerDao.create(session, playerName));
  }
}
