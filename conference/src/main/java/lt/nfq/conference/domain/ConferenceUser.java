package lt.nfq.conference.domain;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class ConferenceUser {
	
	private Integer userId;
	private String username;
	private String name;
	private String surname;
	private String password;
	private Integer enabled;
	private List<ConferenceRole> roles;
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String uname) {
		this.username = uname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<ConferenceRole> getRoles() {
		return roles;
	}

	public void setRoles(List<ConferenceRole> roles) {
		this.roles = roles;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
}
