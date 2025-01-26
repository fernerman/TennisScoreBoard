package org.project.tennisscoreboard.dao;

import java.sql.SQLException;
import org.hibernate.Session;
import org.project.tennisscoreboard.model.Match;
import org.project.tennisscoreboard.model.Player;
import org.project.tennisscoreboard.util.SessionFactoryUtil;

public class MatchDao {

  public Match create(Player playerPitcher, Player playerHost) throws SQLException {
    Match match = new Match();
    match.setPitcher(playerPitcher);
    match.setHost(playerHost);
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
      session.persist(match);
    } catch (Exception e) {
      throw new SQLException();
    }
    return match;
  }
}
