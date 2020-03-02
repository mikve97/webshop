package services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/** 
 * @author Oussama Fahchouch
 */
public class AuthorisationService {
	byte[] secret = Base64.getDecoder().decode("uidupQNPG1sBZZNA34U9eTgECs6BVfhAIOZtWi/BR0Y=");
	
	/**
	 * @return String with JWT
	 */
	public String encodeJWToken(String email, int userId) {
		Instant now = Instant.now();
		
		String jwt = Jwts.builder()
			.setSubject(email)
			.setAudience("starcourt")
			.claim("userId", userId)
			.setIssuedAt(Date.from(now))
			.setExpiration(Date.from(now.plus(60, ChronoUnit.MINUTES)))
			.signWith(Keys.hmacShaKeyFor(this.secret))
			.compact();
		
		return jwt;
	}
	
	/**
	 * @param jwtoken
	 * @return boolean with validated jwt or not
	 */
	public boolean decodeJWToken(String jwtoken) {
		boolean validation = false;
		
		try {
			Jws<Claims> result = Jwts.parser()	
				.setSigningKey(Keys.hmacShaKeyFor(this.secret))
				.parseClaimsJws(jwtoken);
			
			validation = true;
	    } catch (Exception e) {
		      System.out.println("The JWT has an incorrect secret key. The request to the API is restricted.");
	    }
		
		return validation;
	}
	
	/**
	 * @param jwtoken
	 * @return String with username from jwtoken
	 */
	public String retrieveEmailFromJWToken(String jwtoken) {
		String username = "";
		
		Jws<Claims> result = Jwts.parser()	
				.setSigningKey(Keys.hmacShaKeyFor(this.secret))
				.parseClaimsJws(jwtoken);
			
		username = result.getBody().get("sub").toString();

		return username;
	}
	
	/**
	 * @param jwtoken
	 * @return boolean true after destroying jwtoken
	 */
	public boolean destroyJWToken(String jwtoken) {
		jwtoken = null;
		
		return true;
	}
}
