<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Entities</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="../static/style_topic.css">
</head>
<body>
<div class="container-fluid">

    <div class="form-container">
        <div class="form-horizontal">
            <div class="form-icon"><i class="fa fa-user"></i></div>
            <h3 class="title">${todoTitle}</h3>

            <div>
                <a href="/secure/topics?todoTitle=${todoTitle}&&title=Тему" class="btn">Тему</a>
            </div>
            <div>
                <a href="/secure/topics?todoTitle=${todoTitle}&&title=Тест" class="btn">Тест</a>
            </div>
            <div>
                <a href="/secure/topics?todoTitle=${todoTitle}&&title=Вопрос" class="btn">Вопрос</a>
            </div>
            <div>
                <a href="/secure/topics?todoTitle=${todoTitle}&&title=Ответ" class="btn">Ответ</a>
            </div>
            <div>
                <a href="/secure/users?todoTitle=${todoTitle}&&title=Пользователя" class="btn">Пользователя</a>
            </div>
            <c:if test="${todoTitle eq 'Удалить'}">
                <div>
                    <a href="/secure/users?todoTitle=${todoTitle}&&title=Историю" class="btn">Историю</a>
                </div>
            </c:if>

            <br>
            <a href="/secure/menu" class="btn btn-default">Выход</a>
        </div>
    </div>
</div>

</body>
</html>
