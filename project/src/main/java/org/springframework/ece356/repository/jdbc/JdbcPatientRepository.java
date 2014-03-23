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
package org.springframework.ece356.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.ece356.model.Doctor;
import org.springframework.ece356.model.Patient;
import org.springframework.ece356.model.Specialty;
import org.springframework.ece356.model.User;
import org.springframework.ece356.model.Vet;
import org.springframework.ece356.model.Visit;
import org.springframework.ece356.repository.VisitRepository;
import org.springframework.ece356.util.EntityUtils;
import org.springframework.stereotype.Repository;

/**
 * A simple JDBC-based implementation
 */
@Repository
public class JdbcPatientRepository {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private SimpleJdbcInsert insertUser;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcPatientRepository(DataSource dataSource,
			NamedParameterJdbcTemplate namedParameterJdbcTemplate,
			VisitRepository visitRepository, JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;

		this.insertUser = new SimpleJdbcInsert(dataSource)
				.withTableName("user").usingGeneratedKeyColumns("user_id");

		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	/**
	 * Loads the {@link User} with the supplied <code>id</code>; also loads the
	 * {@link Pet Pets} and {@link Visit Visits} for the corresponding owner, if
	 * not already loaded.
	 */
	public Patient findPatientById(String id) throws DataAccessException {
		Patient patient;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);
			patient = this.namedParameterJdbcTemplate
					.queryForObject(
							"SELECT * "
									+ "FROM user,patient"
									+ " WHERE patient.user_id= :id AND user.user_id=patient.user_id",
							params, ParameterizedBeanPropertyRowMapper
									.newInstance(Patient.class));
		} catch (EmptyResultDataAccessException ex) {
			return null;
			// throw new ObjectRetrievalFailureException(User.class, id);
		}
		return patient;
	}
	
	public Collection<Patient> findAllPatients(User user){
		//TODO: change this to use User limited listing

        List<Patient> patients = new ArrayList<Patient>();
        // Retrieve the list of all vets.
        String max_rev_sql = "SELECT user_id, max(version_number) AS maxrev FROM patient GROUP BY user_id";
        patients.addAll(this.jdbcTemplate.query(
                "SELECT c.*  FROM patient c INNER JOIN ( "+ max_rev_sql+" ) b "
                + "ON c.user_id=b.user_id AND c.version_number=b.maxrev",
                ParameterizedBeanPropertyRowMapper.newInstance(Patient.class)));

        // Retrieve the list of all possible specialties.
//        final List<Doctor> doctors = this.jdbcTemplate.query(
//                "SELECT doctor_account as user_id FROM pati_doct WHERE patient_account = ? and patient_version_number = ?",
//                ParameterizedBeanPropertyRowMapper.newInstance(Doctor.class));

        // Build each vet's list of specialties.
        for (Patient patient : patients) {
            final List<Doctor> secondary_doctors = this.jdbcTemplate.query(
                    "SELECT doctor_account as user_id FROM pati_doct WHERE patient_account = ? and patient_version_number = ?",
                    ParameterizedBeanPropertyRowMapper.newInstance(Doctor.class),
                    patient.getUserId(), patient.getVersionNumber());
            Set<Doctor> docs_set = new HashSet<Doctor>();
            Doctor primary_doctor = new Doctor();
            primary_doctor.setUserId(patient.getDoctorAccount());
            docs_set.add(primary_doctor);
            docs_set.addAll(secondary_doctors);
            patient.setAssignedDoctors(docs_set);
        }
        return patients;
    }

	// public void loadPetsAndVisits(final User owner) {
	// Map<String, Object> params = new HashMap<String, Object>();
	// params.put("id", owner.getId().intValue());
	// final List<JdbcPet> pets = this.namedParameterJdbcTemplate.query(
	// "SELECT id, name, birth_date, type_id, owner_id FROM pets WHERE owner_id=:id",
	// params,
	// new JdbcPetRowMapper()
	// );
	// for (JdbcPet pet : pets) {
	// owner.addPet(pet);
	// pet.setType(EntityUtils.getById(getPetTypes(), PetType.class,
	// pet.getTypeId()));
	// List<Visit> visits = this.visitRepository.findByPetId(pet.getId());
	// for (Visit visit : visits) {
	// pet.addVisit(visit);
	// }
	// }
	// }

	/*
	 * public void save(User user) throws DataAccessException {
	 * BeanPropertySqlParameterSource parameterSource = new
	 * BeanPropertySqlParameterSource(user); if (user.isNew()) { //TODO: not
	 * implemented, manual adding of users // Number newKey =
	 * this.insertUser.executeAndReturnKey(parameterSource); // String name =
	 * "user"; // user.setId(newKey.intValue()); } else {
	 * this.namedParameterJdbcTemplate.update(
	 * "UPDATE user SET password=:password, first_name=:first_name " +
	 * "WHERE user_id=:user_id", parameterSource); } }
	 */

	// public Collection<PetType> getPetTypes() throws DataAccessException {
	// return this.namedParameterJdbcTemplate.query(
	// "SELECT id, name FROM types ORDER BY name", new HashMap<String,
	// Object>(),
	// ParameterizedBeanPropertyRowMapper.newInstance(PetType.class));
	// }

	// /**
	// * Loads the {@link Pet} and {@link Visit} data for the supplied {@link
	// List} of {@link User Users}.
	// *
	// * @param owners the list of owners for whom the pet and visit data should
	// be loaded
	// * @see #loadPetsAndVisits(User)
	// */
	// private void loadUsersPetsAndVisits(List<User> owners) {
	// for (User owner : owners) {
	// loadPetsAndVisits(owner);
	// }
	// }

}
