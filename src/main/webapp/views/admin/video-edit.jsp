<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!-- Edit Video Form -->
<form action="<c:url value='/admin/video/update'></c:url>" method="post" enctype="multipart/form-data">
    <input type="hidden" name="videoId" value="${video.videoId}">

    <label for="videoTitle">Video Title:</label><br>
    <input type="text" id="videoTitle" name="title" value="${video.title}" required><br>

    <label for="viewCount">View Count:</label><br>
    <input type="number" id="viewCount" name="views" value="${video.views}" min="0"><br>

    <label for="category">Category:</label><br>
    <select id="category" name="categoryId" required>
        <c:forEach items="${listCategories}" var="category">
            <option value="${category.categoryId}" <c:if test="${category.categoryId == video.category.categoryId}">selected</c:if>>${category.categoryname}</option>
        </c:forEach>
    </select><br>

    <label for="description">Description:</label><br>
    <textarea id="description" name="description" rows="4" cols="50">${video.description}</textarea><br>

    <label>Status:</label><br>
    <input type="radio" id="active" name="active" value="1" <c:if test="${video.active == 1}">checked</c:if>> Active<br>
    <input type="radio" id="inactive" name="active" value="0" <c:if test="${video.active == 0}">checked</c:if>> Inactive<br>

    <label for="poster">Poster:</label><br>
    <input type="file" id="poster" name="poster" accept="image/*" onchange="previewImage(event)"><br>
    <img id="posterPreview" src="<c:url value='${video.poster}'/>" height="150" width="200" style="display:block;"/><br>

    <input type="submit" value="Update Video">
    <input type="button" value="Cancel" onclick="window.location.href='<c:url value='/admin/videos'></c:url>'">
</form>

<script>
    function previewImage(event) {
        const file = event.target.files[0];
        const reader = new FileReader();
        reader.onload = function(e) {
            const img = document.getElementById('posterPreview');
            img.src = e.target.result;
        };
        reader.readAsDataURL(file);
    }
</script>