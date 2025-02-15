package org.project.tennisscoreboard.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class MatchScore {

  private Player playerPitcher;
  private Player playerHost;
  private Score scorePitcher;
  private Score scoreHost;
}
