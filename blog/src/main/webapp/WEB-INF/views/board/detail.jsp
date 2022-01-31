<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<div class="form-group">
		글 번호 : <span id="id">${board.id }</span> <br> 작성자 : <span id="username">${board.user.username}</span>
	</div>
	<hr>
	<div class="form-group">
		<h3>${board.title }</h3>
	</div>
	<hr>
	<div class="form-group">
		<p>${board.content }</p>
	</div>
	<div class="d-flex justify-content-end">
		<button class="btn btn-secondary" onclick="history.back()">뒤로가기</button>
		<c:if test="${board.user.id == principal.user.id }">
			<button class="btn btn-info" onclick="location.href='/board/${board.id}/updateForm'">수정</button>
			<button id="btn-delete" class="btn btn-danger">삭제</button>
		</c:if>
	</div>
	<hr>
	<div class="card">
		<form>
			<input type="hidden" id="boardId" value="${board.id }">
				<div class="card-body">
					<textarea id="reply-content" class="form-control" rows="2"></textarea>
				</div>
				<div class="card-footer">
					<button type="button" id="btn-reply-save" class="btn btn-primary">등록</button>
				</div>
		</form>
	</div>
	<br>
	<br>
	<div class="card">
		<div class="card-header">댓글 리스트</div>
		<ul id="reply--box" class="list-group">
			<c:forEach var="reply" items="${board.replys }" varStatus="status">
				<li id="reply--${status.count }" class="list-group-item d-flex justify-content-between">
					<div>${reply.content}</div>
					<div class="d-flex">
						<div class="font-italic">작성자: ${reply.user.username} &nbsp;&nbsp;</div>
						<button class="badge badge-dark">삭제</button>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>

<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>


