package org.company.project.model.service;

import org.company.project.model.domain.Person;
import org.company.project.model.repository.PersonRepository;

public class PersonService {
    private static final PersonService PERSON_SERVICE = new PersonService();
    private PersonService() {}

    public static PersonService getInstance() {
        return PERSON_SERVICE;
    }
    public void  save (Person person) throws Exception {
        try (PersonRepository personRepository = new PersonRepository()){
            personRepository.insert(person);
            personRepository.commit();
        }
    }
}
