package border.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String directToLogin() {
		return "login";
	}

	@RequestMapping(value = "/loginfailed")
	public String loginError(ModelMap model) {

		model.addAttribute("error", "login.message.loginError");

		return "login";
	}
}
