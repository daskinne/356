package org.springframework.ece356.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ece356.model.Patient;
import org.springframework.ece356.model.User;
import org.springframework.ece356.repository.jdbc.JdbcDoctorRepository;
import org.springframework.ece356.repository.jdbc.JdbcPatientRepository;
import org.springframework.ece356.repository.jdbc.JdbcUserRepository;
import org.springframework.ece356.util.userType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientService {
	private JdbcPatientRepository patientRepository;

	@Autowired
	public PatientService(JdbcPatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}

	@Transactional(readOnly = true)
	public Patient findPatientById(String id) {
		return patientRepository.findPatientById(id);
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
