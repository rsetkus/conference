package lt.nfq.conference.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Conference {	
    private Integer conferenceId;
    private ConferenceType type;
    private String title;
    private String teaser;
    private Date conferenceFrom;
    private Date conferenceTill;
    private String address;
    private String description;
    private Integer isPublished;
    private List<User> attendees;

    public Integer getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Integer id) {
        conferenceId = id;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle(String name) {
        title = name;
    }

    public Date getConferenceFrom() {
        return conferenceFrom;
    }
    public String getConferenceFrom(SimpleDateFormat simpleDateFormat) {
        return simpleDateFormat.format(conferenceFrom);
    }

    public void setConferenceFrom(Date startDate) {
        conferenceFrom = startDate;
    }

    public Date getConferenceTill() {
        return conferenceTill;
    }

    public String getConferenceTill(SimpleDateFormat simpleDateFormat) {
        return simpleDateFormat.format(conferenceTill);
    }

    public void setConferenceTill(Date endDate) {
        conferenceTill = endDate;
    }

	public String getTeaser() {
		return teaser;
	}

	public void setTeaser(String teaser) {
		this.teaser = teaser;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Integer isPublished) {
		this.isPublished = isPublished;
	}

	public List<User> getAttendees() {
		return attendees;
	}

	public void setAttendees(List<User> attendees) {
		this.attendees = attendees;
	}

	public ConferenceType getType() {
		return type;
	}

	public void setType(ConferenceType type) {
		this.type = type;
	}
}
