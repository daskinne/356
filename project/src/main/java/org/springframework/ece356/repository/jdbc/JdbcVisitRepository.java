package org.springframework.ece356.repository.jdbc;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ece356.model.Appointment;
import org.springframework.ece356.model.Treatment;
import org.springframework.ece356.model.Visit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcVisitRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUser;

    private JdbcTemplate jdbcTemplate;
    
    private JdbcTreatmentRepository treatmentRepo;
    
    @Autowired
    public JdbcVisitRepository(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            JdbcTreatmentRepository treatmentRepo) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("user")
                .usingGeneratedKeyColumns("user_id");

        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        
        this.treatmentRepo = treatmentRepo;
    }
    
    // TODO: Move this to service layer?
    public Treatment findTreatmentForVisit(Visit visit) {
        return treatmentRepo.findByKey(
                visit.getAppointmentPatientAccount(),
                visit.getAppointmentVersionNumber(),
                visit.getAppointmentStartTime());
    }
    
    public Visit findByKey(String appointment_patient_account, int appointment_version_number, 
            DateTime appointment_start_time) throws DataAccessException {
        Visit visit;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("appointment_patient_account", appointment_patient_account);
            params.put("appointment_version_number", appointment_version_number);
            params.put("appointment_start_time", appointment_start_time);
            visit = this.namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM appointment WHERE "
                    + "appointment_patient_account=:appointment_patient_account, "
                    + "appointment_version_number=:appointment_version_number, "
                    + "appointment_start_time=:appointment_start_time",
                    params,
                    ParameterizedBeanPropertyRowMapper.newInstance(Visit.class)
            );
        } catch (EmptyResultDataAccessException ex) {
        	return null;
//            throw new ObjectRetrievalFailureException(User.class, id);
        }
        return visit;
    }

    public void addVisit(Visit new_visit){
    	BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(new_visit);
    	this.namedParameterJdbcTemplate.update(
                "INSERT INTO appointment (appointment_patient_account, "
                + "appointment_version_number, appointment_start_time, diagnosis, diagnostic_procedure, doctor_comments) " +
                "VALUES (:appointment_patient_account, "
                + ":appointment_version_number, :appointment_start_time, :diagnosis, :diagnostic_procedure, :doctor_comments)",
                parameterSource);
    }
}
