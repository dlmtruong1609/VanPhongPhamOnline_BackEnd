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

import com.nhom4.vanphongphamonline.model.Customer;
import com.nhom4.vanphongphamonline.model.Role;
import com.nhom4.vanphongphamonline.model.Account;
import com.nhom4.vanphongphamonline.repository.CustomerRepository;
@Service
public class AccountDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private CustomerRepository customerRepository;
	@Override // load user khi truy cập link website
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    Customer customer = customerRepository.findByAccount_Username(username);
        if (customer.getAccount() == null) throw new UsernameNotFoundException(username);


        return new CustomAccountDetails(customer);
	}
	
}
