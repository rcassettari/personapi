package br.com.dio.personapi.service;

import br.com.dio.personapi.dto.response.MessageResponseDTO;
import br.com.dio.personapi.dto.request.PersonDTO;
import br.com.dio.personapi.entity.Person;
import br.com.dio.personapi.exception.PersonNotFoundException;
import br.com.dio.personapi.helper.MessageHelper;
import br.com.dio.personapi.mapper.PersonMapper;
import br.com.dio.personapi.repository.PersonRepository;
import br.com.dio.personapi.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.com.dio.personapi.utils.PersonUtils.*;
import static br.com.dio.personapi.utils.PersonUtils.createFakeExpectedPersonDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTests {

    private static MessageSource messageSource;

    private static MessageHelper messageHelper;

    @Mock
    private static PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private static PersonService personService;

    @BeforeAll
    public static void setUp() throws Exception {
        messageSource = messageSource();
        messageHelper = new MessageHelper( messageSource);
        personService = new PersonServiceImpl(personRepository,messageHelper);
    }

    private static MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Test
    void testGivenPersonDTOThenReturnSavedPerson() {
        PersonDTO personDTO = createFakeDTO();
        Person expectedSavedPerson = createFakeEntity();
        PersonDTO expectedSavedPersonDTO = createFakeExpectedPersonDTO();

        when(personRepository.save(any(Person.class))).thenReturn(expectedSavedPerson);
        PersonDTO savedPerson = personService.createPerson(personDTO);
        assertEquals(expectedSavedPersonDTO, savedPerson);
    }

    @Test
    void testGivenValidPersonIdThenReturnThisPerson() throws PersonNotFoundException {

        PersonDTO personDTO = createFakeDTO();
        Person expectedSavedPerson = createFakeEntity();
        PersonDTO expectedSavedPersonDTO = createFakeExpectedPersonDTO();

        when(personRepository.save(any(Person.class))).thenReturn(expectedSavedPerson);
        PersonDTO savedPerson = personService.createPerson(personDTO);

        PersonDTO expectedPersonDTO = createFakeExpectedPersonDTO();
        expectedPersonDTO.setId(expectedSavedPerson.getId());

        when(personRepository.findById(expectedSavedPerson.getId())).thenReturn(Optional.of(expectedSavedPerson));

        PersonDTO personDTOGetOne = personService.findById(expectedSavedPerson.getId());

        assertEquals(expectedPersonDTO, personDTOGetOne);

        assertEquals(expectedSavedPerson.getId(), personDTOGetOne.getId());
        assertEquals(expectedSavedPerson.getFirstName(), personDTOGetOne.getFirstName());
    }

    @Test
    void testGivenInvalidPersonIdThenThrowException() {
        var invalidPersonId = 1L;
        when(personRepository.findById(invalidPersonId))
                .thenReturn(Optional.ofNullable(any(Person.class)));

        assertThrows(PersonNotFoundException.class, () -> personService.findById(invalidPersonId));
    }

    @Test
    void testGivenNoDataThenReturnAllPeopleRegistered() {
        List<Person> expectedRegisteredPeople = Collections.singletonList(createFakeEntity());
        PersonDTO personDTO = createFakeExpectedPersonDTO();

        when(personRepository.findAll()).thenReturn(expectedRegisteredPeople);

        List<PersonDTO> expectedPeopleDTOList = personService.listAll();

        assertFalse(expectedPeopleDTOList.isEmpty());
        assertEquals(expectedPeopleDTOList.get(0).getId(), personDTO.getId());
    }

    @Test
    void testGivenValidPersonIdAndUpdateInfoThenReturnSuccessOnUpdate() throws PersonNotFoundException {
        var updatedPersonId = 2L;

        PersonDTO updatePersonDTORequest = createFakeExpectedUpdatedPersonDTO();
        updatePersonDTORequest.setId(updatedPersonId);
        updatePersonDTORequest.setLastName("Ricardo updated");

        Person expectedPersonToUpdate = createFakeEntity();
        expectedPersonToUpdate.setId(updatedPersonId);

        Person expectedPersonUpdated = createFakeExpectedUpdatedPerson();
        expectedPersonUpdated.setId(updatedPersonId);
        expectedPersonUpdated.setLastName(updatePersonDTORequest.getLastName());

        when(personRepository.findById(updatedPersonId)).thenReturn(Optional.of(expectedPersonUpdated));
        lenient().when(personMapper.toModel(updatePersonDTORequest)).thenReturn(expectedPersonUpdated);
        when(personRepository.save(any(Person.class))).thenReturn(expectedPersonUpdated);

        PersonDTO updatedPersonDTO = personService.updateById(updatedPersonId, updatePersonDTORequest);

        assertEquals(updatePersonDTORequest, updatedPersonDTO);
    }

    @Test
    void testGivenInvalidPersonIdAndUpdateInfoThenThrowExceptionOnUpdate() throws PersonNotFoundException {
        var invalidPersonId = 1L;

        PersonDTO updatePersonDTORequest = createFakeDTO();
        updatePersonDTORequest.setId(invalidPersonId);
        updatePersonDTORequest.setLastName("Ricardo updated");

        when(personRepository.findById(invalidPersonId))
                .thenReturn(Optional.ofNullable(any(Person.class)));

        assertThrows(PersonNotFoundException.class, () -> personService.updateById(invalidPersonId, updatePersonDTORequest));
    }

    @Test
    void testGivenValidPersonIdThenReturnSuccessOnDelete() throws PersonNotFoundException {
        var deletedPersonId = 1L;
        Person expectedPersonToDelete = createFakeEntity();

        when(personRepository.findById(deletedPersonId)).thenReturn(Optional.of(expectedPersonToDelete));
        personService.delete(deletedPersonId);

        verify(personRepository, times(1)).deleteById(deletedPersonId);
    }

    @Test
    void testGivenInvalidPersonIdThenThrowExceptionOnDelete() throws PersonNotFoundException {
        var invalidPersonId = 1L;

        when(personRepository.findById(invalidPersonId))
                .thenReturn(Optional.ofNullable(any(Person.class)));

        assertThrows(PersonNotFoundException.class, () -> personService.delete(invalidPersonId));
    }

    private MessageResponseDTO createExpectedMessageResponse(Long id) {
        return MessageResponseDTO
                .builder()
                .message(messageHelper.getMessage("PersonService.createdPerson") + id)
                .build();
    }
}
