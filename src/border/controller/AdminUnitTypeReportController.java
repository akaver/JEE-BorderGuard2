package border.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import border.model.AdminUnitType;
import border.model.AdminUnitTypeJSON;
import border.service.AdminUnitTypeService;

@Controller
@RequestMapping("/AdminUnitTypeReport")
public class AdminUnitTypeReportController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AdminUnitTypeController.class);

	@Autowired
	AdminUnitTypeService adminUnitTypeService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		LOGGER.info("/");

		return "AdminUnitTypeReport";
	}

	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public String returnJSON(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOGGER.info("/");

		
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		Collection<AdminUnitTypeJSON> children = new ArrayList<AdminUnitTypeJSON>();

		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			System.out.println("get parameters:" + paramName + " = "
					+ request.getParameter(paramName));

			if (paramName.equals("root")) {
				if (request.getParameter("root").equals("source")) {

					children.add(saveToJSONType(adminUnitTypeService.getByID(1L),
							adminUnitTypeService.getSubordinateCount(adminUnitTypeService
											.getByID(1L)) >= 1));
				} else {
					// load the list of childrens
					for (AdminUnitType adminUnitType : adminUnitTypeService
							.getSubordinates(adminUnitTypeService.getByID(Long.parseLong(request
									.getParameter("root"))), "NOW()")) {

						children.add(saveToJSONType(
								adminUnitType,
								adminUnitTypeService.getSubordinateCount(adminUnitType) >= 1));
					}

				}
			}

		}

		out.println(gson.toJson(children));

		out.flush();		
		
		
		
		
		return null;
	}

	private AdminUnitTypeJSON saveToJSONType(AdminUnitType adminUnitType,
			Boolean hasChildren) {
		AdminUnitTypeJSON res = new AdminUnitTypeJSON();

		res.setText(adminUnitType.getName());
		res.setExpanded(false);
		res.setId(adminUnitType.getAdminUnitTypeID().toString());
		res.setHasChildren(hasChildren);

		return res;
	}

}
