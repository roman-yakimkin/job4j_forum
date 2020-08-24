<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ul class="nav">
        <li class="nav-item active"><a href="<c:url value="/" />">Forum</a></li>
        <li class="nav-item"><a href="<c:url value="/post/create" />">New post</a></li>
        <li class="nav-item"><a href="<c:url value="/user/${user.id}" />">${user.name}</a></li>
        <li class="nav-item"><a href="<c:url value="/user/logout" />">Logout</a></li>
        <li class="nav-item"><a href="<c:url value="/user/login" />">Login</a></li>
        <li class="nav-item"><a href="<c:url value="/user/register" />">Register</a></li>
    </ul>
