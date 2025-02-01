package org.project.tennisscoreboard.util;

import org.project.tennisscoreboard.exception.PlayerInvalidNameException;

public class PlayerValidationUtils {

  public static final int PLAYER_NAME_LENGTH = 10;
  public static final String NAME_SURNAME_REGEX = "[A-Z][a-zA-Z]*";

  public static void validateName(String fullName)
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
          String.format("Name must not exceed %d characters.", PLAYER_NAME_LENGTH)
      );
    }
    if (!name.matches(NAME_SURNAME_REGEX) || !surname.matches(NAME_SURNAME_REGEX)) {
      throw new PlayerInvalidNameException(
          "Name must start with an uppercase letter and contain only English letters.");
    }
  }
}
