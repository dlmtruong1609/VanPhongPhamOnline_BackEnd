package com.nhom4.vanphongphamonline.config;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nhom4.vanphongphamonline.jwt.JwtAuthenticationFilter;
import com.nhom4.vanphongphamonline.services.AccountDetailsServiceImpl;

//@Configuration
//@EnableWebSecurity
//@Order(2)
//public class WebSecurityConfigAdmin extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private UserDetailsService userDetailsService; // khởi tại userDetails
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return super.userDetailsService();
//    }
//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() { // Lọc token để đăng nhập và request với token khi truy cập server
//        return new JwtAuthenticationFilter();
//    }
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
////  mã hoá password khi đăng nhập, đăng ký
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//	@Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////		Kiểm tra password có đúng ko?
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
//    }
//    @Autowired
//    private AccountDetailsServiceImpl taiKhoanDetailsServiceImpl;
////	 danh sách các đường dẫn cho phép các loại role nào truy cập, nếu ko có mặc định là có token mới được truy cập
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//    	http.csrf().disable()
//            .authorizeRequests()
//                .antMatchers("/admin/*").hasRole("ADMIN")
//                .and().formLogin().loginPage("/login")
//                .permitAll().defaultSuccessUrl("/admin/product?index=0").and().addFilterBefore(new Filter() {
//					
//					@Override
//					public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//							throws IOException, ServletException {
//						// TODO Auto-generated method stub
//						HttpSession session = ((HttpServletRequest)request).getSession();
//						System.out.println(session.getAttribute("username") + "user");
//						if(session.getAttribute("username") != null) {
//							UserDetails userDetails = taiKhoanDetailsServiceImpl.loadUserByUsername(session.getAttribute("username").toString());
//			                if(userDetails != null) {
//			                    // Nếu người dùng hợp lệ, set thông tin cho Seturity Context (biến chung của project)
//			                    UsernamePasswordAuthenticationToken
//			                            authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//			                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
//
//			                    SecurityContextHolder.getContext().setAuthentication(authentication);
//			                }	
//						}
//					
//		                chain.doFilter(request, response);
//					}
//				}, UsernamePasswordAuthenticationFilter.class);
//       
//    }
//    }

