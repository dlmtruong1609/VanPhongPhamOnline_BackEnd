package com.nhom4.vanphongphamonline.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nhom4.vanphongphamonline.model.KhachHang;
import com.nhom4.vanphongphamonline.model.Role;
import com.nhom4.vanphongphamonline.model.TaiKhoan;
import com.nhom4.vanphongphamonline.repository.KhachHangRepository;
@Service
public class TaiKhoanDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private KhachHangRepository khachHangRepository;
	@Override // load user khi truy cập link website
	public UserDetails loadUserByUsername(String tenTaiKhoan) throws UsernameNotFoundException {
	    KhachHang khachHang = khachHangRepository.findByUsername(tenTaiKhoan);
        if (khachHang.getTaiKhoan() == null) throw new UsernameNotFoundException(tenTaiKhoan);


        return new CustomTaiKhoanDetails(khachHang);
	}
	
}
