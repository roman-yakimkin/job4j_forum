<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <div class="row pt-3">
        <div class="col-sm-12">
            <h4>The job4j forum</h4>
        </div>
    </div>
    <div class="row pt-3">
        <div class="col-sm-12 post">
            <h4>${item.title}</h4>
            <div class="actions">
                <a href="<c:url value="/post/${item.id}/edit"/>">edit</a>
                <a href="<c:url value="/post/${item.id}/delete"/>">delete</a>
            </div>
            <div class="body">
                ${item.body}
            </div>
            <div class="author">
                posted by <a href="<c:url value="/user/${item.author.id}"/>">${item.author.name}</a>, last updated <span class="created"><fmt:formatDate value="${item.changed.time}" pattern="dd.MM.Y HH:mm:ss" /></span>
            </div>
        </div>
    </div>
    <c:if test="${comments.size() > 0}">
    <div class="row pt-3">
        <div class="col-sm-12 comments">
            <h5>Comments</h5>
            <c:forEach var="comment" items="${comments}">
            <div class="comment" style="padding-left: ${20 * comment.depth}px">
                <div class="author">by <a href="<c:url value="/user/${comment.author.id}"/>">${comment.author.name}</a> on <span class="created"><fmt:formatDate value="${comment.changed.time}" pattern="dd.MM.Y HH:mm:ss" /></div>
                <div class="body">${comment.body}</div>
                <div class="actions" >
                    <a href="<c:url value="/comment/reply/${item.id}/${comment.id}"/>">reply</a>
                    <a href="<c:url value="/comment/${comment.id}/edit"/>">edit</a>
                    <a href="<c:url value="/comment/${comment.id}/delete"/>">delete</a>
                </div>
            </div>
            </c:forEach>
        </div>
    </div>
    </c:if>
    <div class="row>
        <div class="col-sm-12">
            <h5>Add new comment</h5>
            <form class="form-main" action="/comment/save" method="post" style="width: 100%">
                <input type="hidden" name="id" value="0" />
                <input type="hidden" name="postId" value="${item.id}" />
                <input type="hidden" name="parentId" value="0" />
                <div class="form-group">
                    <label>Comment</label>
                    <textarea class="form-control" name="body" rows="5"></textarea>
                </div>
                <button type="submit" class="btn btn-primary">Add comment</button>
            </form>
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