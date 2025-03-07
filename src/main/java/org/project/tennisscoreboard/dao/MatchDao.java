package org.project.tennisscoreboard.dao;

import java.sql.SQLException;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.project.tennisscoreboard.model.Match;
import org.project.tennisscoreboard.model.Player;
import org.project.tennisscoreboard.util.SessionFactoryUtil;

public class MatchDao {

  public Optional<Match> create(Player playerPitcher, Player playerHost) throws SQLException {
    Transaction transaction = null;
    Match match = new Match();
    match.setPitcher(playerPitcher);
    match.setHost(playerHost);
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      session.persist(match);
      transaction.commit();
      return Optional.of(match);
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
        throw new SQLException();
      }
    }
    return Optional.empty();
  }
}
