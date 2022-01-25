<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form action="/action_page.php" class="was-validated">
		<div class="form-group">
			<label for="uname">Username:</label> <input type="text" class="form-control" id="username" placeholder="아이디를 입력해주세요." name="username" required>
			<div class="valid-feedback"></div>
			<div class="invalid-feedback">멋진 아이디를 입력해주세요.</div>
		</div>		
		<div class="form-group">
			<label for="pwd">Password:</label> <input type="password" class="form-control" id="password" placeholder="비밀번호를 입력해주세요." name="password" required>
			<div class="valid-feedback"></div>
			<div class="invalid-feedback">비밀번호를 입력해주세요.</div>
		</div>
		<div class="form-group">
			<label for="uname">Email:</label> <input type="text" class="form-control" id="email" placeholder="이메일 주소를 입력해주세요." name="email" required>
			<div class="valid-feedback"></div>
			<div class="invalid-feedback">이메일을 입력해 주세요</div>
		</div>		
	</form>
	<button id="btn-save" class="btn btn-primary">회원가입 완료</button>
</div>

<script src="/blog/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>


