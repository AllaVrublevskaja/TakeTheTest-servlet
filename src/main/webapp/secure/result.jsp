<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Topics</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/dataTables.bootstrap5.min.css" />
    <link rel="stylesheet" href="../static/style.css">
</head>
<body>
<div class="container-fluid">
    <div class = "row justify-content-center">
    <div class="col-12 col-md-10 col-lg-8">
        <div class="form-container  mt-4 mb-4">
            <div class="form-icon"><i class="fa fa-user"></i></div>
            <h3 class="title">Результат тестирования ${user.name}</h3>
            <div class="form-horizontal">
                <div class="table-responsive">
                <div style="text-align: center; color: darkgreen; ">
                    <c:if test="${topic.name != null}">
                        <h6>Выбрана тема: ${topic.name} </h6>
                    </c:if>
                    <c:if test="${test.name != null}">
                        <h6>Выбран тест: ${test.name} </h6>
                    </c:if>
                    <c:if test="${result.date != null}">
                        <h6>Дата прохождения: ${result.date}</h6>
                    </c:if>
                    <hr>
                </div>
                <table  id="resultTable" class="table table-bordered table-striped text-center">
                    <thead class="table-light">
                    <tr>
                        <th>Вопрос</th>
                        <th>Правильный ответ</th>
                    </tr>
                    </thead>
                    <c:forEach var="question" items="${questions}">
                        <tr>
                            <td>
                                <c:if test="${question.name != null}">
                                    <h6>${question.name}</h6>
                                </c:if>
                            </td>
                            <td>
                                <c:forEach var="selected" items="${selectedAnswers}">
                                    <c:if test="${question.id == selected.questionId}">
                                            <h6>${selected.selectedAnswerName}</h6>
                                    </c:if>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <hr>
                <a href="/secure/menu" class="btn btn-default">Выход</a>
                </div>
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
    $(document).ready(function() {
        $('#resultTable').DataTable({
            language: {
                url: '//cdn.datatables.net/plug-ins/1.13.4/i18n/Русский.json'
            },
            pageLength: 4,
            lengthMenu: [4, 5, 7, 10]
        });
    });
</script>
</body>
</html>
