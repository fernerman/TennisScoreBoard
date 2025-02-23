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
                </tr>
                </thead>
                <tbody>
                <tr class="player1">
                    <td class="table-text">${requestScope.match.playerPitcher.name}</td>
                    <td class="table-text">${requestScope.match.pitcherScore.set}</td>
                </tr>
                <tr class="player2">
                    <td class="table-text">${requestScope.match.playerHost.name}</td>
                    <td class="table-text">${requestScope.match.hostScore.set}</td>
                </tr>
                </tbody>
            </table>
        </section>
    </div>
</main>
<%@include file="footer.jsp" %>
</body>
</html>
