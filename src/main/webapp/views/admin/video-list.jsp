<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<h2>Video List</h2>
<a href="<c:url value='/admin/video/add'></c:url>">Add New Video</a>

<table border="1" width="100%">
    <thead>
        <tr>
            <th>Video ID</th>
            <th>Title</th>
            <th>Views</th>
            <th>Category</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${listVideo}" var="video">
            <tr>
                <td>${video.videoId}</td>
                <td>${video.title}</td>
                <td>${video.views}</td>
                <td>${video.category.categoryname}</td>
                <td><c:if test="${video.active == 1}">Active</c:if><c:if test="${video.active == 0}">Inactive</c:if></td>
                <td>
                    <a href="<c:url value='/admin/video/edit?id=${video.videoId}'/>">Edit</a>
                    <a href="<c:url value='/admin/video/delete?id=${video.videoId}'/>" onclick="return confirm('Are you sure you want to delete this video?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>