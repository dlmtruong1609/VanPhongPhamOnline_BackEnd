package com.nhom4.vanphongphamonline.config;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nhom4.vanphongphamonline.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Bean
    public UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
//    	http.authorizeRequests().antMatchers("/resources/**").permitAll();
        http
        .cors().and().csrf().disable()
            .authorizeRequests()
                .antMatchers("/api/nhacungcap/data").permitAll()
                .antMatchers("/api/dangky").permitAll()
                .antMatchers("/api/dangnhap").permitAll()
                .antMatchers("/api/guiemail").permitAll()
                .antMatchers("/api/quanly/sanpham/danhsach").permitAll()
                .antMatchers("/api/sanpham/trang").permitAll()
                .antMatchers("/api/img/them").permitAll()
                .antMatchers("/api/img/hinhanh").permitAll()
                .antMatchers("/api/hoadon/thanhtoan").hasRole("MEMBER")
                .antMatchers("/api/hoadon/chitiet").hasRole("MEMBER")
                .antMatchers("/api/khachhang/capnhat").hasRole("MEMBER")
                .antMatchers("/api/khachhang/chitiet").hasAnyRole("MEMBER", "ADMIN")
                .antMatchers("/api/quanly/sanpham/chitiet").permitAll()
                .antMatchers("/api/quanly/sanpham/timkiem").permitAll()
                .antMatchers("/api/quanly/sanpham/them").hasRole("ADMIN")
                .antMatchers("/api/quanly/sanpham/xoa").hasRole("ADMIN")
                .antMatchers("/api/quanly/sanpham/capnhat").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());

        return source;
    }
    
}
