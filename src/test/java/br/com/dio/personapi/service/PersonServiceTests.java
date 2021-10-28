package br.com.dio.personapi.service;

import br.com.dio.personapi.dto.response.MessageResponseDTO;
import br.com.dio.personapi.dto.request.PersonDTO;
import br.com.dio.personapi.entity.Person;
import br.com.dio.personapi.helper.MessageHelper;
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

import static br.com.dio.personapi.utils.PersonUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTests {

    private static MessageSource messageSource;

    private static MessageHelper messageHelper;

    @Mock
    private static PersonRepository personRepository;

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
    void testGivenPersonDTOThenReturnSavedMessage() {
        PersonDTO personDTO = createFakeDTO();
        Person expectedSavedPerson = createFakeEntity();
        PersonDTO expectedSavedPersonDTO = createFakeExpectedPersonDTO();

        when(personRepository.save(any(Person.class))).thenReturn(expectedSavedPerson);
        PersonDTO savedPerson = personService.createPerson(personDTO);
        assertEquals(expectedSavedPersonDTO, savedPerson);
    }

    private MessageResponseDTO createExpectedMessageResponse(Long id) {
        return MessageResponseDTO
                .builder()
                .message(messageHelper.getMessage("PersonService.createdPerson") + id)
                .build();
    }
}
