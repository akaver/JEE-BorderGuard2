package border.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String getHomePage() {
		System.out.println("HomeController - home");
		return "home";
	}

	@RequestMapping("/user")
	public String getUserPage() {
		System.out.println("HomeController - user");
		return "home";
	}

	@RequestMapping("/admin")
	public String getAdminPage() {
		System.out.println("HomeController - admin");
		return "home";
	}
}
