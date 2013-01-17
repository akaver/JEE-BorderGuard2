package mvc.controller;

import javax.validation.Valid;

import mvc.domain.Person;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class FormController {

	@RequestMapping(value="/form")
	public String showForm(@ModelAttribute Person person) {
		return "form";
	}

	@RequestMapping(value="/form", method = RequestMethod.POST)
	public String saveForm(ModelMap model,
			@ModelAttribute @Valid Person person, BindingResult result) {

		if (!result.hasErrors()) {
			model.addAttribute("message", "message.ok");
		}

		return "form";
	}
}
