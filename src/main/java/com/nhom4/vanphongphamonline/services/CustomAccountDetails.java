package com.nhom4.vanphongphamonline.services;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nhom4.vanphongphamonline.models.Customer;
import com.nhom4.vanphongphamonline.models.Role;
public class CustomAccountDetails implements UserDetails {
	private Customer customer;
	
	public CustomAccountDetails(Customer customer) {
		// TODO Auto-generated constructor stub
		this.customer = customer;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		// kiểm tra role để truy cập đến các đường link cho phép
        for (Role role : customer.getAccount().getRoles()){
        	System.out.println(role.getName() + "role");
        	return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return customer.getAccount().getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return customer.getAccount().getUsername();
	}
	public String getEmail() {
		return customer.getAccount().getEmail();
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