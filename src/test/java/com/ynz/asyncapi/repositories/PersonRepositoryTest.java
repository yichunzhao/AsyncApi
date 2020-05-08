package com.ynz.asyncapi.repositories;

import com.ynz.asyncapi.entities.Person;
import com.ynz.asyncapi.entities.Phone;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @DataJpaTest provides some standard setup needed for testing the persistence layer:
 * <ul>
 * <li>configuring H2, an in-memory database</li>
 * <li>setting Hibernate, Spring Data, and the DataSource</li>
 * <li>performing an @EntityScan</li>
 * <li>turning on SQL logging</li>
 * </ul>
 * <p>
 *  SpringExtension integrates the Spring TestContext Framework into JUnit 5's Jupiter programming model.
 */

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Slf4j
class PersonRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void whenFindByName_thenReturnPerson() {
        log.info("whenFindByName_thenReturnPerson");
        //given
        Person testPerson = new Person();
        String name = "Erick Freeman";
        testPerson.setName(name);
        testPerson.addPhone(new Phone("90009000"));
        testPerson.addPhone(new Phone("10002000"));

        entityManager.persist(testPerson);
        entityManager.flush();

        //when
        Person found = personRepository.findByName(name);

        //then
        assertThat(found.getName(), is(name));
        assertThat(found.getPhones(), hasSize(2));
    }

    @Test
    public void whenDeletePersonByName_thenCannotFindIt() {
        log.info("whenDeletePersonByName_thenCannotFindIt");
        //given
        Person testPerson = new Person();
        String name = "Erick Freeman";
        testPerson.setName(name);
        testPerson.addPhone(new Phone("90009000"));
        testPerson.addPhone(new Phone("10002000"));

        Person persisted = entityManager.persist(testPerson);
        entityManager.flush();

        //when
        personRepository.delete(persisted);

        //Person is not found
        Person found = entityManager.find(Person.class, persisted.getId());
        assertNull(found);

        //while phone is still kept.
        persisted.getPhones().stream().map(phone -> phone.getId()).
                forEach(id -> assertNotNull(entityManager.find(Phone.class, id)));
    }


}