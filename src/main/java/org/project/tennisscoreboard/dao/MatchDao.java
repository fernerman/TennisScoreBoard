package org.project.tennisscoreboard.dao;

import java.sql.SQLException;
import java.util.List;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.project.tennisscoreboard.model.entity.Match;
import org.project.tennisscoreboard.model.entity.Player;
import org.project.tennisscoreboard.util.SessionFactoryUtil;

public class MatchDao {

  @SneakyThrows
  public void create(Player playerPitcher, Player playerHost, Player winner) {
    Transaction transaction = null;
    Match match = new Match();
    match.setPitcher(playerPitcher);
    match.setHost(playerHost);
    match.setWinner(winner);
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      session.persist(match);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      throw new SQLException("Match creation failed", e);
    }
  }

  @SneakyThrows
  public List<Match> findAll() {
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      Query<Match> query = session.createQuery("FROM Match", Match.class);
      return query.getResultList();
    } catch (Exception e) {
      throw new SQLException();
    }
  }
}
