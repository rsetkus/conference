package lt.nfq.conference.service;

import java.util.ArrayList;
import java.util.List;

import lt.nfq.conference.domain.ConferenceRole;
import lt.nfq.conference.domain.ConferenceUser;
import lt.nfq.conference.service.dao.UserMapper;
import lt.nfq.security.ConferenceUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserMapper userMapper;
	
	private List<GrantedAuthority> getUserAuthorities(List<ConferenceRole> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(ConferenceRole role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
		}
		
		return authorities;
	}
	
	protected UserDetails createUserDetails(final String userName, final UserDetails userFromUserQuery, final List<GrantedAuthority> combinedAuthorities) {
		return null;
	}
	
	public ConferenceUser getUserById(Integer id) {
		return 	userMapper.getUser(id);	
	}
	
	public boolean createUser(ConferenceUser user) {
		return userMapper.insertUser(user) > 0;
	}

	@Override
	public ConferenceUserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		ConferenceUser user = userMapper.getUserByUsername(userName);
		
		String tUsername = user.getUsername();
		String tPassword = user.getPassword();
		boolean isEnabled = user.getEnabled() > 0;
		
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		
		ConferenceUserDetails userDetails = new ConferenceUserDetails(
				tUsername, tPassword, isEnabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, getUserAuthorities(user.getRoles()),
				user.getName(), user.getSurname(), user.getUserId());
		
		return userDetails;
	}
}
