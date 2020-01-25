package com.nhom4.vanphongphamonline.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nhom4.vanphongphamonline.model.Role;
import com.nhom4.vanphongphamonline.model.TaiKhoan;
import com.nhom4.vanphongphamonline.repository.TaiKhoanRepository;

public class TaiKhoanDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private TaiKhoanRepository taiKhoanRepository;
	@Override
	public UserDetails loadUserByUsername(String tenTaiKhoan) throws UsernameNotFoundException {
	    TaiKhoan taiKhoan = taiKhoanRepository.findByUsername(tenTaiKhoan);
        if (taiKhoan == null) throw new UsernameNotFoundException(tenTaiKhoan);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        
        for (Role role : taiKhoan.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getTenRole()));
        }

        return new org.springframework.security.core.userdetails.User(taiKhoan.getTaiKhoan(), taiKhoan.getMatKhau(), grantedAuthorities);
	}
	
}
