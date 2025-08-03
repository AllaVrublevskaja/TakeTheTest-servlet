<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registration</title>
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
                <h3 class="title">Login</h3>
                <c:if test="${errorMessage != null}">
                    <h5 style="color: darkmagenta"> ${errorMessage} </h5>
                </c:if>
                <form class="form-horizontal" action="/registration" method="post">
                    <div class="form-group">
                        <label>login</label>
                        <input class="form-control" type="text" name="login" placeholder="login">
                    </div>
                    <div class="form-group">
                        <label>password</label>
                        <input class="form-control" type="password" name="password" placeholder="password">
                    </div>
                    <button type="submit" class="btn btn-default">Registration</button>
                    <a href="/logout" class="btn btn-default">Logout</a>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>