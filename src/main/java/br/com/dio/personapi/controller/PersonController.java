package br.com.dio.personapi.controller;

import br.com.dio.personapi.dto.request.PersonDTO;
import br.com.dio.personapi.exception.PersonNotFoundException;
import br.com.dio.personapi.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/people")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonController {

    private PersonService personService;

    @GetMapping
    public List<PersonDTO> listAll()
    {
        return personService.listAll();
    }

    @GetMapping("/{id}")
    public PersonDTO findById( @PathVariable Long id ) throws PersonNotFoundException
    {
        return personService.findById(id);
    }

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    public PersonDTO createPerson(@RequestBody @Valid PersonDTO personDTO) {
        return personService.createPerson(personDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public void deleteById( @PathVariable Long id ) throws PersonNotFoundException
    {
        personService.delete(id);
    }

    @PutMapping("/{id}")
    public PersonDTO updateById(@PathVariable Long id , @RequestBody @Valid PersonDTO personDTO) throws PersonNotFoundException
    {
        return personService.updateById(id, personDTO);
    }
}
