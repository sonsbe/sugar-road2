package com.example.sugarroad2.config.auth;

import com.example.sugarroad2.model.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


public class NowUserDetails implements UserDetails{

	private Users user;

    public NowUserDetails(Users user){
        this.user = user;
    }

    public Users getUser() {
		return user;
	}

    @Override
    public String getPassword() { //인증 정보에 내장된 패스워드
        return user.getUserPassword();
    }

    @Override
    public String getUsername() { //인증 정보에 내장된 아이디
        return user.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        user.getRoleList().forEach(r -> {
        	authorities.add(()->{ return r;});
        });
        return authorities;
    }
}
