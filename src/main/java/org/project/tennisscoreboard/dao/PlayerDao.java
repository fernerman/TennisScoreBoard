package org.project.tennisscoreboard.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.project.tennisscoreboard.model.Player;
import org.project.tennisscoreboard.util.SessionFactoryUtil;

import java.util.Optional;

public class PlayerDao {
    public static final String GET_NEW_PLAYER_BY_NAME_HQL = "FROM PLAYERS e WHERE e.name = :name";

    public Player create(String username) {
        Transaction transaction = null;
        Optional<Player> existPlayer = existPlayer(username);
        if (existPlayer.isPresent()) {
            throw new RuntimeException("Player with name " + username + " already exists");
        }
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Player player = new Player();
            player.setName(username);
            session.persist(player);
            transaction.commit();
            return player;
        } catch (Exception e) {
            throw new RuntimeException("Error creating player!", e);
        }
    }

    public Optional<Player> existPlayer(String username) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Player player = session.createQuery(GET_NEW_PLAYER_BY_NAME_HQL, Player.class)
                    .setParameter("NAME", username)
                    .uniqueResult();
            return Optional.ofNullable(player);
        }
    }
}
