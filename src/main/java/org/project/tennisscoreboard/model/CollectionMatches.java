package org.project.tennisscoreboard.model;

import java.util.HashMap;
import java.util.UUID;
import org.project.tennisscoreboard.dto.MatchScore;

public class CollectionMatches {

  public static HashMap<UUID, MatchScore> matches = new HashMap<>();
}
