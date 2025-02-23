<%@ page import="org.project.tennisscoreboard.util.ScoreFormatter" %>
<%@ page import="org.project.tennisscoreboard.model.MatchScore" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Match Score</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@include file="header.jsp" %>
<main>
    <div class="container">
        <h1>Current match</h1>
        <div class="current-match-image"></div>
        <section class="score">
            <table class="table">
                <thead class="result">
                <tr>
                    <th class="table-text">Player</th>
                    <th class="table-text">Sets</th>
                    <th class="table-text">Games</th>
                    <th class="table-text">Points</th>
                </tr>
                </thead>
                <tbody>
                <tr class="player1">
                    <td class="table-text">${requestScope.match.playerPitcher.name}</td>
                    <td class="table-text">${requestScope.match.pitcherScore.set}</td>
                    <td class="table-text">${requestScope.match.pitcherScore.game}</td>
                    <td class="table-text">
                        <%
                            MatchScore match = (MatchScore) request.getAttribute("match");
                        %>
                        <%= ScoreFormatter.getFormattingPoints(
                                match.getPitcherScore(),
                                match.getHostScore()) %>

                    </td>
                    <td class="table-text">
                        <form action="${pageContext.request.contextPath}/match-score" method="post">
                            <input type="hidden" name="uuid" value="${requestScope.matchUuid}"/>
                            <input type="hidden" name="winnerId"
                                   value="${requestScope.match.playerPitcher.id}"/>
                            <button type="submit" class="score-btn">Score</button>
                        </form>
                    </td>
                </tr>
                <tr class="player2">
                    <td class="table-text">${requestScope.match.playerHost.name}</td>
                    <td class="table-text">${requestScope.match.hostScore.set}</td>
                    <td class="table-text">${requestScope.match.hostScore.game}</td>
                    <td class="table-text">
                        <%= ScoreFormatter.getFormattingPoints(
                                match.getHostScore(),
                                match.getPitcherScore()) %>
                    </td>
                    <td class="table-text">
                        <form action="${pageContext.request.contextPath}/match-score" method="post">
                            <input type="hidden" name="uuid" value="${requestScope.matchUuid}"/>
                            <input type="hidden" name="winnerId"
                                   value="${requestScope.match.playerHost.id}"/>
                            <button type="submit" class="score-btn">Score</button>
                        </form>
                    </td>
                </tr>
                </tbody>
            </table>
        </section>
    </div>
</main>
<%@include file="footer.jsp" %>
</body>
</html>
