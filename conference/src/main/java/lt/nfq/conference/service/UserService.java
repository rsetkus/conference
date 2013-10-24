package lt.nfq.conference.service;

import lt.nfq.conference.domain.User;
import lt.nfq.conference.service.dao.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	public User getUserById(Integer id) {
		return 	userMapper.getUser(id);	
	}
	
	public boolean createUser(User user) {
		return userMapper.insertUser(user) > 0;
	}
}
