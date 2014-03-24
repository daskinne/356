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
public class PatientController {

    private final UserService userService;

    @Autowired
    public PatientController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/patients")
    public String showPatientList(Map<String, Object> model) {
    	if(model.get("user") == null){
    		return "redirect:/login";
    	}
        // Here we are returning an object of type 'Vets' rather than a collection of Vet objects 
        // so it is simpler for Object-Xml mapping
        Patients patients = new Patients();
        patients.getPatientList().addAll(this.userService.getPatients((User) model.get("user")));
        model.put("patients", patients);
        return "patientList";
    }
	
	@RequestMapping(value = "/patient", method = RequestMethod.GET)
	public String displayPatient(Map<String, Object> model) {
		if(model.get("user") == null){
    		return "redirect:/login";
    	}
		Patient patient = this.userService.findLatestPatientRevisionById(((User) model.get("user")).getUserId());
		model.put("patient", patient);
		return "patient";
	}
	
	@RequestMapping(value = "/patient", method = RequestMethod.PUT)
	public String updatePatient(@ModelAttribute("patient") Patient patient, BindingResult result, Map<String, Object> model) {
		if (result.hasErrors()) {
            return "/patient";
            // TODO: Error
        } else {
        	this.userService.savePatient(patient);
        	model.put("updateSuccess", 1);
            return "/patient";
        }
	}
	
	@RequestMapping(value = "/patient/{patientId}/profile", method = RequestMethod.GET)
    public String displayPatientLookup(@PathVariable("patientId") String patientId, Map<String, Object> model) {
		if(model.get("user") == null) {
    		return "redirect:/login";
    	}
		Patient patient = this.userService.findLatestPatientRevisionById(patientId);
		model.put("patient", patient);
		return "patient";
	}
}
