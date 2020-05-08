package com.ynz.asyncapi;

import com.ynz.asyncapi.entities.Person;
import com.ynz.asyncapi.entities.Phone;
import com.ynz.asyncapi.repositories.PersonRepository;
import com.ynz.asyncapi.repositories.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.stream.Stream;

@SpringBootApplication
public class AsyncApiApplication implements CommandLineRunner {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    public static void main(String[] args) {
        SpringApplication.run(AsyncApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Phone workPhone = new Phone("90009876");
        Phone privatePhone = new Phone("28280011");

        Person p1 = new Person("Mike");
        p1.addPhone(workPhone);
        p1.addPhone(privatePhone);

        Person p2 = new Person(("John Carnell"));
        Stream.of(new Phone("12345678"), new Phone("55556666"), new Phone("67891213"))
                .forEach(ph -> p2.addPhone(ph));

        personRepository.save(p1);
        personRepository.save(p2);


    }
}
