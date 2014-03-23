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
package org.springframework.ece356.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.springframework.core.style.ToStringCreator;

/**
 * Simple JavaBean domain object representing a user.
 */
@Entity
@Table(name = "lega_visi")
public class LegalPrescriptionVisit {
	
    @Column(name = "visit_appointment_patient_account")
    @NotEmpty
    private String visit_appointment_patient_account;

    @Column(name = "visit_appointment_patient_version_number")
    @NotEmpty
    private int visit_appointment_patient_version_number;

    @Column(name = "visit_appointment_version_number")
    @NotEmpty
    private int visit_appointment_version_number;
    
    @Column(name = "visit_appointment_start_time")
    @NotEmpty
    private DateTime visit_appointment_start_time;
    
    @Column(name = "legal_prescription_medication")
    @NotEmpty
    private int legal_prescription_medication;
    
    @Column(name = "end_date")
    @NotEmpty
    private DateTime end_date;
    
    @Column(name = "dosage")
    @NotEmpty
    private String dosage;
    
    public String getVisitAppointmentPatientAccount() {
        return this.visit_appointment_patient_account;
    }
    
    public void setVisitAppointmentPatientAccount(String visit_appointment_patient_account) {
        this.visit_appointment_patient_account = visit_appointment_patient_account;
    }

    public int getVisitAppointmentPatientVersionNumber() {
        return this.visit_appointment_patient_version_number;
    }
    
    public void setVisitAppointmentPatientVersionNumber(int visit_appointment_patient_version_number) {
        this.visit_appointment_patient_version_number = visit_appointment_patient_version_number;
    }
    
    public int getVisitAppointmentVersionNumber() {
        return this.visit_appointment_version_number;
    }
    
    public void setVisitAppointmentVersionNumber(int visit_appointment_version_number) {
        this.visit_appointment_version_number = visit_appointment_version_number;
    }
    
    public DateTime getVisitAppointmentStartTime() {
        return this.visit_appointment_start_time;
    }
    
    public void setVisitAppointmentStartTime(DateTime visit_appointment_start_time) {
        this.visit_appointment_start_time = visit_appointment_start_time;
    }
    
    public int getLegalPrescriptionMedication() {
        return this.legal_prescription_medication;
    }
    
    public void setLegalPrescriptionMedication(int legal_prescription_medication) {
        this.legal_prescription_medication = legal_prescription_medication;
    }
    
    public DateTime getEndDate() {
        return this.end_date;
    }
    
    public void setEndDate(DateTime end_date) {
        this.end_date = end_date;
    }
    
    public String getDosage() {
        return this.dosage;
    }
    
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
    
    // TODO: Add toString()
    @Override
    public String toString() {
        return new ToStringCreator(this)
                .toString();
    }
}
