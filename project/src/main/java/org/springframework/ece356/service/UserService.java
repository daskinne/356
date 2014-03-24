package org.springframework.ece356.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
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
	public Patient findPatientById(String id) {
		return patientRepository.findByKey(id);
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
		}
		//TODO: check Patient table to see if patient
//		return userType.DOCTOR;
//		//TODO: check Staff table to see if staff
//		return userType.STAFF;
//		//else is admin
		return userType.ADMIN;
	}
	
    @Transactional(readOnly = true)
    public Collection<Patient> getPatients(User user) {
    	//TODO: add user access logic
    	switch(user.getType()){
    	case DOCTOR:
    		break;
    	case PATIENT:
    		break;
    	case STAFF:
    		break;
    	case ADMIN:
    		break;
    	}
        return patientRepository.findAllPatients(user);
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
