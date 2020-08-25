<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html >
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css">

    <title>The job4j forum</title>
</head>
<body>
<div class="container pt-3">
    <div class="row">
        <div class="col col-sm-12">
            <%@ include file="../chunks/menu.jsp" %>
        </div>
    </div>
    <div class="row pt-3">
        <div class="col-sm-12">
            <h4>The job4j forum</h4>
        </div>
    </div>
    <div class="row pt-3">
        <div class="col-sm-12 post">
            <h4>${item.name}</h4>
            <div class="actions">
<%--                <a href="<c:url value="/post/${item.id}/edit"/>">edit</a>--%>
            </div>
        </div>
    </div>
    <div class="row pt-3">
        <div class="col col-sm-12 col-md-6">
            <h5>Latest posts</h5>
            <c:if test="${posts.size() > 0}">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">Post</th>
                        <th scope="col">Created/Updated</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${posts}" var="post">
                        <tr>
                            <td><a href="<c:url value="/post/${post.id}" /> ">${post.title}</a></td>
                            <td><fmt:formatDate value="${post.changed.time}" pattern="dd.MM.Y HH:mm:ss" /></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${posts.size() == 0}">
                No posts
            </c:if>
        </div>
        <div class="col col-sm-12 col-md-6">
            <h5>Latest comments</h5>
            <c:if test="${comments.size() > 0}">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">Comments</th>
                        <th scope="col">Created/Updated</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${comments}" var="comment">
                        <tr>
                            <td><a href="<c:url value="/comment/${comment.id}" /> ">${fn:substring(comment.body, 0, 25)}...</a></td>
                            <td><fmt:formatDate value="${comment.changed.time}" pattern="dd.MM.Y HH:mm:ss" /></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${comments.size() == 0}">
                No comments
            </c:if>
        </div>
    </div>
</div>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>