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
@Table(name = "user_doct")
public class UserDoctor {
	@Column(name = "user_id")
	@NotEmpty
	private String user_id;

	public String getUserId() {
		return user_id;
	}

	public void setUserId(String user_id) {
		this.user_id = user_id;
	}
	@Column(name = "doctor_account")
	@NotEmpty
	private String doctor_account;

	public String getDoctorAccount() {
		return doctor_account;
	}

	public void setDoctorAccount(String doctor_account) {
		this.doctor_account = doctor_account;
	}

}
