<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="static/style.css">
</head>
<body>
<div class="container-fluid">
    <div class="row justify-content-center">
        <div class="col-md-4 offset-md-4">
            <div class="form-container">
                <div class="form-icon"><i class="fa fa-user"></i></div>
                <h3 class="title">Проверь себя!</h3>
                <h4 class="title">Пройди тест</h4>
                <br>
                <br>
                <div class="form-horizontal">
                    <a href="/login" class="btn">Начать</a>
                    <div>
                    </div>
                    <a href="/registration" class="btn">Регистрация</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>