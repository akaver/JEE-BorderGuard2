package border.controller;

import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@Resource
	private MessageSource resources;

	@RequestMapping(value="/home")
	public String home(ModelMap model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("username", authentication.getName());

		return "home";
	}

	@RequestMapping(value="/loginfailed")
	public String loginerror(ModelMap model) {

		model.addAttribute("error", "message.loginError");

		return "login";
	}

	@RequestMapping(value="/login")
	public String login() {
 		return "login";
 	}

	@RequestMapping(value="/info")
	public String authenticationInfoExample(HttpSession session) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		for (GrantedAuthority authority : authentication.getAuthorities()) {
			if (authority.getAuthority().equals("ROLE_ADMIN")) {
				// do admin stuff
			}
		}

		System.out.println(session.getAttribute("SPRING_SECURITY_CONTEXT"));

		return "home";
	}

	@RequestMapping(value="/message")
	public String messageExample(Locale locale) {

		String[] params = { "p1", "p2" };
		System.out.println(resources.getMessage("message.paramExample", params, locale));

		return "home";
	}
}
