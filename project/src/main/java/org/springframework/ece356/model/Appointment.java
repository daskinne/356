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
@Table(name = "appointment")
public class Appointment {
	
    @Column(name = "patient_account")
    @NotEmpty
    private String patient_account;

    @Column(name = "patient_version_number")
    @NotEmpty
    private int patient_version_number;

    @Column(name = "doctor_account")
    @NotEmpty
    private String doctor_account;
    
    @Column(name = "version_number")
    @NotEmpty
    private int version_number;
    
    @Column(name = "start_time")
    @NotEmpty
    private DateTime start_time;
    
    @Column(name = "end_time")
    @NotEmpty
    private DateTime end_time;
    
    public String getPatientAccount() {
        return this.patient_account;
    }
    
    public void setPatientAccount(String patient_account) {
        this.patient_account = patient_account;
    }

    public int getPatientVersionNumber() {
        return this.patient_version_number;
    }
    
    public void setPatientVersionNumber(int patient_version_number) {
        this.patient_version_number = patient_version_number;
    }
    
    public String getDoctorAccount() {
        return this.doctor_account;
    }
    
    public void setDoctorAccount(String doctor_account) {
        this.doctor_account = doctor_account;
    }
    
    public int getVersionNumber() {
        return this.version_number;
    }
    
    public void setVersionNumber(int version_number) {
        this.version_number = version_number;
    }
    
    public DateTime getStartTime() {
        return this.start_time;
    }
    
    public void setStartTime(DateTime start_time) {
        this.start_time = start_time;
    }
    
    public DateTime getEndTime() {
        return this.end_time;
    }
    
    public void setEndTime(DateTime end_time) {
        this.end_time = end_time;
    }
    
    // TODO: Add toString()
    @Override
    public String toString() {
        return new ToStringCreator(this)
                .toString();
    }
}
