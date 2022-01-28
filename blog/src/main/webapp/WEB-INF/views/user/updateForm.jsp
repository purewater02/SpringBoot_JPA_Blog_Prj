<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form class="was-validated">
		<input type="hidden" value="${principal.user.id }" id="id">
		<div class="form-group">
			<label for="uname">Username:</label> <input type="text" class="form-control" id="username" placeholder="아이디를 입력해주세요." name="username"  value="${principal.user.username }" readonly>
		</div>		
		<div class="form-group">
			<label for="pwd">Password:</label> <input type="password" class="form-control" id="password" placeholder="비밀번호를 입력해주세요." name="password" required>
			<div class="valid-feedback"></div>
			<div class="invalid-feedback">수정할 비밀번호를 입력해주세요.</div>
		</div>
		<div class="form-group">
			<label for="uname">Email:</label> <input type="text" class="form-control" id="email" placeholder="이메일 주소를 입력해주세요." name="email"  value="${principal.user.email }">
			<div class="valid-feedback"></div>
			<div class="invalid-feedback">수정할 이메일을 입력해 주세요</div>
		</div>		
	</form>
	<button id="btn-update" class="btn btn-primary">회원정보 수정 완료</button>
</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>


