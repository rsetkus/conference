package lt.nfq.conference.service.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import lt.nfq.conference.domain.ConferenceType;

public interface ConferenceTypeMapper {
	
	@Select("SELECT * FROM ConferenceType")
	public List<ConferenceType> getConferenceTypes();
}
