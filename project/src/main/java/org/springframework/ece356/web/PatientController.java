/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.ece356.web;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ece356.model.Login;
import org.springframework.ece356.model.User;
import org.springframework.ece356.service.PatientService;
import org.springframework.ece356.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@SessionAttributes("patient")
//http://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/mvc.html#mvc-ann-sessionattrib
public class PatientController {
	private final PatientService patientService;

	@Autowired
	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}

//	@RequestMapping("/login")
//	// @SessionAttributes({"user"})
//	public String loginUser(HttpSession session) {
//		session.setAttribute("id", 1);
//		session.setAttribute("type", userType.DOCTOR);
//		// input
//		return "welcome";
//	}
	
	@RequestMapping(value = "/patient", method = RequestMethod.GET)
	public String loginUser(Model model) {
		if(model.containsAttribute("patient")){
			return "patient";
		}
		model.addAttribute("patient", new Login());
		return "patient";
	}
/*
	@RequestMapping(value = "/patient", method = RequestMethod.POST)
	public String loginUser(@Valid Login login, BindingResult result, ModelMap modelMap) {
		System.out.printf("%s%s",login.getUsername(), login.getPassword());
	    if(result.hasErrors()) {
            return "patient";
        }
		User user = userService.validateLogin(login.getUsername(), login.getPassword());
		if(user == null){
			return "patient";
		}
		modelMap.addAttribute("user", user);
		return "patient";
	}
*/
	// Access example:
	// @Controller
	// @SessionAttributes({"user"})
	// public class InspectionTypeController {
	//
	// @RequestMapping(value="/addInspectionType.htm", method =
	// RequestMethod.POST )
	// public void addInspectionType(InspectionType inspectionType,
	// @ModelAttribute User user) {
	// System.out.println("User: "+ user.getUserDetails().getFirstName);
	// }
	// }
/*
	@RequestMapping("/logout")
	public String logoutUser(ModelMap modelMap, HttpSession session) {
		session.removeAttribute("user");
		modelMap.remove("user");
		return "logout";
	}*/
}
