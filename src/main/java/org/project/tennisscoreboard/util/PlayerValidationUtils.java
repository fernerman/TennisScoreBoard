package org.project.tennisscoreboard.util;

import org.project.tennisscoreboard.exception.PlayerInvalidNameException;

public class PlayerValidationUtils {

  public static final int PLAYER_NAME_LENGTH = 10;
  public static final String REGULAR_EXPRESSION = "[A-Z][a-zA-Z]*";

  public static void validateNameAndSurnamePlayer(String fullName)
      throws PlayerInvalidNameException {

    if (fullName == null || fullName.isEmpty()) {
      throw new PlayerInvalidNameException("Player name cannot be null or empty.");
    }
    String[] parts = fullName.trim().split("\\s+");

    if (parts.length < 2) {
      throw new PlayerInvalidNameException("Full name must contain at least a name and a surname.");
    }
    String name = parts[0];
    String surname = parts[1];

    if (name.length() > PLAYER_NAME_LENGTH || surname.length() > PLAYER_NAME_LENGTH) {
      throw new PlayerInvalidNameException(
          "Name must not exceed " + PLAYER_NAME_LENGTH + " characters.");
    }
    if (!name.matches(REGULAR_EXPRESSION) || !surname.matches(REGULAR_EXPRESSION)) {
      throw new PlayerInvalidNameException(
          "Name must start with an uppercase letter and contain only English letters.");
    }
  }
}
