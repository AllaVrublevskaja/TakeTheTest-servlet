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
        <form class="form-horizontal" action="${endpoint}" method="post">
            <div class="form-icon"><i class="fa fa-user"></i></div>
            <h3 class="title">${heading}</h3>
            <hr>

            <table class="table table-hover text-center">
                <c:forEach var="entity" items="${entities}">
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${entity.id eq topicColorId}">
                                    <button type="submit" class="btn" style="color: ${colorTopic}"
                                            name="entityId" value="${entity.id}"> ${entity.name}
                                    </button>
                                </c:when>
                                <c:when test="${entity.id eq testColorId}">
                                    <button type="submit" class="btn" style="color: ${colorTest}"
                                            name="entityId" value="${entity.id}"> ${entity.name}
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="submit" class="btn" name="entityId"
                                            value="${entity.id}"> ${entity.name}
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <c:if test="${todoTitle eq 'Редактировать'}">
                            <c:if test="${thisTest==null and createTopic}">
                                <td>
                                    <a href="/secure/create_change.jsp?change=change&changeName=${entity.name}&id=${entity.id}"
                                       class="btn btn-default">Изменить</a>
                                </td>
                            </c:if>
                            <c:if test="${thisTest==null and createUser}">
                                <td>
                                    <a href="/secure/create_change.jsp?change=change&changeName=${entity.name}&id=${entity.id}&auser=auser"
                                       class="btn btn-default">Изменить</a>
                                </td>
                            </c:if>
                            <c:if test="${createTest and thisTest}">
                                <td>
                                    <a href="/secure/create_change.jsp?change=change&changeName=${entity.name}&id=${entity.id}"
                                       class="btn btn-default">Изменить</a>
                                </td>
                            </c:if>
                        </c:if>
                        <c:if test="${todoTitle eq 'Удалить'}">
                            <c:if test="${thisTest==null and (createTopic or createUser)}">
                                <td>
                                    <form action="${endpoint}" method="post" style="display:inline;">
                                        <input type="hidden" name="_method" value="DELETE"/>
                                        <button type="submit" name="removeId" class="btn btn-default"
                                                value="${entity.id}">Удалить
                                        </button>
                                    </form>
                                </td>
                            </c:if>
                            <c:if test="${createTest and thisTest}">
                                <td>
                                    <form action="${endpoint}" method="post" style="display:inline;">
                                        <input type="hidden" name="_method" value="DELETE"/>
                                        <button type="submit" name="removeId" class="btn btn-default"
                                                value="${entity.id}">Удалить
                                        </button>
                                    </form>
                                </td>
                            </c:if>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
            <hr>
            <c:if test="${(todoTitle eq 'Создать') and (createTopic or createTest or createQuestion
            or createAnswer or createUser)}">
                <c:choose>
                    <c:when test="${createTopic and thisTest==null}">
                        <a href="/secure/create_change.jsp" class="btn btn-default">${todo}</a>
                    </c:when>
                    <c:when test="${createTest and thisTest}">
                        <a href="/secure/create_change.jsp" class="btn btn-default">${todo}</a>
                    </c:when>
                    <c:when test="${createUser and thisTest==null}">
                        <a href="/secure/create_change.jsp?auser=auser" class="btn btn-default">${todo}</a>
                    </c:when>
                </c:choose>
            </c:if>
            <c:if test="${todoTitle==null}">
                <a href="/secure/menu" class="btn btn-default">Выход</a>
            </c:if>
            <c:if test="${todoTitle!=null}">
                <c:choose>
                    <c:when test="${todoTitle eq 'Удалить' and title eq 'Историю'}">
                        <form action="${endpoint}" method="post" style="display:inline;">
                            <input type="hidden" name="_method" value="DELETE"/>
                            <button type="submit" name="removeId" class="btn btn-default">
                                Удалить всю историю
                            </button>
                            <a href="/secure/menu_admin" class="btn btn-default">Выход</a>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <a href="/secure/menu_admin" class="btn btn-default">Выход</a>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </form>
    </div>
</div>
</body>
</html>
