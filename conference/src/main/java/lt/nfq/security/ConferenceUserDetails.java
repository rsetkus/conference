package lt.nfq.security;

import java.util.Collection;

import lt.nfq.conference.service.dao.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * User details information class
 * 
 * @author akademija
 */
public class ConferenceUserDetails extends User {
	
	/**
	 * User real name
	 */
	private String mName;
	
	/**
	 * User real surname
	 */
	private String mSurname;
	
	/**
	 * User id
	 */
	private int mUserId;
	
	@Autowired
	private UserMapper userMapper;

	/**
	 * Generated serial version id
	 */
	private static final long serialVersionUID = -3455166247672702876L;

	/**
	 * Constructor
	 * 
	 * @param username String login
	 * @param password String password
	 * @param enabled int active user flag
	 * @param accountNonExpired
	 * @param credentialsNonExpired
	 * @param accountNonLocked
	 * @param authorities Collection<GrantedAuthority> user roles
	 * @param name String real name
	 * @param surname String surname
	 * @param userId int user id
	 */
	public ConferenceUserDetails(String username, String password,
			boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities, String name, String surname, int userId) {
		
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
		
		mName = name;
		mSurname = surname;
		mUserId = userId;
	}
	
	/**
	 * Get full user name
	 * 
	 * @return String
	 */
	public String getUserFullName() {		
		return mName +" "+ mSurname;
	}
	
	/**
	 * Get user id
	 * 
	 * @return int
	 */
	public int getUserId() {
		return mUserId;
	}
}
