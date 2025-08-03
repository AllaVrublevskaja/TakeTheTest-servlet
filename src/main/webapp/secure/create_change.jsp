<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Topics</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="../static/style.css">
</head>
<body>

<c:set var="change" value="${pageContext.request.getParameter('change')}"/>
<c:set var="changeId" value="${pageContext.request.getParameter('id')}"/>
<c:set var="changeName" value="${pageContext.request.getParameter('changeName')}"/>
<c:set var="answer" value="${pageContext.request.getParameter('answer')}"/>
<c:set var="auser" value="${pageContext.request.getParameter('auser')}"/>
<div class="container-fluid">
    <div class="col-md-4 offset-md-4">
        <div class="form-container">
            <div class="form-icon"><i class="fa fa-user"></i></div>
            <c:if test="${change eq 'change'}">
                <c:set var="heading" value="${headingChange}"/>
                <c:set var="todo" value="${todoChange}"/>
                <c:if test="${answer eq 'answer'}">
                    <c:set var="heading" value="Изменить"/>
                    <c:set var="todo" value="Изменить ответ"/>
                    <c:set var="endpoint" value="/secure/answers"/>
                </c:if>
                <c:if test="${auser eq 'auser'}">
                    <c:set var="heading" value="Изменить"/>
                    <c:set var="todo" value="Изменить пользователя"/>
                    <c:set var="endpoint" value="/secure/users"/>
                </c:if>
            </c:if>
            <c:if test="${answer eq 'answer' and change != 'change'}">
                <c:set var="heading" value="Ответ"/>
                <c:set var="todo" value="Добавить ответ"/>
                <c:set var="endpoint" value="/secure/answers"/>
            </c:if>
            <c:if test="${auser eq 'auser' and change != 'change'}">
                <c:set var="heading" value="Пользователь"/>
                <c:set var="todo" value="Добавить пользователя"/>
                <c:set var="endpoint" value="/secure/users"/>
            </c:if>
            <h3 class="title">${heading}</h3>

            <form class="form-horizontal" action="${endpoint}" method="post">
                <div class="form-group">
                    <c:if test="${change=='change'}">
                        <lable>Было:</lable>
                        <input class="form-control" type="text" placeholder="${changeName}" readonly>
                        <label>Стало:</label>
                    </c:if>
                    <c:if test="${change!='change'}">
                        <label>${heading}</label>
                    </c:if>
                    <input type="hidden" name="changeName" value="${changeName}">
                    <input type="hidden" name="changeId" value="${changeId}">
                    <input class="form-control" type="text" name="name" placeholder="${changeName}">
                    <c:if test="${answer eq 'answer'}">
                        <label>Правильный ответ</label>
                        <input type="checkbox" name="isCorrect" value="true">
                    </c:if>
                    <c:if test="${auser eq 'auser'}">
                        <label>пароль</label>
                    <input class="form-control" type="password" name="password">
                    <c:if test="${auser eq 'auser'}">
                        <label>Администратор</label>
                        <input type="checkbox" id="red" name="admin" value="true">
                    </c:if>
                    </c:if>
                </div>

                <button type="submit" class="btn btn-default">${todo}</button>

            </form>
        </div>
    </div>
</div>
</body>
</html>
