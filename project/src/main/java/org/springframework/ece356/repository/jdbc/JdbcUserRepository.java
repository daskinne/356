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

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.ece356.model.User;
import org.springframework.ece356.model.Visit;
import org.springframework.ece356.repository.VisitRepository;
import org.springframework.stereotype.Repository;

/**
 * A simple JDBC-based implementation

 */
@Repository
public class JdbcUserRepository {

    private VisitRepository visitRepository;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                   VisitRepository visitRepository) {

        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("user")
                .usingGeneratedKeyColumns("user_id");

        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        this.visitRepository = visitRepository;
    }

    /**
     * Loads the {@link User} with the supplied <code>id</code>; also loads the {@link Pet Pets} and {@link VisitOld Visits}
     * for the corresponding owner, if not already loaded.
     */
    public User findByKey(int id) throws DataAccessException {
        User user;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", id);
            user = this.namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM user WHERE user_id= :id",
                    params,
                    ParameterizedBeanPropertyRowMapper.newInstance(User.class)
            );
        } catch (EmptyResultDataAccessException ex) {
        	return null;
//            throw new ObjectRetrievalFailureException(User.class, id);
        }
        return user;
    }

    public User findByLogin(String username, String password) throws DataAccessException {
        User user;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("username", username);
            params.put("password", password);
            user = this.namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM user WHERE user_id= :username and password = :password",
                    params,
                    ParameterizedBeanPropertyRowMapper.newInstance(User.class)
            );
        } catch (EmptyResultDataAccessException ex) {
        	//TODO: ensure the user is notified this was invalid credentials
        	return null;
        }
        return user;
    }

//    public void loadPetsAndVisits(final User owner) {
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("id", owner.getId().intValue());
//        final List<JdbcPet> pets = this.namedParameterJdbcTemplate.query(
//                "SELECT id, name, birth_date, type_id, owner_id FROM pets WHERE owner_id=:id",
//                params,
//                new JdbcPetRowMapper()
//        );
//        for (JdbcPet pet : pets) {
//            owner.addPet(pet);
//            pet.setType(EntityUtils.getById(getPetTypes(), PetType.class, pet.getTypeId()));
//            List<Visit> visits = this.visitRepository.findByPetId(pet.getId());
//            for (Visit visit : visits) {
//                pet.addVisit(visit);
//            }
//        }
//    }


    public void save(User user) throws DataAccessException {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
        	//TODO: not implemented, manual adding of users
			// Number newKey = this.insertUser.executeAndReturnKey(parameterSource);
			// String name = "user";
			// user.setId(newKey.intValue());
        } else {
            this.namedParameterJdbcTemplate.update(
                    "UPDATE user SET password=:password, first_name=:first_name " +
                            "WHERE user_id=:user_id",
                    parameterSource);
        }
    }

//    public Collection<PetType> getPetTypes() throws DataAccessException {
//        return this.namedParameterJdbcTemplate.query(
//                "SELECT id, name FROM types ORDER BY name", new HashMap<String, Object>(),
//                ParameterizedBeanPropertyRowMapper.newInstance(PetType.class));
//    }

//    /**
//     * Loads the {@link Pet} and {@link Visit} data for the supplied {@link List} of {@link User Users}.
//     *
//     * @param owners the list of owners for whom the pet and visit data should be loaded
//     * @see #loadPetsAndVisits(User)
//     */
//    private void loadUsersPetsAndVisits(List<User> owners) {
//        for (User owner : owners) {
//            loadPetsAndVisits(owner);
//        }
//    }


}
