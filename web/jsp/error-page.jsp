<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Ошибка 404 - Страница не найдена</title>
    <style>
      body {
        font-family: Arial, sans-serif;
        text-align: center;
        padding: 50px;
        background-color: #f8f9fa;
      }

      .container {
        max-width: 500px;
        margin: auto;
        padding: 20px;
        background: white;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      }

      h1 {
        color: #dc3545;
      }

      p {
        font-size: 18px;
      }

      a {
        text-decoration: none;
        color: #007bff;
      }

      a:hover {
        text-decoration: underline;
      }
    </style>
</head>
<body>
<div class="container">
    <h1>Ошибка 404</h1>
    <p>К сожалению, запрашиваемая страница не найдена.</p>
    <p>Вы можете вернуться на <a href="<%= request.getContextPath() %>/">главную страницу</a>.</p>
</div>
</body>
</html>
