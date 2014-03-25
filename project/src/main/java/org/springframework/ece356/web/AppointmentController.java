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

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ece356.model.Appointment;
import org.springframework.ece356.model.Patient;
import org.springframework.ece356.model.Patients;
import org.springframework.ece356.model.Pet;
import org.springframework.ece356.model.User;
import org.springframework.ece356.model.Vets;
import org.springframework.ece356.service.ClinicService;
import org.springframework.ece356.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("user")
public class AppointmentController {

	private final UserService userService;

	@Autowired
	public AppointmentController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/patient/{patientId}/appointment/new", method = RequestMethod.POST)
	public String newAppointment(@PathVariable("patientId") String patientId,
			@ModelAttribute("appointment") Appointment appointment,
			BindingResult result, Map<String, Object> model) {
		// TODO: check patientId == appointmentPatientId
		if (result.hasErrors()) {
			return "patient/appointment";
			// TODO: Error
		}
		userService.newAppointment(appointment);
		model.put("updateSuccess", 1);
		return "/patient";
	}

	@RequestMapping(value = "/patient/appointment", method = RequestMethod.PUT)
	public String updateAppointment(
			@ModelAttribute("appointment") Appointment appointment,
			BindingResult result, Map<String, Object> model) {
		// TODO: check patientId == appointmentPatientId
		if (result.hasErrors()) {
			return "patient/appointment";
			// TODO: Error
		}
		userService.newAppointment(appointment);
		model.put("updateSuccess", 1);
		return "/patient";
	}

	@RequestMapping("/patient/{patientId}/appointment/new")
	public String addAppointmentPatient(
			@PathVariable("patientId") String patientId,
			Map<String, Object> model) {
		Appointment apt = new Appointment();
		Patient pat = userService.findPatientById(patientId);
		if (pat == null) {
			return "redirect:login";
		}
		apt.setPatientAccount(patientId);
		apt.setDoctorAccount(pat.getDoctorAccount());
		model.put("appointment", apt);
		return "patient/appointment";

	}

	@RequestMapping("/patient/{patientId}/appointments")
	public String showAppointmentList(
			@PathVariable("patientId") String patientId,
			Map<String, Object> model) {

		userService.getAppointmentsForUser(patientId);
		return "appointmentList";

	}

}
