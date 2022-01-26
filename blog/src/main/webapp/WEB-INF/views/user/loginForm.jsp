<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form action="/auth/loginProc" method="post">
		<div class="form-group">
			<label for="uname">Username:</label> <input type="text" class="form-control"  id="username" placeholder="아이디를 입력해주세요." name="username" required>
		</div>		
		<div class="form-group">
			<label for="pwd">Password:</label> <input type="password" class="form-control"  id="password" placeholder="비밀번호를 입력해주세요." name="password" required>
		</div>
		<div class="form-group form-check">
			<label class="form-check-label"> <input class="form-check-input" type="checkbox" name="remember" required> 로그인 상태 유지
			</label>
		</div>
		<button id="btn-login" class="btn btn-primary">로그인</button>
	</form>
</div>

<%@ include file="../layout/footer.jsp"%>


