package border.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(LoginController.class);

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String directToLogin() {
		LOGGER.info("Entering login page");
		return "login";
	}

	@RequestMapping(value = "/loginfailed")
	public String loginError(ModelMap model) {
		LOGGER.info("Login failed");

		model.addAttribute("error", "login.message.loginError");
		return "login";
	}
}
