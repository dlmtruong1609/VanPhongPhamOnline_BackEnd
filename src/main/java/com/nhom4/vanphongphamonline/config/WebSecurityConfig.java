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
@Order(1)
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
    	http.csrf().disable().addFilterBefore(corsFilter(), SessionManagementFilter.class).antMatcher("/api/v1/*")
            .authorizeRequests()
                .antMatchers("/api/v1/register").permitAll()
                .antMatchers("/api/v1/login").permitAll()
                .antMatchers("/api/v1/loginAdmin").permitAll()
                .antMatchers("/api/v1/sendEmail").permitAll()
                .antMatchers("/api/v1/admin/*").permitAll()
                .antMatchers("/admin/product/*").permitAll()
                .antMatchers("/admin/customer/*").permitAll()
                .antMatchers("/assets/js/plugins/*").permitAll()
                .antMatchers("/assets/js/core/*").permitAll()
                .antMatchers("/assets/js/*").permitAll()
                .antMatchers("/assets/css/*").permitAll()
                .antMatchers("/assets/demo/*").permitAll()
                .antMatchers("/assets/img/*").permitAll()
                .antMatchers("/assets/img/faces/*").permitAll()
                .antMatchers("/api/v1/admin/category/*").permitAll()
                .antMatchers("/api/v1/admin/supplier/*").permitAll()
                .antMatchers("/api/v1/admin/product/*").permitAll()
                .antMatchers("/api/v1/supplier/*").permitAll()
                .antMatchers("/api/v1/product/*").permitAll()
                .antMatchers("/api/v1/category/*").permitAll()
                .antMatchers("/api/v1/file/{id}.png").permitAll()
                .antMatchers("/api/v1/file/uploadFile").permitAll()
                .antMatchers("/api/v1/email/*").permitAll()
                .antMatchers("/api/v1/order/*").permitAll()
                .antMatchers("/api/v1/customer/*").permitAll()
                .antMatchers("/api/v1/cart/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    
}
