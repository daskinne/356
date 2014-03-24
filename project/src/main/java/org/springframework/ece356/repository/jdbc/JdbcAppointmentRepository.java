package org.springframework.ece356.repository.jdbc;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ece356.model.Appointment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcAppointmentRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUser;

    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public JdbcAppointmentRepository(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("user")
                .usingGeneratedKeyColumns("user_id");

        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    
    public Appointment findByKey(String patient_account, int patient_version_number, 
    		int version_number, DateTime start_time) throws DataAccessException {
    	Appointment user;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("patient_account", patient_account);
            params.put("patient_version_number", patient_version_number);
            params.put("version_number", version_number);
            params.put("start_time", start_time);
            user = this.namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM appointment WHERE "
                    + "patient_account=:patient_account, "
                    + "patient_version_number=:patient_version_number"
                    + "version_number=:version_number"
                    + "start_time=:start_time, ",
                    params,
                    ParameterizedBeanPropertyRowMapper.newInstance(Appointment.class)
            );
        } catch (EmptyResultDataAccessException ex) {
        	return null;
//            throw new ObjectRetrievalFailureException(User.class, id);
        }
        return user;
    }

    public void addAppointment(Appointment new_appointment){
    	BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(new_appointment);
    	this.namedParameterJdbcTemplate.update(
                "INSERT INTO appointment (patient_account, patient_version_number, doctor_account, version_number, start_time, end_time) " +
                "VALUES (:patient_account, :patient_version_number, :doctor_account, :version_number, :start_time, :end_time)",
                parameterSource);
    }
}
