package lt.nfq.conference.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lt.nfq.conference.service.ConferenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConferenceController {
	
	@Autowired
	private ConferenceService conferenceService;
	
	private SimpleDateFormat getDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String list(ModelMap model) {
		SimpleDateFormat dFormat = getDateFormat();
		
		long timeNow = new Date().getTime();
		String startDate = dFormat.format(timeNow);
		String endDate = dFormat.format(timeNow + 1000 * 60 * 60 * 24 * 10); // + 10d
		
		model.addAttribute("dateFormat", dFormat);
		
		try {
			model.addAttribute("conferences", conferenceService.getConferencesByDates(dFormat.parse(startDate), dFormat.parse(endDate)));
		} catch (ParseException e) {
		}
		
		return "conference/list";
	}
	
	@RequestMapping(value="/conference", method=RequestMethod.GET)
	public String view(ModelMap model) {
		
		model.addAttribute("conference", conferenceService.getConference(0));
		return "conference/details";
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	public String filter(ModelMap model,
			@RequestParam(value="dateFrom") Date from,
			@RequestParam(value="dateTill") Date till) {
		
		model.addAttribute("conferences", conferenceService.getConferencesByDates(from, till));
		model.addAttribute("dateFormat", getDateFormat());
		
		return "conference/list";
	}
	
	@InitBinder
	public void iniBinder(WebDataBinder binder) {
		SimpleDateFormat dFormat = getDateFormat();
		dFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dFormat, false));
	}
}
