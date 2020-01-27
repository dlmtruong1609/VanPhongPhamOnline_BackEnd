package com.nhom4.vanphongphamonline.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nhom4.vanphongphamonline.model.Role;
import com.nhom4.vanphongphamonline.model.TaiKhoan;
import com.nhom4.vanphongphamonline.repository.RoleRepository;
import com.nhom4.vanphongphamonline.repository.TaiKhoanRepository;

import java.util.HashSet;

@Service
public class TaiKhoanServiceImpl implements TaiKhoanService {
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Override
	public void save(TaiKhoan taiKhoan) {
		// TODO Auto-generated method stub
		taiKhoan.setMatKhau(bCryptPasswordEncoder.encode(taiKhoan.getMatKhau()));
		taiKhoan.setMatKhauXacNhan(bCryptPasswordEncoder.encode(taiKhoan.getMatKhauXacNhan()));
		taiKhoan.setRoles(new HashSet<>(roleRepository.findAll()));
		taiKhoanRepository.insert(taiKhoan);
	}

	@Override
	public TaiKhoan findByUsername(String tenTaiKhoan) {
		// TODO Auto-generated method stub
		return taiKhoanRepository.findByUsername(tenTaiKhoan);
	}
}
