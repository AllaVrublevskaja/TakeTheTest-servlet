<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Topics</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/dataTables.bootstrap5.min.css"/>
    <link rel="stylesheet" href="../static/style.css">
</head>
<body>
<div class="container-fluid">
    <div class="row justify-content-center">
        <div class="col-12 col-md-10 col-lg-8">
            <div class="form-container mt-4 mb-4">
                <div class="form-icon"><i class="fa fa-user"></i></div>
                <h3 class="title">История тестирования</h3>
                <div class="form-horizontal">
                    <div class="table-responsive">
                        <table id="historyTable" class="table table-bordered table-striped text-center">
                            <thead class="table-light">
                            <tr>
                                <th>Имя</th>
                                <th>Тема</th>
                                <th>Тест</th>
                                <th>Дата тестирования</th>
                                <th>Вопросов</th>
                                <th>Правильных ответов</th>
                                <th>Выбранных ответов</th>
                            </tr>
                            </thead>
                            <c:forEach var="history" items="${histories}">
                                <tr>
                                    <td>${history.userName}</td>
                                    <td>${history.topicName}</td>
                                    <td>${history.testName}</td>
                                    <td>${history.date}</td>
                                    <td>${history.countQuestions}</td>
                                    <td>${history.countTrueAnswers}</td>
                                    <td>${history.countSelectedAnswers}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                    <hr>
                    <a href="/secure/menu" class="btn btn-default">Выход</a>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- jQuery (нужен для DataTables) -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- Bootstrap 5 JS Bundle (Popper.js внутри) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"></script>

<!-- DataTables JS -->
<script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.13.4/js/dataTables.bootstrap5.min.js"></script>

<!-- Инициализация DataTables с русской локализацией -->
<script>
    $(document).ready(function () {
        $('#historyTable').DataTable({
            language: {
                url: '//cdn.datatables.net/plug-ins/1.13.4/i18n/Русский.json'
            },
            pageLength: 5,
            lengthMenu: [5, 10, 25, 50]
        });
    });
</script>
</body>
</html>
