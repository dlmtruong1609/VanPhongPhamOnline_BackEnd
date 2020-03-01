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

import com.nhom4.vanphongphamonline.model.KhachHang;
import com.nhom4.vanphongphamonline.model.Role;
import com.nhom4.vanphongphamonline.model.TaiKhoan;
public class CustomTaiKhoanDetails implements UserDetails {
	private KhachHang khachHang;
	
	public CustomTaiKhoanDetails(KhachHang khachHang) {
		// TODO Auto-generated constructor stub
		this.khachHang = khachHang;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		// kiểm tra role để truy cập đến các đường link cho phép
        for (Role role : khachHang.getTaiKhoan().getRoles()){
        	return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.getTenRole()));
        }
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return khachHang.getTaiKhoan().getMatKhau();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return khachHang.getTaiKhoan().getTaiKhoan();
	}
	public String getEmail() {
		return khachHang.getTaiKhoan().getEmail();
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
