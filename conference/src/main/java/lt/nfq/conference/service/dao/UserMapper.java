package lt.nfq.conference.service.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import lt.nfq.conference.domain.User;

public interface UserMapper {
	
	@Select("SELECT * FROM User WHERE userId=#{id}")
	public User getUser(@Param("id") int id);
	
	@Options(flushCache=true)
	@Insert("INSERT INTO User (email, password, name, surname) VALUES (#{email}, #{password}, #{name}, #{surname})")
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="userId", before=false, resultType=int.class)
	public int insertUser(User user);
}
