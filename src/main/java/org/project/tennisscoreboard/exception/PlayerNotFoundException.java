package org.project.tennisscoreboard.exception;

public class PlayerNotFoundException extends RuntimeException {

  public PlayerNotFoundException(String message) {
    super(message);
  }
}
