package com.nhom4.vanphongphamonline.services;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.nhom4.vanphongphamonline.model.Role;
import com.nhom4.vanphongphamonline.model.TaiKhoan;
public class CustomTaiKhoanDetails implements UserDetails {
	private TaiKhoan taiKhoan;
	
	public CustomTaiKhoanDetails(TaiKhoan taiKhoan) {
		// TODO Auto-generated constructor stub
		this.taiKhoan = taiKhoan;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
        for (Role role : taiKhoan.getRoles()){
        	return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.getTenRole()));
        }
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return taiKhoan.getMatKhau();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return taiKhoan.getTaiKhoan();
	}
	public String getEmail() {
		return taiKhoan.getEmail();
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
