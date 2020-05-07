package com.ynz.asyncapi;

import com.ynz.asyncapi.entities.Person;
import com.ynz.asyncapi.entities.Phone;
import com.ynz.asyncapi.repositories.PersonRepository;
import com.ynz.asyncapi.repositories.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

		Person person = new Person("Mike");
		person.addPhone(workPhone);
		person.addPhone(privatePhone);

		personRepository.save(person);

	}
}
