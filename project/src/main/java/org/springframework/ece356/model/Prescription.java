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
@Table(name = "legal_prescription")
public class Prescription {
	@Column(name = "medication")
	@NotEmpty
	private int medication;

	public int getMedication() {
		return medication;
	}

	public void setMedication(int medication) {
		this.medication = medication;
	}
}
