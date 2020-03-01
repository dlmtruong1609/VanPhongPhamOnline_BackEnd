package com.nhom4.vanphongphamonline.services;

public interface SecurityService {
	String findLoggedInUsername();

    void autoLogin(String username, String password); // tự động login sau khi đăng ký
}
