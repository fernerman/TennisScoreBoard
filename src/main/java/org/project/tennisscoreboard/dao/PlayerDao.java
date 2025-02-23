package org.project.tennisscoreboard.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.project.tennisscoreboard.model.entity.Player;
import org.project.tennisscoreboard.util.SessionFactoryUtil;

public class PlayerDao {

  private static final String GET_NEW_PLAYER_BY_NAME_HQL = "FROM Player e WHERE e.name = :NAME";

  public Player create(Session session, String username) {
    Player player = new Player();
    player.setName(username);
    session.persist(player);
    return player;
  }


  public Optional<Player> getPlayerByName(Session session, String username) {
    List<Player> players = session.createQuery(GET_NEW_PLAYER_BY_NAME_HQL, Player.class)
        .setParameter("NAME", username)
        .getResultList();
    return players.isEmpty() ? Optional.empty() : Optional.of(players.get(0));
  }

  @SneakyThrows
  public Player findById(UUID id) {
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Player player = session.find(Player.class, id);
      return Optional.ofNullable(player).orElseThrow(() -> new SQLException("Not found player"));
    }
  }
}
