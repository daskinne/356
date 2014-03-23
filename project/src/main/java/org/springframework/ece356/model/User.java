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
package org.springframework.ece356.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.core.style.ToStringCreator;

/**
 * Simple JavaBean domain object representing a user.
 */
@Entity
@Table(name = "user")
public class User {
    @Column(name = "user_id")
    @NotEmpty
    private int user_id;

    @Column(name = "username")
    @NotEmpty
    private String username;

    @Column(name = "password")
    @NotEmpty
    private String password;
    
    @Column(name = "first_name")
    @NotEmpty
    private String first_name;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
//    private Set<Pet> pets;

    public int getUserId() {
        return this.user_id;
    }

    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
    public String getFistName() {
        return this.first_name;
    }

 
    public void setUserId(int user_id) {
        this.user_id = user_id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setFistName(String first_name) {
        this.first_name = first_name;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

//    public List<Pet> getPets() {
//        List<Pet> sortedPets = new ArrayList<Pet>(getPetsInternal());
//        PropertyComparator.sort(sortedPets, new MutableSortDefinition("name", true, true));
//        return Collections.unmodifiableList(sortedPets);
//    }

//    public void addPet(Pet pet) {
//        getPetsInternal().add(pet);
//        pet.setOwner(this);
//    }

    /**
     * Return the User with the given id, or null if none found for this Owner.
     */
    public User getUser(int id) {
    	//get by id
        //return getUser(id, false);
    	return null;
    }

    /**
     * Return the User with the given name and password
     *
     * @param name to test
     * @return true if pet name is already in use
     */
    public User getUser(String username, String password) {
        username = username.toLowerCase();
//        for (Pet pet : getPetsInternal()) {
//            if (!ignoreNew || !pet.isNew()) {
//                String compName = pet.getName();
//                compName = compName.toLowerCase();
//                if (compName.equals(name)) {
//                    return pet;
//                }
//            }
//        }
        return null;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("username", this.getUsername())
                .append("userid", this.getUserId())
                .toString();
    }
    
    public boolean isNew() {
        return (this.user_id == 0);
    }
    public int getId(int id) {
        return this.user_id;
    }
    public void setId(int id) {
        this.user_id = id;
    }
}
