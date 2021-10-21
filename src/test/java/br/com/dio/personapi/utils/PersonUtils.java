package br.com.dio.personapi.utils;

import br.com.dio.personapi.dto.request.PersonDTO;
import br.com.dio.personapi.entity.Person;

import java.time.LocalDate;
import java.util.Collections;

public class PersonUtils {

    private static final String FIRST_NAME = "Abner";
    private static final String LAST_NAME = "Silva";
    private static final String CPF_NUMBER = "605.869.413-20";
    private static final long PERSON_ID = 1L;
    public static final LocalDate BIRTH_DATE = LocalDate.of(2010, 10, 1);

    public static PersonDTO createFakeDTO() {
        return PersonDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .cpf(CPF_NUMBER)
                .birthDate(LocalDate.parse("2010-10-01"))
                .phones(Collections.singletonList(PhoneUtils.createFakeDTO()))
                .build();
    }

    public static PersonDTO createFakeExpectedPersonDTO() {
        return PersonDTO.builder()
                .id(PERSON_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .cpf(CPF_NUMBER)
                .birthDate(LocalDate.parse("2010-10-01"))
                .phones(Collections.singletonList(PhoneUtils.createExpectedPhoneDTO()))
                .build();
    }

    public static Person createFakeEntity() {
        return Person.builder()
                .id(PERSON_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .cpf(CPF_NUMBER)
                .birthDate(BIRTH_DATE)
                .phones(Collections.singletonList(PhoneUtils.createFakeEntity()))
                .build();
    }
}
