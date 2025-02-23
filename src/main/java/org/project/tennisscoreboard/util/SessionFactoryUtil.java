package org.project.tennisscoreboard.util;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryUtil {

  @Getter
  private static final SessionFactory sessionFactory;

  static {
    try {
      Class.forName("org.h2.Driver");
      sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("SessionFactory initialization failed: " + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }
}
