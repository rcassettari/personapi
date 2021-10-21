package br.com.dio.personapi.service;

import br.com.dio.personapi.dto.response.MessageResponseDTO;
import br.com.dio.personapi.dto.request.PersonDTO;
import br.com.dio.personapi.entity.Person;
import br.com.dio.personapi.exception.PersonNotFoundException;
import br.com.dio.personapi.helper.MessageHelper;
import br.com.dio.personapi.mapper.PersonMapper;
import br.com.dio.personapi.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    private MessageHelper messageHelper;

    public PersonDTO createPerson(PersonDTO personDTO)
    {
        Person personToBeSaved = personMapper.toModel(personDTO);
        Person savedPerson = personRepository.save(personToBeSaved);
        return personMapper.toDTO(savedPerson);
    }

    public List<PersonDTO> listAll() {
        List<Person> allPerson = personRepository.findAll();
        return allPerson
                .stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws  PersonNotFoundException {
        Person person = verifyIfExists(id);
        return personMapper.toDTO(person);
    }

    public void delete(Long id) throws PersonNotFoundException {
        verifyIfExists(id);
        personRepository.deleteById(id);
    }

    public PersonDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfExists(id);
        Person personToUpdate = personMapper.toModel(personDTO);
        Person updatedPerson = personRepository.save(personToUpdate);
        return personMapper.toDTO(updatedPerson);
    }

    private MessageResponseDTO createMessageResponseDTO(Long id, String message) {
        return MessageResponseDTO
                .builder()
                .message(message + id)
                .build();
    }

    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }
}

