package lt.nfq.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lt.nfq.conference.service.UserService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.codec.Hex;

public class ConferenceAuthenticationProvider implements AuthenticationProvider {
	
	private static final Logger logger = Logger.getLogger(ConferenceAuthenticationProvider.class);
	
	private ConferenceUserDetails userDetails;
	
	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken)authentication;
		
		/**
		 *  The identity of the principal being authenticated.
		 *  In the case of an authentication request with username and password, this would be the username.
		 */
		String userName = String.valueOf(auth.getPrincipal()); //"robertas.setkus@gmail.com";
		
		userDetails = userService.loadUserByUsername(userName);
		// check if realy user exists in database.
		// I wish I could know more simpler approach :)
		if(userDetails.getUsername().isEmpty()) {
			throw new BadCredentialsException("Bad login");
		}
		
		// This is usually a password.
		// You have to be sure how spring security is configured
		// because it could be plain password or salted.
		String password = "";
		try {
			MessageDigest md5Digest = MessageDigest.getInstance("MD5");
			byte[] data = md5Digest.digest(String.valueOf(auth.getCredentials()).getBytes());
			password = String.valueOf(Hex.encode(data));
		} catch (NoSuchAlgorithmException e) {
			logger.debug(e.getMessage());
		}
		
		if(!userDetails.getPassword().equals(password)) {
			throw new BadCredentialsException("Bad password");
		}
			
		UsernamePasswordAuthenticationToken principal = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		return principal;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
