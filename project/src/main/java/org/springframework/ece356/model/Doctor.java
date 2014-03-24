package org.springframework.ece356.model;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.core.style.ToStringCreator;

/**
 * Simple JavaBean domain object representing a user.
 */
@Entity
@Table(name = "doctor")
public class Doctor {
    
    // TODO fill this out in services
    public Collection<Patient> patients;
    
    // TODO fill this out in services
    public Collection<Appointment> appointments;
    
	@Column(name = "user_id")
	@NotEmpty
	private String user_id;

    @Column(name = "pay_rate")
    @NotEmpty
    private float pay_rate;

	
	public String getUserId() {
		return user_id;
	}

	public void setUserId(String user_id) {
		this.user_id = user_id;
	}

	public float getPayRate() {
		return pay_rate;
	}

	public void setPayRate(float pay_rate) {
		this.pay_rate = pay_rate;
	}
}
