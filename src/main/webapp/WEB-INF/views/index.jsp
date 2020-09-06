<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            <%@ include file="chunks/menu.jsp" %>
        </div>
    </div>
    <div class="row">
        <div class="col col-sm-12 title">
            <h4>The job4j forum</h4>
        </div>
    </div>
    <div class="row pt-3">
        <div class="col col-sm-12">
            <c:if test="${can_add_post}">
                <a class="btn btn-primary create-post" href="<c:url value="/post/create" />">New post</a>
            </c:if>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Post</th>
                    <th scope="col">By</th>
                    <th scope="col">Created</th>
                    <th scope="col">Updated</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${posts}" var="post">
                    <tr>
                        <td><a href="<c:url value="/post/${post.id}" /> ">${post.title}</a></td>
                        <td><a href="<c:url value="/user/${post.author.id}" /> ">${post.author.name}</a></td>
                        <td><fmt:formatDate value="${post.created.time}" pattern="dd.MM.Y HH:mm:ss" /></td>
                        <td><fmt:formatDate value="${post.changed.time}" pattern="dd.MM.Y HH:mm:ss" /></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
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