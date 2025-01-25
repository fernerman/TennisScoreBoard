package org.project.tennisscoreboard.model;

import lombok.Getter;

@Getter
public class Score {
    private int point;
    private int game;
    private int set;

    public Score(int point, int game, int set) {
        this.point = point;
        this.game = game;
        this.set = set;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setGame(int game) {
        this.game = game;
    }

    public void setSet(int set) {
        this.set = set;
    }
}