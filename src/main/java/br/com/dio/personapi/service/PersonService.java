package br.com.dio.personapi.service;

import br.com.dio.personapi.dto.MessageResponseDTO;
import br.com.dio.personapi.entity.Person;
import br.com.dio.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository)
    {
        this.personRepository = personRepository;
    }

    public MessageResponseDTO createPerson(Person person)
    {
        Person savedPerson = personRepository.save(person);
        return MessageResponseDTO
                .builder()
                .message("Created person with ID:" + savedPerson.getId())
                .build();
    }

}
