package br.com.dio.personapi.service;

import br.com.dio.personapi.dto.MessageResponseDTO;
import br.com.dio.personapi.dto.request.PersonDTO;
import br.com.dio.personapi.entity.Person;
import br.com.dio.personapi.mapper.PersonMapper;
import br.com.dio.personapi.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    public MessageResponseDTO createPerson(PersonDTO personDTO)
    {
        Person personToBeSaved = personMapper.toModel(personDTO);

        Person savedPerson = personRepository.save(personToBeSaved);
        return MessageResponseDTO
                .builder()
                .message("Created person with ID:" + savedPerson.getId())
                .build();
    }

}

