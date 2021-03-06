let index = {
	init: function() {
		$("#btn-save").on("click", ()=>{
			this.save();
		});
		$("#btn-update").on("click", ()=>{
			this.update();
		});
	},
	
	save: function() {
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		}; //javascript 오브젝트임.
		
		//ajax 호출시 default가 비동기 호출
		//ajax 통신을 이용하여 위의 data를 json으로 변경하여 insert 요청할 것임.
		//ajax가 통신에 성공해서 서버가 json형태로 응답을 해주면 자동으로 자바 오브젝트로 변환해줌. (dataType을 지정하지 않아도.)
		$.ajax({
			//회원가입 수행 요청
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data), //js object를 JSON형태로 변경. http body 데이터.
			contentType: "application/json; charset=utf-8" //body 데이터의 타입 지정 (MIME)
			//dataType: "json" //서버에서 응답이 올 때 문자열로 오는데 문자열 형태가 'json'이라면 => js object로 파싱 해줌.			
		}).done(function(resp){
			if(resp.status == 200) {
				alert("회원가입 성공!!.");
				location.href="/";
			}else {
				alert("이미 가입된 username입니다... 다른 username을 입력해 주세요.");
			}						
		}).fail(function(error){
			alert(JSON.stringify(error));			
		}); //ajax 통신을 이용하여 3개의 데이터를 JSON으로 변경하여 insert 요청.
	},
	
	update: function() {
		let data = {
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		$.ajax({			
			type: "PUT",
			url: "/user/update",
			data: JSON.stringify(data), //js object를 JSON형태로 변경. http body 데이터.
			contentType: "application/json; charset=utf-8", //body 데이터의 타입 지정 (MIME)
			dataType: "json" //서버에서 응답이 올 때 문자열로 오는데 문자열 형태가 'json'이라면 => js object로 파싱 해줌.			
		}).done(function(resp){			
			alert("회원정보수정이 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));			
		});
	}	
	
	
/*		login: function() {
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),			
		}; //javascript 오브젝트임.		
		
		$.ajax({
			//회원가입 수행 요청
			type: "POST",
			url: "/api/user/login",
			data: JSON.stringify(data), //js object를 JSON형태로 변경. http body 데이터.
			contentType: "application/json; charset=utf-8", //body 데이터의 타입 지정 (MIME)
			dataType: "json" //서버에서 응답이 올 때 문자열로 오는데 문자열 형태가 'json'이라면 => js object로 파싱 해줌.			
		}).done(function(resp){			
			alert("로그인이 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));			
		}); //ajax 통신을 이용하여 3개의 데이터를 JSON으로 변경하여 insert 요청.
	}
*/
}

index.init();