package com.nhom4.vanphongphamonline.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.nhom4.vanphongphamonline.services.CustomTaiKhoanDetails;
import com.nhom4.vanphongphamonline.services.TaiKhoanDetailsServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
@Component
public class JwtTokenProvider {
	// Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
    private final String JWT_SECRET = "truongdeptrai";

    //Thời gian có hiệu lực của chuỗi jwt
    private final long JWT_EXPIRATION = 36000000;

    // Tạo ra jwt từ thông tin user
    public String generateToken(CustomTaiKhoanDetails customTaiKhoanDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        // Tạo chuỗi json web token từ id của user.
        return Jwts.builder()
                   .setSubject(customTaiKhoanDetails.getUsername())
                   .setIssuedAt(now)
                   .setExpiration(expiryDate)
                   .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                   .compact();
    }

    // Lấy thông tin user từ jwt
    public Long getTaiKhoanIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                            .setSigningKey(JWT_SECRET)
                            .parseClaimsJws(token)
                            .getBody();

        return Long.parseLong(claims.getSubject());
    }
    public String getTaiKhoanUserNameFromJWT(String token) {
        Claims claims = Jwts.parser()
                            .setSigningKey(JWT_SECRET)
                            .parseClaimsJws(token)
                            .getBody();

        return claims.getSubject();
    }
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
        	ex.printStackTrace();
        } catch (ExpiredJwtException ex) {
        	ex.printStackTrace();
        } catch (UnsupportedJwtException ex) {
        	ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
        	ex.printStackTrace();
        }
        return false;
    }
}
