package com.nhom4.vanphongphamonline.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CORSFilter implements Filter {

    // Danh sách domain cho phép truy cập server, có thể ghi thêm
    private final List<String> allowedOrigins = Arrays.asList("http://localhost:3000", "https://vanphongphamonlinevn.herokuapp.com"); 

    public void destroy() {

    }
//  Cấu hình yêu cầu khi request
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        // Lets make sure that we are working with HTTP (that is, against HttpServletRequest and HttpServletResponse objects)
        if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;

            // Access-Control-Allow-Origin
            String origin = request.getHeader("Origin");
            response.setHeader("Access-Control-Allow-Origin", allowedOrigins.contains(origin) ? origin : "");
            response.setHeader("Vary", "Origin");

            // Access-Control-Max-Age
            response.setHeader("Access-Control-Max-Age", "3600");

            // Access-Control-Allow-Credentials
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setStatus(200);
            // Access-Control-Allow-Methods
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            // Access-Control-Allow-Headers
            response.setHeader("Access-Control-Allow-Headers", 
            		"Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, Content-Type, "
            		+ "Access-Control-Request-Method, "
            		+ "Access-Control-Request-Headers, Authorization, "
            		+ "Access-Control-Allow-Origin, " + "X-CSRF-TOKEN");
        }

        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {
    }
}