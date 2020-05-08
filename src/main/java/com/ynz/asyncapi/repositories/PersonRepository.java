package com.ynz.asyncapi.repositories;

import com.ynz.asyncapi.entities.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {
    Person findByName(String name);

}
