<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Question</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="../static/style_topic.css">
</head>
<body>

<div class="container container-fluid">
    <div class="form-container">
        <div class="form-horizontal">
            <div class="form-icon"><i class="fa fa-user"></i></div>
            <h3 class="title">${heading} ${index+1}</h3>
            <c:if test="${questionsSize >0}">
                <table class="table text-center">
                    <tr>
                        <td style="color: navy; font-size: 25px; font-weight: 700; text-align: center; border: none">
                                ${question.name}
                        </td>
                        <c:if test="${todoTitle eq 'Редактировать' and createQuestion}">
                            <td style="border: none">
                                <a href="/secure/create_change.jsp?change=change&changeName=${question.name}&id=${question.id}"
                                   class="btn btn-default">Изменить</a>
                            </td>
                        </c:if>
                        <c:if test="${todoTitle eq 'Удалить' and createQuestion}">
                            <td style="border: none">
                                <form action="${endpoint}" method="post" style="display:inline;">
                                    <input type="hidden" name="_method" value="DELETE"/>
                                    <button type="submit" name="removeId" class="btn btn-default"
                                            value="${question.id}">Удалить
                                    </button>
                                </form>
                            </td>
                        </c:if>
                    </tr>
                </table>
                <hr>
                <c:if test="${check eq 'check'}">
                    <table class="table table-bordered table-hover text-center">
                        <tr>
                            <td style="color: white; font-size: 20px; font-weight: 500;
                    text-align: center; background-color: darkseagreen; border: none">
                                    ${answerToReturn}
                            </td>
                        </tr>
                    </table>
                </c:if>
                <c:if test="${check != 'check'}">
                    <div style="background-color: darkseagreen;">
                        <h3>Выбери правильный ответ</h3>
                        <br>
                        <div>
                            <table class="table text-center">
                                <c:forEach var="answer" items="${answers}">
                                    <c:set var="iscorrect" value=""/>
                                    <c:if test="${todoTitle != null}">
                                        <c:set var="iscorrect" value="${answer.correct}"/>
                                    </c:if>
                                    <tr>
                                        <td style="border: none">
                                            <form class="form-horizontal" action="/secure/answers" method="post">
                                                <div style="display: flex;">
                                                <c:if test="${answer.answer eq true}">
                                                    <button type="submit" class="btn" name="answerId"
                                                            style="color: ${color}"
                                                            value="${answer.id}">${answer.name}</button>
                                                </c:if>
                                                <c:if test="${answer.answer eq false}">
                                                    <button type="submit" class="btn me-2" name="answerId"
                                                            value="${answer.id}">${answer.name}
                                                    </button>
                                                </c:if>
                                                    <p style="color: navy; font-weight: bold; font-size: 18px;">
                                                            ${iscorrect}
                                                    </p>
                                                </div>
                                            </form>
                                        </td>
                                        <c:if test="${todoTitle eq 'Редактировать' and createAnswer}">
                                            <td style="border: none">
                                                <a href="/secure/create_change.jsp?answer=answer&change=change&changeName=${answer.name}&id=${answer.id}"
                                                   class="btn btn-default">Изменить</a>
                                            </td>
                                        </c:if>
                                        <c:if test="${todoTitle eq 'Удалить' and createAnswer }">
                                            <td style="border: none">
                                                <form action="/secure/answers" method="post"
                                                      style="display:inline;">
                                                    <input type="hidden" name="_method" value="DELETE"/>
                                                    <button type="submit" name="removeId" class="btn btn-default"
                                                            value="${answer.id}">Удалить
                                                    </button>
                                                </form>
                                            </td>
                                        </c:if>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <br>
                        <c:if test="${createAnswer and todoTitle eq 'Создать'}">
                            <div>
                                <a href="/secure/create_change.jsp?answer=answer" class="btn btn-default">Добавить
                                    ответ</a>
                            </div>
                        </c:if>
                        <c:if test="${answers.size() > 0 and !createAnswer and todoTitle == null}">
                            <div>
                                <form action="${endpoint}" method="post" style="display:inline;">
                                    <button type="submit" name="check" class="btn btn-default"
                                            value="check">Проверить
                                    </button>
                                </form>
                            </div>
                        </c:if>

                    </div>
                </c:if>
                <hr>
            </c:if>

            <c:if test="${(todoTitle eq 'Создать') and createQuestion and !createAnswer}">
                <a href="/secure/create_change.jsp" class="btn btn-default">${todo}</a>
            </c:if>

            <c:if test="${index<questionsSize-1}">
                <form action="${endpoint}" method="post">
                    <button type="submit" name="next" class="btn btn-default"
                            value="next">Следующий вопрос
                    </button>
                </form>
            </c:if>

            <form action="${endpoint}" method="post">
                <button type="submit" name="quit" class="btn btn-default"
                        value="quit">Выход
                </button>
            </form>
        </div>
    </div>
</div>

</body>
</html>
