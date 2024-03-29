package org.springframework.ece356.repository.jdbc;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ece356.model.Appointment;
import org.springframework.ece356.model.Patient;
import org.springframework.ece356.model.Visit;
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
    
    private JdbcVisitRepository visitRepo;
    
    @Autowired
    public JdbcAppointmentRepository(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            JdbcVisitRepository visitRepo) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("user")
                .usingGeneratedKeyColumns("user_id");

        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        
        this.visitRepo = visitRepo;
    }
    
    // TODO: Move this to service layer?
    public Visit findVisitForAppointment(Appointment appointment) {
        return visitRepo.findByKey(
                appointment.getPatientAccount(),
                appointment.getVersionNumber(),
                appointment.getStartTime());
    }
    
    public Appointment findByKey(String patient_account, int version_number, DateTime start_time) throws DataAccessException {
    	Appointment user;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("patient_account", patient_account);
            params.put("version_number", version_number);
            params.put("start_time", start_time);
            user = this.namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM appointment WHERE "
                    + "patient_account=:patient_account, "
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
    
    public Collection<Appointment> findLatestByKey(String patient_account) {
    	Collection<Appointment> appointments = new ArrayList<Appointment>();
    	
    	try {
	    	String naturalJoinThis = "(SELECT patient_account, start_time, max(version_number) "
	    			+ "FROM appointment "
	    			+ "WHERE patient_account=" + patient_account +") maxresult ";
	    	
	    	appointments.addAll(this.jdbcTemplate.query("SELECT * "
	    			+ "FROM appointment, " + naturalJoinThis
	    			+ "WHERE appointment.patient_account=maxresult.patient_account "
	    			+ 	"AND appointment.start_time=maxresult.start_time "
	    			+ 	"AND appointment.version_number=maxresult.version_number",
	                ParameterizedBeanPropertyRowMapper.newInstance(Appointment.class)));
	    	for (Appointment a: appointments)
	    		System.out.println(a);
	    	
    	} catch (EmptyResultDataAccessException ex) {
    		// TODO: handle this case
        	return null;
//            throw new ObjectRetrievalFailureException(User.class, id);
        }	
    	return appointments;
    }

    public void addAppointment(Appointment new_appointment){
    	BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(new_appointment);    	
    	this.namedParameterJdbcTemplate.update(
                "INSERT INTO appointment (patient_account, doctor_account, version_number, start_time, end_time) " +
                "VALUES (:patient_account, :doctor_account, :version_number, :start_time, :end_time)",
                parameterSource);
    }
}
