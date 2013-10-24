package lt.nfq.conference.domain;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
	private Integer userId;
	private String email;
	private String name;
	private String surname;
	private String password;
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
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
		try {
			// Creating md5 hashing object
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			
			byte[] byteData = md5.digest(password.getBytes("UTF-8"));
			StringBuffer buffer = new StringBuffer(byteData.length * 2);
			
			for(byte b : byteData) {
				buffer.append(String.format("%02x", b & 0xff));
			}
			
			this.password = buffer.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
