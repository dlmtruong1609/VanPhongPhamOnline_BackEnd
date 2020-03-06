package com.nhom4.vanphongphamonline.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nhom4.vanphongphamonline.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService; // khởi tại userDetails
    @Bean
    public UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() { // Lọc token để đăng nhập và request với token khi truy cập server
        return new JwtAuthenticationFilter();
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
//  mã hoá password khi đăng nhập, đăng ký
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		Kiểm tra password có đúng ko?
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
//	fix lỗi CORS
	@Bean
	CORSFilter corsFilter() {
	    CORSFilter filter = new CORSFilter();
	    return filter;
	}

//	 danh sách các đường dẫn cho phép các loại role nào truy cập, nếu ko có mặc định là có token mới được truy cập
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable().addFilterBefore(corsFilter(), SessionManagementFilter.class)
            .authorizeRequests()
                .antMatchers("/api/nhacungcap/data").permitAll()
                .antMatchers("/api/dangky").permitAll()
                .antMatchers("/api/dangnhap").permitAll()
                .antMatchers("/api/dangxuat").hasAnyRole("MEMBER", "ADMIN")
                .antMatchers("/api/guiemail").permitAll()
                .antMatchers("/api/quanly/sanpham/danhsach").permitAll()
                .antMatchers("/api/quanly/sanpham/chitiet").permitAll()
                .antMatchers("/api/sanpham/trang").permitAll()
                .antMatchers("/api/img/them").permitAll()
                .antMatchers("/api/img/hinhanh").permitAll()
                .antMatchers("/api/hoadon/thanhtoan").hasRole("MEMBER")
                .antMatchers("/api/hoadon/chitiet").hasRole("MEMBER")
                .antMatchers("/api/khachhang/capnhat").hasRole("MEMBER")
                .antMatchers("/api/khachhang/chitiet").hasAnyRole("MEMBER", "ADMIN")
                .antMatchers("/api/quanly/sanpham/timkiem").permitAll()
                .antMatchers("/api/quanly/sanpham/them").hasRole("ADMIN")
                .antMatchers("/api/quanly/sanpham/xoa").hasRole("ADMIN")
                .antMatchers("/api/quanly/sanpham/capnhat").hasRole("ADMIN")
                .antMatchers("/api/giohang/them").permitAll()
                .antMatchers("/api/giohang/dulieu").permitAll()
                .antMatchers("/api/giohang/capnhat").permitAll()
                .antMatchers("/api/giohang/xoa").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    
}
