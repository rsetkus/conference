package lt.nfq.conference.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user/*")
public class UserController {
	
	@RequestMapping(value="register", method=RequestMethod.GET)
	public String register(ModelMap model) {
		return "user/register";
	}
	
	@RequestMapping(value="edit/{userId}", method=RequestMethod.GET)
	public String edit(@PathVariable("user") String user) {
		return "";
	}
}
