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
package org.springframework.ece356.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ece356.model.User;
import org.springframework.ece356.repository.jdbc.JdbcDoctorRepository;
import org.springframework.ece356.repository.jdbc.JdbcUserRepository;
import org.springframework.ece356.util.userType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
	private JdbcUserRepository userRepository;
	private JdbcDoctorRepository doctorRepository;

	@Autowired
	public UserService(JdbcUserRepository userRepository, JdbcDoctorRepository doc) {
		this.userRepository = userRepository;
		this.doctorRepository = doc;
	}

	@Transactional(readOnly = true)
	public User findUserById(int id) {
		return userRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public User validateLogin(String username, String password) {
		System.out.printf("%s%s", username, password);
		User user = userRepository.findByLogin(username, password);
		return user;
	}
	
	public userType getType(User user){
		if(doctorRepository.findById(user.getUserId()) != null){
			return userType.DOCTOR;
		}
		//TODO: check Patient table to see if patient
//		return userType.DOCTOR;
//		//TODO: check Staff table to see if staff
//		return userType.STAFF;
//		//else is admin
		return userType.ADMIN;
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
