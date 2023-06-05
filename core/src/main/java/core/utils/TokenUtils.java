package core.utils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class TokenUtils {
	private static SecretKey key = Keys.hmacShaKeyFor("n2r5u8x/A?D(G-KaPdSgVkYp3s6v9y$B".getBytes((StandardCharsets.UTF_8)));
	public static String createJWT(String subject, long ttlMillis) {
		long nowMillis = System.currentTimeMillis();
	    Date now = new Date(nowMillis);
	    JwtBuilder builder = Jwts.builder()
	                                .setIssuedAt(now)
	                                .setSubject(subject)
	                                .signWith(key);
	    if (ttlMillis >= 0) {
	    	long expMillis = nowMillis + ttlMillis;
	        Date exp = new Date(expMillis);
	        builder.setExpiration(exp);
	    }
	    return builder.compact();
	}
	
	public static Jws<Claims> parseClaims(String jwt) {
		Jws<Claims> jws;
		try {
		    jws = Jwts.parserBuilder()
		    .setSigningKey(key)
		    .build()
		    .parseClaimsJws(jwt);
		    return jws;
		}catch (JwtException ex) {
			return null;
		}
	}
	
	public static String getUsernameFromToken(String jwt) {
		Jws<Claims> claims = parseClaims(jwt);
		Claims claimsBody = claims.getBody();
		return claimsBody.getSubject();
	}
}
