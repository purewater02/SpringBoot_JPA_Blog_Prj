package com.pure.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pure.blog.model.User;

import lombok.Getter;

//스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행한 후 완료가 되면 UserDetails 타입의 오브젝트를 스프링 시큐리티의 세션에 저장해줌.
@Getter
public class PrincipalDetail implements UserDetails {
		
	private User user;
	
	public PrincipalDetail(User user) {
		this.user = user;
	}
	
	@Override
	public String getPassword() {		
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	//계정이 만료되지 않았는지를 리턴 (true: 아직 유효)
	@Override
	public boolean isAccountNonExpired() {		
		return true;
	}

	//계정이 잠겨있지 않은지 리턴 (ture: 잠겨있지 않음)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//비밀변호가 만료되지 않았는지 리턴 (true: 아직 유효)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//계정 활성화 상태인지 리턴 (true: 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}	
	
	//계정이 갖고 있는 권한 목록을 리턴함. 원래 권한이 여러개 있을 수 있어서 루프를 돌려야 하지만 지금은 권한이 USER 하나밖에 없다.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		/*
		 * collectors.add(new GrantedAuthority() {
		 * 
		 * @Override public String getAuthority() { return "ROLE_"+user.getRole();
		 * //ROLE_USER 스프링 시큐리티에서는 role 앞에 "ROLE_"을 붙여주어야 인식. } });
		 */
		// add() 안에는 GrantedAuthority 밖에 못들어 가고 GrantedAuthority 안에는 메서드가 하나밖에 없기 때문에 람다식으로 처리
		collectors.add(() -> {return "ROLE_"+user.getRole();});
		
		return collectors;
	}
}
