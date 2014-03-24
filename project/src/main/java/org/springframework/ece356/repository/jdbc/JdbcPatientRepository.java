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
import org.springframework.ece356.model.Doctor;
import org.springframework.ece356.model.Patient;
import org.springframework.ece356.model.Pet;
import org.springframework.ece356.model.User;
import org.springframework.ece356.model.Visit;
import org.springframework.ece356.repository.VisitRepository;
import org.springframework.ece356.util.userType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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

    public Patient findByKey(String user_id, int version_number) throws DataAccessException {
        Patient user;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("user_id", user_id);
            params.put("version_number", version_number);
            user = this.namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM patient WHERE "
                    + "user_id=:user_id, "
                    + "version_number=:version_number",
                    params,
                    ParameterizedBeanPropertyRowMapper.newInstance(Patient.class)
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
//            throw new ObjectRetrievalFailureException(User.class, id);
        }
        return user;
    }

	
	/**
	 * Loads the {@link User} with the supplied <code>id</code>; also loads the
	 * {@link Pet Pets} and {@link Visit Visits} for the corresponding owner, if
	 * not already loaded.
	 */
	public Patient findLatestRevision(String id) throws DataAccessException {
		Patient patient;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);
			
			String max_rev_sql = "(SELECT version_number "
									+ "FROM patient "
									+ "WHERE patient.user_id=:id)";
			patient = this.namedParameterJdbcTemplate
					.queryForObject(
							"SELECT * "
									+ "FROM user,patient"
									+ " WHERE patient.user_id=:id AND user.user_id=patient.user_id" 
									+ " AND version_number >= all" + max_rev_sql,
							params, ParameterizedBeanPropertyRowMapper
									.newInstance(Patient.class));
			
		} catch (EmptyResultDataAccessException ex) {
			return null;
			// throw new ObjectRetrievalFailureException(User.class, id);
		}
		return patient;
	}
	
	public Patient getDoctorsForPatient(Patient patient) {
        final List<Doctor> secondary_doctors = this.jdbcTemplate
                .query("SELECT doctor_account as user_id FROM pati_doct WHERE patient_account = ? and patient_version_number = ?",
                        ParameterizedBeanPropertyRowMapper
                                .newInstance(Doctor.class), patient
                                .getUserId(), patient.getVersionNumber());
        Set<Doctor> docs_set = new HashSet<Doctor>();
        Doctor primary_doctor = new Doctor();
        primary_doctor.setUserId(patient.getDoctorAccount());
        docs_set.add(primary_doctor);
        docs_set.addAll(secondary_doctors);
        patient.setAssignedDoctors(docs_set);
        return patient;
	}
	   
    public Collection<Patient> findAllPatientsForDoctor(User user) {
        List<Patient> patients = new ArrayList<Patient>();
        String doctor_patient_sql = "SELECT patient_account, patient_version_number FROM pati_doct WHERE doctor_account = '"
                + user.getUserId() + "'";
        String max_rev_sql = "(SELECT user_id, max(version_number) AS maxrev FROM patient where doctor_account = '"
                + user.getUserId()
                + "' GROUP BY user_id) UNION DIST ("
                + doctor_patient_sql + ")";
        String main_query = "(SELECT c.*  FROM patient c INNER JOIN ( "
                + max_rev_sql
                + " ) b "
                + "ON c.user_id=b.user_id AND c.version_number=b.maxrev) UNION DISTINCT ("
                + doctor_patient_sql + ")";
        
        patients.addAll(this.jdbcTemplate.query(main_query,
                ParameterizedBeanPropertyRowMapper.newInstance(Patient.class)));
        
        // Populate list of doctors for a patient.
        for (Patient patient : patients) getDoctorsForPatient(patient);
        
        return patients;
    }
	
    public Collection<Patient> findAllPatientsForStaff(User user) {
        List<Patient> patients = new ArrayList<Patient>();
        String user_doct_sql = "SELECT doctor_account FROM user_doct WHERE user_id = '"
                + user.getUserId() + "'";
        String max_rev_sql = "SELECT user_id, max(version_number) AS maxrev FROM patient where doctor_account in ("
                + user_doct_sql + ") GROUP BY user_id";
        String main_query = "SELECT c.*  FROM patient c INNER JOIN ( "
                + max_rev_sql + " ) b "
                + "ON c.user_id=b.user_id AND c.version_number=b.maxrev";
        System.out.println(main_query);
        
        patients.addAll(this.jdbcTemplate.query(main_query,
                ParameterizedBeanPropertyRowMapper.newInstance(Patient.class)));
        
        // Populate list of doctors for a patient.
        for (Patient patient : patients) getDoctorsForPatient(patient);
        
        return patients;
    }
}
