package org.springframework.ece356.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.ece356.model.Appointment;
import org.springframework.ece356.model.Doctor;
import org.springframework.ece356.model.Patient;
import org.springframework.ece356.model.Prescription;
import org.springframework.ece356.model.User;
import org.springframework.ece356.model.Visit;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcDoctorRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUser;

    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public JdbcDoctorRepository(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("user")
                .usingGeneratedKeyColumns("user_id");

        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    public Doctor findByKey(String id) throws DataAccessException {
        Doctor user;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", id);
            user = this.namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM doctor WHERE user_id= :id",
                    params,
                    ParameterizedBeanPropertyRowMapper.newInstance(Doctor.class)
            );
        } catch (EmptyResultDataAccessException ex) {
        	return null;
//            throw new ObjectRetrievalFailureException(User.class, id);
        }
        return user;
    }
    
    public List<Prescription> validPrescriptions(String user_id) {
        final List<Prescription> prescriptions = this.jdbcTemplate.query(
                "SELECT legal_prescription_medication as medication FROM doct_lega WHERE doctor_user= ?",
                new ParameterizedRowMapper<Prescription>() {
                    @Override
                    public Prescription mapRow(ResultSet rs, int row) throws SQLException {
                        Prescription p = new Prescription();
                        p.setMedication(rs.getInt("medication"));
                        return p;
                    }
                },
                user_id);
        return prescriptions;
    }

    //TODO: add doctor to patient
//	public void addDoctorPatient(Doctor doctor, Patient patient) throws DataAccessException {
//        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(doctor);
//        if (this.findById(doctor.getUserId()) == null) {
//        	addDoctor(doctor);
//        	//TODO: not implemented, manual adding of doctors from User
//        } else {
//            this.namedParameterJdbcTemplate.update(
//                    "UPDATE doctor SET user_id=:user_id, pay_rate=:pay_rate " +
//                            "WHERE user_id=:user_id",
//                    parameterSource);
//        }
//    }

    public void addDoctor(Doctor new_doctor){
    	BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(new_doctor);
    	this.namedParameterJdbcTemplate.update(
                "INSERT INTO doctor (user_id, pay_rate) VALUES (:user_id, :pay_rate)",
                parameterSource);
    }
    
    public void save(Doctor doctor) throws DataAccessException {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(doctor);
        if (this.findByKey(doctor.getUserId()) == null) {
        	addDoctor(doctor);
        	//TODO: not implemented, manual adding of doctors from User
        } else {
            this.namedParameterJdbcTemplate.update(
                    "UPDATE doctor SET user_id=:user_id, pay_rate=:pay_rate " +
                            "WHERE user_id=:user_id",
                    parameterSource);
        }
    }
    
    
    // TODO:
    public Collection<Appointment> findAllAppointmentsForDoctor(String user_id) {
//        List<Appointment> appointments = new ArrayList<Appointment>();
//        String appointment_sql = "SELECT patient_account, doctor_account, version_number, start_time FROM appointment WHERE doctor_account = '"
//                + user_id + "'";
//        String max_rev_sql = "(SELECT "
//                + "patient_account, doctor_account, max(version_number), start_time "
//                + "AS maxrev FROM patient where doctor_account = '"
//                + user_id
//                + "' GROUP BY patient_account, doctor_account, start_time) UNION DISTINCT ("
//                + appointment_sql + ")";
//        String main_query = "(SELECT c.*  FROM patient c INNER JOIN ( "
//                + max_rev_sql
//                + " ) b "
//                + "ON c.user_id=b.user_id AND c.version_number=b.maxrev) UNION DISTINCT ("
//                + appointment_sql + ")";
//        
//        patients.addAll(this.jdbcTemplate.query(main_query,
//                ParameterizedBeanPropertyRowMapper.newInstance(Patient.class)));
//        
//        return patients;
        return null;
    }
    //TODO:


}
