package com.tcs.authservice;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Service
public class JwtService {

	private final String secret = "my-super-secret-key-for-practice-only";
	
	public String generateToken(String username,String role) {
		try {
			// Convert our String secret into a key
			//SecretKey secretKey=new SecretKeySpec(secret.getBytes(),"HmacSHA256");
			
			// Create JWT header
			JWSHeader header=new JWSHeader(JWSAlgorithm.HS256);
			
			// Create JWT payload/claims
			
			 JWTClaimsSet claims = new JWTClaimsSet.Builder()
	                    .subject(username)
	                    .claim("role", role)
	                    .issueTime(Date.from(Instant.now()))
	                    .expirationTime(
	                            Date.from(
	                                    Instant.now().plus(1, ChronoUnit.HOURS)
	                            )
	                    )
	                    .build();
			 
			 SignedJWT signedJWT = new SignedJWT(header, claims);

	            // Sign JWT
	            signedJWT.sign(new MACSigner(secret));

	            // Return JWT as String
	            return signedJWT.serialize();
		}
		catch (Exception e) {
            throw new RuntimeException("Could not generate JWT", e);
        }
	}
}
