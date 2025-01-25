package org.project.tennisscoreboard.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.project.tennisscoreboard.model.Player;
import org.project.tennisscoreboard.util.SessionFactoryUtil;

import java.util.Optional;
import java.util.UUID;

public class PlayerDao {
    public static final String GET_NEW_PLAYER_BY_NAME_HQL = "FROM Player e WHERE e.name = :NAME";

    public UUID create(String username) {
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
            return player.getId();
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

    public Player findPlayerById(UUID playerPitcherId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            // Используем get() для получения объекта по идентификатору
            return session.get(Player.class, playerPitcherId); // Вернет null, если не найден
        }
    }
}
