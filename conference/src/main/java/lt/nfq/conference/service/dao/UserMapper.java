package lt.nfq.conference.service.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import lt.nfq.conference.domain.ConferenceUser;

public interface UserMapper {
	
	@Select("SELECT * FROM User WHERE userId=#{id}")
	public ConferenceUser getUser(@Param("id") int id);
	
	@Options(flushCache=true)
	@Insert("INSERT INTO User (username, password, name, surname) VALUES (#{username}, MD5(#{password}), #{name}, #{surname})")
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="userId", before=false, resultType=int.class)
	public int insertUser(ConferenceUser user);
	
	public ConferenceUser getUserByUsername(String username);
}
