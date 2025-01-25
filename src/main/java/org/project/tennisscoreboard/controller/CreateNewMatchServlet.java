package org.project.tennisscoreboard.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.tennisscoreboard.service.MatchService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/new-match")
public class CreateNewMatchServlet extends HttpServlet {
    public static final String NAME_PITCHER_PARAMETER = "name";
    public static final String NAME_HOST_PARAMETER = "name2";
    private final MatchService matchService = new MatchService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String namePitcher = request.getParameter(NAME_PITCHER_PARAMETER);
        String nameHost = request.getParameter(NAME_HOST_PARAMETER);
        UUID matchId = matchService.createMatch(namePitcher, nameHost);
        if (matchId != null) {
            response.sendRedirect("/match-score?uuid=" + matchId);
        }
    }
}
