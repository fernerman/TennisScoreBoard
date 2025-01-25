package org.project.tennisscoreboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CreateNewMatchDTO {
    private UUID playerPitcherId;
    private UUID playerHostId;
    private int currentScore;
}
