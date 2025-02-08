package org.project.tennisscoreboard.dao;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.project.tennisscoreboard.model.Player;
import org.project.tennisscoreboard.util.SessionFactoryUtil;

public class PlayerDao {

  private final String GET_NEW_PLAYER_BY_NAME_HQL = "FROM Player e WHERE e.name = :NAME";

  public Optional<Player> create(String username) throws SQLException {
    Transaction transaction = null;
    Optional<Player> existPlayer = getPlayerByName(username);
    if (existPlayer.isPresent()) {
      return existPlayer;
    }

    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      Player player = new Player();
      player.setName(username);
      session.persist(player);
      transaction.commit();
      return Optional.of(player);
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
        throw new SQLException();
      }
    }
    return Optional.empty();
  }

  public Optional<Player> getPlayerByName(String username) {
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Player player = session.createQuery(GET_NEW_PLAYER_BY_NAME_HQL, Player.class)
          .setParameter("NAME", username)
          .getSingleResult();
      return Optional.ofNullable(player);
    }
  }

  public Optional<Player> findById(UUID id) {
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Player player = session.find(Player.class, id);
      return Optional.ofNullable(player);
    }
  }
}
