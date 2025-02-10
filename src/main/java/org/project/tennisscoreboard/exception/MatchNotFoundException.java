package org.project.tennisscoreboard.exception;

public class MatchNotFoundException extends RuntimeException {

  public MatchNotFoundException(String message) {
    super(message);
  }
}
