package org.project.tennisscoreboard.dao;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;
import org.hibernate.Session;
import org.project.tennisscoreboard.model.Player;
import org.project.tennisscoreboard.util.SessionFactoryUtil;

public class PlayerDao {

  private final String GET_NEW_PLAYER_BY_NAME_HQL = "FROM Player e WHERE e.name = :NAME";

  public Player findOrCreate(Session session, String username) {

    Optional<Player> existPlayer = getPlayerByName(session, username);
    if (existPlayer.isPresent()) {
      return existPlayer.get();
    }
    Player player = new Player();
    player.setName(username);
    session.persist(player);
    return player;
  }

  public Optional<Player> getPlayerByName(Session session, String username) {
    try {
      Player player = session.createQuery(GET_NEW_PLAYER_BY_NAME_HQL, Player.class)
          .setParameter("NAME", username)
          .getSingleResult();
      return Optional.ofNullable(player);
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  public Player findById(UUID id) throws SQLException {
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Player player = session.find(Player.class, id);
      return Optional.ofNullable(player).orElseThrow(() -> new SQLException("Not found player"));
    }
  }
}
