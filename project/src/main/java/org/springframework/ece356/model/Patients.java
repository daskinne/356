package org.springframework.ece356.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Patients {

    private List<Patient> patients;

    @XmlElement
    public List<Patient> getPatientList() {
        if (patients == null) {
        	patients = new ArrayList<Patient>();
        }
        return patients;
    }

}
