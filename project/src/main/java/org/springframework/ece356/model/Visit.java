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

import com.sun.istack.Nullable;

/**
 * Simple JavaBean domain object representing a user.
 */
@Entity
@Table(name = "visit")
public class Visit {
	
    // Fill this out in service
    @Nullable
    private Treatment treatment;
    
    @Column(name = "appointment_patient_account")
    @NotEmpty
    private String appointment_patient_account;

    @Column(name = "appointment_patient_version_number")
    @NotEmpty
    private int appointment_patient_version_number;

    @Column(name = "appointment_version_number")
    @NotEmpty
    private int appointment_version_number;
    
    @Column(name = "appointment_start_time")
    @NotEmpty
    private DateTime appointment_start_time;
    
    @Column(name = "diagnosis")
    @NotEmpty
    private String diagnosis;
    
    @Column(name = "diagnostic_procedure")
    @NotEmpty
    private String diagnostic_procedure;
    
    @Column(name = "doctor_comments")
    @NotEmpty
    private String doctor_comments;
    
    public String getAppointmentPatientAccount() {
        return this.appointment_patient_account;
    }
    
    public void setAppointmentPatientAccount(String appointment_patient_account) {
        this.appointment_patient_account = appointment_patient_account;
    }

    public int getAppointmentPatientVersionNumber() {
        return this.appointment_patient_version_number;
    }
    
    public void setAppointmentPatientVersionNumber(int appointment_patient_version_number) {
        this.appointment_patient_version_number = appointment_patient_version_number;
    }
    
    public int getAppointmentVersionNumber() {
        return this.appointment_version_number;
    }
    
    public void setAppointmentVersionNumber(int appointment_version_number) {
        this.appointment_version_number = appointment_version_number;
    }
    
    public DateTime getAppointmentStartTime() {
        return this.appointment_start_time;
    }
    
    public void setAppointmentStartTime(DateTime appointment_start_time) {
        this.appointment_start_time = appointment_start_time;
    }
    
    public String getDiagnosis() {
        return this.diagnosis;
    }
    
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
    
    public String getDiagnosticProcedure() {
        return this.diagnostic_procedure;
    }
    
    public void setDiagnosticProcedure(String diagnostic_procedure) {
        this.diagnostic_procedure = diagnostic_procedure;
    }
    
    public String getDoctorComments() {
        return this.doctor_comments;
    }
    
    public void setDoctorComments(String doctor_comments) {
        this.doctor_comments = doctor_comments;
    }
    
    // TODO: Add toString()
    @Override
    public String toString() {
        return new ToStringCreator(this)
                .toString();
    }
}
