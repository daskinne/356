package org.springframework.ece356.repository.jdbc;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ece356.model.Treatment;
import org.springframework.ece356.model.Visit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTreatmentRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUser;

    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public JdbcTreatmentRepository(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("user")
                .usingGeneratedKeyColumns("user_id");

        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    
    public Treatment findByKey(String visit_appointment_patient_account,
    		int visit_appointment_version_number, DateTime visit_appointment_start_time) throws DataAccessException {
        Treatment treatment;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("visit_appointment_patient_account", visit_appointment_patient_account);;
            params.put("visit_appointment_version_number", visit_appointment_version_number);
            params.put("visit_appointment_start_time", visit_appointment_start_time);
            treatment = this.namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM appointment WHERE "
                    + "visit_appointment_patient_account=:visit_appointment_patient_account, "
                    + "visit_appointment_version_number=:visit_appointment_version_number, "
                    + "visit_appointment_start_time=:visit_appointment_start_time",
                    params,
                    ParameterizedBeanPropertyRowMapper.newInstance(Treatment.class)
            );
        } catch (EmptyResultDataAccessException ex) {
        	return null;
//            throw new ObjectRetrievalFailureException(User.class, id);
        }
        return treatment;
    }

    public void addTreatment(Treatment new_treatment){
    	BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(new_treatment);
    	this.namedParameterJdbcTemplate.update(
                "INSERT INTO treatment (visit_appointment_patient_account, "
                + "visit_appointment_version_number, visit_appointment_start_time, start_time, end_time, procedure) " +
                "VALUES (:visit_appointment_patient_account, "
                + ":visit_appointment_version_number, :visit_appointment_start_time, :start_time, :end_time, :procedure)",
                parameterSource);
    }
}
