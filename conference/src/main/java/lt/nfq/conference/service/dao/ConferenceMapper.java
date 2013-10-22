package lt.nfq.conference.service.dao;

import java.util.Date;
import java.util.List;

import lt.nfq.conference.domain.Conference;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface ConferenceMapper {

    @Select("SELECT * FROM conference WHERE conferenceId=#{id}")
    public Conference getConference(@Param("id") int id);

    @Select("SELECT * FROM conference")
    public List<Conference> getConferences();

    @Select("SELECT * FROM conference WHERE conferenceFrom > #{start} and conferenceFrom < #{end} and conferenceTill < #{end} and conferenceTill > #{start}")
    public List<Conference> getConferencesByDates(@Param("start") Date start, @Param("end") Date end);

    @Options(flushCache=true)
    @Update("UPDATE conference set title = #{name}, conferenceFrom = #{startDate}, conferenceTill = #{endDate} WHERE conferenceId=#{id}")
    public int updateConference(Conference conference);

    @Options(flushCache=true)
    @Insert("INSERT INTO conference (title, conferenceFrom, conferenceTill) VALUES (#{name}, #{startDate}, #{endDate})")
    @SelectKey(statement="CALL IDENTITY()", keyProperty="id", before=false, resultType=int.class)
    public int insertConference(Conference conference);
    
}
