package org.project.tennisscoreboard.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateNewMatchDTO {

  private UUID playerPitcherId;
  private UUID playerHostId;
  private int currentScore;
}
