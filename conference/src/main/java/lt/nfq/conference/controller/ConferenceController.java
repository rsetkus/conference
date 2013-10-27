package lt.nfq.conference.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lt.nfq.conference.domain.Conference;
import lt.nfq.conference.service.ConferenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Conference resource controller class
 * 
 * @author akademija
 *
 */
@Controller
public class ConferenceController {
	
	@Autowired
	private ConferenceService conferenceService;
	
	private SimpleDateFormat getDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}
	
	/**
	 * Front conference portal request
	 * 
	 * @param model
	 * @return
	 */
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
	
	/**
	 * Request for creating new conference
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/conference/create", method=RequestMethod.GET)
	public String create(ModelMap model) {
		model.addAttribute("conference", new Conference());
		model.addAttribute("conferenceTypes", conferenceService.getConferenceTypes());
		
		return "conference/form";
	}
	
	/**
	 * Create new conference or update existing
	 * 
	 * @param conference
	 * @return
	 */
	@RequestMapping(value="/conference/manage", method=RequestMethod.POST)
	public String create(@ModelAttribute("conference") Conference conference) {
		int id = conferenceService.saveConference(conference);
		if(id > 0) {
			return "redirect:/conference/"+ id;
		}
		
		return "conference/form";
	}
	
	/**
	 * Delete conference request handler
	 * 
	 * @param conferenceId
	 * @return
	 */
	@RequestMapping(value="/conference/delete/{conferenceId}", method=RequestMethod.POST)
	public String delete(@PathVariable("conferenceId") String conferenceId) {
		try {
			int id = Integer.valueOf(conferenceId);
			conferenceService.deleteConference(id);
			
		} catch(NumberFormatException e) {
			
		}
		
		return "redirect:/";
	}
	
	/**
	 * Update existing conference
	 * 
	 * @param conference
	 * @return
	 */
	@RequestMapping(value="/conference/edit/{conferenceId}", method=RequestMethod.GET)
	public String create(ModelMap model, @PathVariable("conferenceId") String conferenceId) {
		try {
			int id = Integer.valueOf(conferenceId);
			model.addAttribute("conference", conferenceService.getConference(id));
			model.addAttribute("conferenceTypes", conferenceService.getConferenceTypes());
			model.addAttribute("dateFormat", getDateFormat());
		} catch(NumberFormatException e) {
			
		}
		
		return "conference/form";
	}
	
	/**
	 * Get conference details
	 * 
	 * @param model
	 * @param conferenceId
	 * @return
	 */
	@RequestMapping(value="/conference/{conferenceId}", method=RequestMethod.GET)
	public String view(ModelMap model, @PathVariable("conferenceId") String conferenceId) {
		//Do not know how to catch inner Spring Exceptions due to parameter casting
		//Expecting integer value, but could pass any string value
		try {
			int id = Integer.valueOf(conferenceId);
			model.addAttribute("conference", conferenceService.getConference(id));
		} catch(NumberFormatException e) {
			
		}
		
		return "conference/details";
	}
	
	/**
	 * Get all conferences by default or filter values
	 * 
	 * @param model
	 * @param from
	 * @param till
	 * @return
	 */
	@RequestMapping(value="/", method = RequestMethod.POST)
	public String filter(ModelMap model,
			@RequestParam(value="dateFrom") Date from,
			@RequestParam(value="dateTill") Date till) {
		
		model.addAttribute("conferences", conferenceService.getConferencesByDates(from, till));
		model.addAttribute("dateFormat", getDateFormat());
		
		return "conference/list";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dFormat = getDateFormat();
		dFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dFormat, false));
	}
}
