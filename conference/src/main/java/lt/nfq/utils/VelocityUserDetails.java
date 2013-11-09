package lt.nfq.utils;

import java.util.HashSet;
import java.util.Set;

import lt.nfq.security.ConferenceUserDetails;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Velocity UserDetails wrapper class
 *  
 * @author akademija
 */
public class VelocityUserDetails {
	
	private static final Logger logger = Logger.getLogger(VelocityUserDetails.class);
	
	/**
	 * Get current UserDetails object
	 * 
	 * @return UserDetails
	 */
	private static ConferenceUserDetails getUserDetails() {
		ConferenceUserDetails userDetails = null;
		try {
			UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
			userDetails = (ConferenceUserDetails)auth.getPrincipal();
		} catch(ClassCastException e) {
			logger.debug(e.getMessage());
		}
		
		return userDetails;
	}
	
	/**
	 * Get logged in user roles
	 * 
	 * @return Set<String>
	 */
	private static Set<String> getUserRoles() {
		// current user roles holder
		Set<String> userRoles = new HashSet<String>();
		
		UserDetails userDetails = VelocityUserDetails.getUserDetails();
		if(null != userDetails) {
			for(GrantedAuthority authority : userDetails.getAuthorities()) {
				userRoles.add(authority.getAuthority());
			}
		}
		
		return userRoles;
		
	}
	
	/**
	 * Get current user name
	 * 
	 * @return current user name
	 */
	public static String getPrincipal() {
		String pricipal = "guest";
		
		ConferenceUserDetails userDetails = VelocityUserDetails.getUserDetails();
		if(null != userDetails) {
			pricipal = userDetails.getUserFullName();
		}
		
		return pricipal;
	}
	
	public int getUserId() {
		ConferenceUserDetails userDetails = VelocityUserDetails.getUserDetails();
		return userDetails.getUserId();
	}
	
	/**
	 * Is current user granted to any of passed authorities
	 * 
	 * @param authorities
	 * @return true if user has any of the listed roles, otherwise false
	 */
	public static boolean allGranted(String... authorities) {
		Set<String> roles = VelocityUserDetails.getUserRoles();
		
		if(!roles.isEmpty()) {
			for(String auth : authorities) {
				if(roles.contains(auth)) {
					return true;
				}
			}
		}
		
		return false;
	}
}