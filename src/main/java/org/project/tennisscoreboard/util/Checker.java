package org.project.tennisscoreboard.util;

import org.project.tennisscoreboard.exception.PlayerNotValidNameException;

public class Checker {

  public static final int PLAYER_NAME_LENGTH = 10;

  public static void validateNameAndSurnamePlayer(String fullName)
      throws PlayerNotValidNameException {

    if (fullName == null || fullName.isEmpty()) {
      throw new PlayerNotValidNameException("Full name cannot be null or empty.");
    }
    String[] parts = fullName.trim().split("\\s+");

    if (parts.length < 2) {
      throw new IllegalArgumentException("Full name must contain at least a name and a surname.");
    }
    String name = parts[0];
    String surname = parts[1];

    // Validate name length
    if (name.length() > PLAYER_NAME_LENGTH || surname.length() > PLAYER_NAME_LENGTH) {
      throw new PlayerNotValidNameException(
          "Name must not exceed " + PLAYER_NAME_LENGTH + " characters.");
    }
    if (!name.matches("[A-Z][a-zA-Z]*") || !surname.matches("[A-Z][a-zA-Z]*")) {
      throw new PlayerNotValidNameException(
          "Name must start with an uppercase letter and contain only English letters.");
    }
  }
}
