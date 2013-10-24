package lt.nfq.conference.controller;

import java.util.HashMap;

import lt.nfq.conference.domain.User;
import lt.nfq.conference.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user/*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="register", method=RequestMethod.GET)
	public String register(ModelMap model) {
		model.addAttribute("user", new User());
		return "user/register";
	}
	
	@RequestMapping(value="create", method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String, String> create(@ModelAttribute("user") User user) {
		HashMap<String, String> response = new HashMap<String, String>();
		if(userService.createUser(user)) {
			response.put("success", "saved");
		} else {
			response.put("error", "error with saving");
		}
		
		return response;
	}
	
	@RequestMapping(value="edit/{userId}", method=RequestMethod.GET)
	public String edit(@PathVariable("user") String user) {
		return "";
	}
}
