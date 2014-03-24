package org.springframework.ece356.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.ece356.model.Doctor;
import org.springframework.ece356.model.Patient;
import org.springframework.ece356.model.User;
import org.springframework.ece356.model.Vet;
import org.springframework.ece356.repository.jdbc.JdbcDoctorRepository;
import org.springframework.ece356.repository.jdbc.JdbcPatientRepository;
import org.springframework.ece356.repository.jdbc.JdbcUserRepository;
import org.springframework.ece356.util.userType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
	private JdbcUserRepository userRepository;
	private JdbcDoctorRepository doctorRepository;
	private JdbcPatientRepository patientRepository;

	@Autowired
	public UserService(JdbcUserRepository userRepository, JdbcDoctorRepository doc, JdbcPatientRepository patient) {
		this.userRepository = userRepository;
		this.doctorRepository = doc;
		this.patientRepository = patient;
	}

	@Transactional(readOnly = true)
	public User findUserById(int id) {
		return userRepository.findByKey(id);
	}

	@Transactional(readOnly = true)
	public Patient findLatestPatientRevisionById(String id) {
		return patientRepository.findLatestRevision(id);
	}

	@Transactional(readOnly = true)
	public User validateLogin(String username, String password) {
		System.out.printf("%s%s", username, password);
		User user = userRepository.findByLogin(username, password);
		return user;
	}
	
	public userType getType(User user){
		if(doctorRepository.findByKey(user.getUserId()) != null){
			return userType.DOCTOR;
		}else if(patientRepository.findLatestRevision(user.getUserId()) != null){
			return userType.PATIENT;
		}else if(user.getIsOfficer()){
			return userType.ADMIN;
		}else{
			return userType.STAFF;
		}
	}
	
    @Transactional(readOnly = true)
    public Collection<Patient> getPatients(User user) {
        Collection<Patient> patients;
    	//TODO: add user access logic
    	switch(user.getType()){
    	case DOCTOR:
    	    patients = patientRepository.findAllPatientsForDoctor(user.getUserId());
    	    break;
    	case PATIENT:
    	    patients = new ArrayList<Patient>();
    	    break;
    	case STAFF:
    	    patients = patientRepository.findAllPatientsForStaff(user.getUserId());
    	    break;
    	case ADMIN:
    	    patients = new ArrayList<Patient>();
    	    break;
	    default:
	        patients = new ArrayList<Patient>();
    	}
        
        // Populate list of doctors for a patient.
        for (Patient patient : patients) patientRepository.getDoctorsForPatient(patient);
        
        return new ArrayList<Patient>();
    }
    
    public void populatePatientsForDoctor(Doctor doctor) {
        doctor.patients = patientRepository.findAllPatientsForDoctor(doctor.getUserId());
    }
    
    @Transactional
    public void savePatient(Patient patient) {
    	patientRepository.savePatient(patient);
    }
//	public Set<Visit> doctorVisits(User user){
//		//TODO: verify user is doctor
//	}

	// @Transactional(readOnly = true)
	// public Collection<PetType> findPetTypes() throws DataAccessException {
	// return petRepository.findPetTypes();
	// }
	//
	// @Transactional(readOnly = true)
	// public Owner findOwnerById(int id) throws DataAccessException {
	// return ownerRepository.findById(id);
	// }
	//
	// @Transactional(readOnly = true)
	// public Collection<Owner> findOwnerByLastName(String lastName) throws
	// DataAccessException {
	// return ownerRepository.findByLastName(lastName);
	// }
	//
	// @Transactional
	// public void saveOwner(Owner owner) throws DataAccessException {
	// ownerRepository.save(owner);
	// }
	//
	//
	// @Transactional
	// public void saveVisit(Visit visit) throws DataAccessException {
	// visitRepository.save(visit);
	// }
	//
	//
	//
	// @Transactional(readOnly = true)
	// public Pet findPetById(int id) throws DataAccessException {
	// return petRepository.findById(id);
	// }
	//
	//
	// @Transactional
	// public void savePet(Pet pet) throws DataAccessException {
	// petRepository.save(pet);
	// }
	//
	//
	// @Transactional(readOnly = true)
	// @Cacheable(value = "vets")
	// public Collection<Vet> findVets() throws DataAccessException {
	// return vetRepository.findAll();
	// }

}
