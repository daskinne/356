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
import org.springframework.ece356.util.userType;

/**
 * Simple JavaBean domain object representing a user.
 */
@Entity
@Table(name = "user")
public class User {
	@Column(name = "user_id")
	@NotEmpty
	private String user_id;

	@Column(name = "password")
	@NotEmpty
	private String password;

	@Column(name = "first_name")
	@NotEmpty
	private String first_name;

	@Column(name = "is_officer")
	@NotEmpty
	private Boolean is_officer;
	
	private userType type;
	
	public userType getType(){
		return this.type;
	}
	public void setType(userType type){
		this.type = type;
	}
	
	public Boolean getIsOfficer() {
		return this.is_officer;
	}

	public void setIsOfficer(Boolean is_officer) {
		this.is_officer = is_officer;
	}
	
	// @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	// private Set<Pet> pets;

	public String getUserId() {
		return this.user_id;
	}

	public String getPassword() {
		return this.password;
	}

	public String getFistName() {
		return this.first_name;
	}

	public void setUserId(String user_id) {
		this.user_id = user_id;
	}

	public void setFistName(String first_name) {
		this.first_name = first_name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("userid", this.getUserId())
                .toString();
    }
    
    public boolean isNew() {
        return (this.user_id == null);
    }
    public String getId(String id) {
        return this.user_id;
    }
    public void setId(String id) {
        this.user_id = id;
    }
}
