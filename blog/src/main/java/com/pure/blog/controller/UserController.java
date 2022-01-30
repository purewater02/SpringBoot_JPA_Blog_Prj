package com.pure.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pure.blog.model.KakaoProfile;
import com.pure.blog.model.OAuthToken;
import com.pure.blog.model.User;
import com.pure.blog.service.UserService;

// 인증이 되지 않은 사용자들이 출입할 수 있는 /auth/** 경로 허용
// 주소가 "/"일 경우 index.jsp 허용
// static에 있는 /js/**, /css/**, /image/** 등등 허용.

@Controller
public class UserController {
	
	@Value("${pure.key}")
	private String pureKey;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
		
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) {
		String rest_api_key = "b8fcc2cb4e694b98986d5501c3e20dc0";
		String redirect_uri = "http://localhost:8282/auth/kakao/callback";
		
		//POST방식으로 key = value 형태의 데이터를 요청 (x-www-form-urlencoded;charset=utf-8 방식)
		RestTemplate rt = new RestTemplate();
		
		//Http Header 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//Http Body 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", rest_api_key);
		params.add("redirect_uri", redirect_uri);
		params.add("code", code);
		
		//HttpHeader와 HttpBody를 하나의 오브젝트에 합치기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
				new HttpEntity<MultiValueMap<String,String>>(params, headers);
		
		//POST방식으로 http 요청하고 String타입으로 응답받아서 response 변수에 저장
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token", //요청주소
				HttpMethod.POST, 									//요청방식
				kakaoTokenRequest,									//request entity (http header랑 body 합친것)
				String.class 												//응답받을 타입
		);
		
		//JSON에 담기 위해 ObjectMapper 라이브러리 사용
		ObjectMapper om = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			//String 상태인 response.getBody를 ObjectMapper를 이용해 파싱하여 OAuthToken의 각 변수에 값을 넣어줌.
			oauthToken = om.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("카카오 액세스 토큰: "+oauthToken.getAccess_token());
		
		//사용자 토큰으로 사용자 정보 받기
		//POST방식으로 key = value 형태의 데이터를 요청 (x-www-form-urlencoded;charset=utf-8 방식)
		RestTemplate rt2 = new RestTemplate();
		
		//Http Header 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//HttpHeader와 HttpBody를 하나의 오브젝트에 합치기
		HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest2 =
				new HttpEntity<>(headers2);
		
		//POST방식으로 http 요청하고 String타입으로 응답받아서 response 변수에 저장
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me", //요청주소
				HttpMethod.POST, 									//요청방식
				kakaoProfileRequest2,									//request entity (http header랑 body 합친것)
				String.class 												//응답받을 타입
		);
		
		ObjectMapper om2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = om2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		System.out.println("카카오 아이디: "+kakaoProfile.getId());
		System.out.println("카카오 이메일: "+kakaoProfile.getKakao_account().getEmail());
		System.out.println("카카오로그인시 블로그서버 유저네임: "+kakaoProfile.getProperties().getNickname()+"_kakao");
		System.out.println("카카오로그인시 블로그서버 이메일: "+kakaoProfile.getKakao_account().getEmail());
		System.out.println("카카오로그인시 블로그서버 패스워드: "+pureKey);		
		
		User kakaoUser = User.builder()
				.username(kakaoProfile.getProperties().getNickname()+"_kakao")
				.password(pureKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();
		
		//기가입, 미가입 분기 처리
		User originUser = userService.회원찾기(kakaoUser.getUsername());
		if(originUser.getUsername() == null) { //미가입자인 경우 바로 회원가입
			System.out.println("미가입자 자동 회원가입 진행");
			userService.회원가입(kakaoUser);
		}
		System.out.println("자동 로그인");
		//세션 로그인 강제
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), kakaoUser.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);		
		
		return "redirect:/";
	}
	
}
