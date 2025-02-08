package org.project.tennisscoreboard.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.project.tennisscoreboard.model.Score;

@Getter
@Setter
@AllArgsConstructor
public class MatchScore {

  private UUID playerPitcherId;
  private UUID playerHostId;
  private Score scorePitcher;
  private Score scoreHost;
}
