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
@Table(name = "doct_lega")
public class DoctorPrescription {
	@Column(name = "doctor_user")
	@NotEmpty
	private String doctor_user;

	public String getDoctorUser() {
		return doctor_user;
	}

	public void setDoctorUser(String doctor_user) {
		this.doctor_user = doctor_user;
	}

	@Column(name = "legal_prescription_medication")
	@NotEmpty
	private int legal_prescription;

	public int getLegalPrescription() {
		return legal_prescription;
	}

	public void getLegalPrescription(int legal_prescription) {
		this.legal_prescription = legal_prescription;
	}
}
