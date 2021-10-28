package br.com.dio.personapi.service;

import br.com.dio.personapi.dto.request.PersonDTO;
import br.com.dio.personapi.exception.PersonNotFoundException;

import java.util.List;

public interface PersonService {

    public PersonDTO createPerson(PersonDTO personDTO);

    public List<PersonDTO> listAll();

    public PersonDTO findById(Long id) throws  PersonNotFoundException;

    public void delete(Long id) throws PersonNotFoundException;

    public PersonDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException;
}

