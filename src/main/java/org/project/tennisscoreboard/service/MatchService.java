package org.project.tennisscoreboard.service;

import org.project.tennisscoreboard.dao.PlayerDao;
import org.project.tennisscoreboard.model.Player;

public class MatchService {
    private final PlayerDao playerDao=new PlayerDao();
    public void createMatch(String playerName1,String playerName2){
        Player player1=playerDao.create(playerName1);
        Player player2=playerDao.create(playerName2);

    }
}
