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
import org.springframework.core.style.ToStringCreator;

/**
 * Simple JavaBean domain object representing a user.
 */
@Entity
@Table(name = "patient")
public class Patient {
	
    @Column(name = "user_id")
    @NotEmpty
    private int user_id;

    @Column(name = "version_number")
    @NotEmpty
    private String version_number;

    @Column(name = "phone_number")
    @NotEmpty
    private String phone_number;
    
    @Column(name = "health_card")
    @NotEmpty
    private String health_card;
    
    @Column(name = "sin")
    @NotEmpty
    private int sin;

    @Column(name = "address")
    @NotEmpty
    private String address;

    @Column(name = "current_health")
    @NotEmpty
    private int current_health;
    
    @Column(name = "doctor_account")
    @NotEmpty
    private String doctor_account;
    
    
    
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
//    private Set<Pet> pets;

    public int getUserId() {
        return this.user_id;
    }
    
    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getVersionNumber() {
		return version_number;
	}

	public void setVersionNumber(String version_number) {
		this.version_number = version_number;
	}

	public String getPhoneNumber() {
		return phone_number;
	}

	public void setPhoneNumber(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getHealthCard() {
		return health_card;
	}

	public void setHealthCard(String health_card) {
		this.health_card = health_card;
	}

	public int getSin() {
		return sin;
	}

	public void setSin(int sin) {
		this.sin = sin;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCurrentHealth() {
		return current_health;
	}

	public void setCurrentHealth(int current_health) {
		this.current_health = current_health;
	}

	public String getDoctorAccount() {
		return doctor_account;
	}

	public void setDoctorAccount(String doctor_account) {
		this.doctor_account = doctor_account;
	}
	
    // TODO: Add toString()
    @Override
    public String toString() {
        return new ToStringCreator(this)
                .toString();
    }
}
