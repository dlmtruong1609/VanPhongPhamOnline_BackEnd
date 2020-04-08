package com.nhom4.vanphongphamonline.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nhom4.vanphongphamonline.model.Customer;
import com.nhom4.vanphongphamonline.model.Role;
import com.nhom4.vanphongphamonline.repository.CustomerRepository;
@Service
public class CustomerService {
	@Autowired
	CustomerRepository customerRepository;
	// kiểm tra có phải là role admin hay ko?
	public boolean hasRoleAdmin() {
		Customer customer = customerRepository.findByAccount_Username(SecurityContextHolder.getContext().getAuthentication().getName());
        for (Role role : customer.getAccount().getRoles()){
        	if(role.getName().equals("ADMIN")) {
        		return true;
        	}
        }
        return false;
	}
}
