package lt.nfq.conference.controller;

import java.security.Principal;
import java.util.HashMap;

import lt.nfq.conference.domain.ConferenceUser;
import lt.nfq.conference.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user/*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * User registration form request
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="register", method=RequestMethod.GET)
	public String register(ModelMap model) {
		model.addAttribute("user", new ConferenceUser());
		return "user/register";
	}
	
	/**
	 * Posting fresh new user details for creating new user
	 * 
	 * @param user User
	 * @return
	 */
	@RequestMapping(value="create", method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String, String> create(@ModelAttribute("user") ConferenceUser user) {
		HashMap<String, String> response = new HashMap<String, String>();
		if(userService.createUser(user)) {
			response.put("success", "saved");
		} else {
			response.put("error", "error with saving");
		}
		
		return response;
	}
	
	/**
	 * Request user edit form
	 * 
	 * @param user User
	 * @return
	 */
	@RequestMapping(value="edit", method=RequestMethod.GET)
	public String edit(ModelMap model) {
		try {
			Principal user = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.addAttribute("user", user);
		} catch (ClassCastException e) {
			// dump user
			model.addAttribute("user", new ConferenceUser());
		}
		
		return "user/user";
	}
	
	/**
	 * Request user login form (inner security framework is configured to use).
	 * 
	 * @return
	 */
	@RequestMapping(value="login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
}
