package com.nhom4.vanphongphamonline.services;

import com.nhom4.vanphongphamonline.model.TaiKhoan;

public interface TaiKhoanService {
	void save(TaiKhoan taiKhoan);

    TaiKhoan findByUsername(String tenTaiKhoan);
}
