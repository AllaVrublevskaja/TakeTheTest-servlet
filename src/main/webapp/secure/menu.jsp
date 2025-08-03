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
            <h3 class="title">Меню</h3>
            <div style="text-align: center; color: darkgreen; ">
                <c:if test="${topic.name != null}">
                    <h6> Выбрана тема: ${topic.name} </h6>
                </c:if>
                <c:if test="${test.name != null}">
                    <h6> Выбран тест: ${test.name} </h6>
                </c:if>
            </div>
                <c:if test="${testTimeFinish != null and topic.name == null}">
                    <h5 style="color: darkmagenta"> ${testTimeFinish} </h5>
                </c:if>

            <div>
                <a href="/secure/topics" class="btn">Просмотр доступных тем</a>
            </div>
            <div>
                <a href="/secure/tests?id=${topic.id}" class="btn">Просмотр доступных тестов по теме</a>
            </div>
            <div>
                <a href="/secure/questions?id=${test.id}" class="btn">Пройти выбранный тест</a>
            </div>
            <div>
                <a href="/secure/results?id=${result.id}" class="btn">Просмотр результатов пройденного теста</a>
            </div>
            <div>
                <a href="/secure/histories?id=${user.id}" class="btn">Просмотр истории своих тестирований</a>
            </div>
            <c:if test="${admin}">
                <div>
                    <a href="/secure/menu_admin?todoTitle=Создать" class="btn">Создать</a>
                </div>
                <div>
                    <a href="/secure/menu_admin?todoTitle=Редактировать" class="btn">Редактировать</a>
                </div>
                <div>
                    <a href="/secure/menu_admin?todoTitle=Удалить" class="btn">Удалить</a>
                </div>
                <div>
                    <a href="/secure/histories" class="btn">Просмотр статистики по тестам</a>
                </div>
            </c:if>
            <br>
            <a href="/logout" class="btn btn-default">Выход</a>
        </div>
    </div>
</div>
</body>
</html>
