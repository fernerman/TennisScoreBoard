package org.project.tennisscoreboard.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.tennisscoreboard.dao.PlayerDao;
import org.project.tennisscoreboard.service.MatchService;

import java.io.IOException;

@WebServlet("/new-match")
public class CreateNewMatchServlet extends HttpServlet {
    public static final String NAME_PARAMETER = "NAME";
    private final PlayerDao playerDao=new PlayerDao();
    private final MatchService matchService = new MatchService();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String namePitcher = request.getParameter(NAME_PARAMETER);
        String nameHost = request.getParameter(NAME_PARAMETER);
        matchService.createMatch(namePitcher, nameHost);
    }
}
