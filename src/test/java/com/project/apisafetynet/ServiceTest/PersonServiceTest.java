package com.project.apisafetynet.ServiceTest;


import com.project.apisafetynet.Repository.PersonRepository;
import com.project.apisafetynet.Service.PersonServiceImpl;
import com.project.apisafetynet.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


import java.util.ArrayList;
import java.util.Optional;


import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersonServiceTest {

    private Person person;

    private static PersonServiceImpl personService;

    @Mock
    private static PersonRepository personRepository;


    @BeforeEach
    private void setUpBeforeAllTests() {
        personRepository = mock(PersonRepository.class);
        personService = new PersonServiceImpl(personRepository);

    }
    @Test
    public void getPersonsTest() {
        ArrayList<Person> personArrayList = new ArrayList<>();
        personArrayList.add(person);
        when(personRepository.findAll()).thenReturn(personArrayList);
        Iterable<Person> personList = personService.getPersons();
        assertNotNull(personList);
        assertEquals(personArrayList, personList);
    }

    @Test
    public void deletePersonTest() {
        Person person = new Person();
        person.setId("Jack,Jekyll");
        person.setFirstName("Jack");
        person.setLastName("Jekyll");
        person.setAddress("33 rue du test");
        person.setZip("0000");
        person.setPhone("00000000");
        person.setCity("TestVille");
        person.email("Test@PersonTest.com");
        assertNotNull(person);
        personService.deletePerson(person);
        assertNull(person);

    }
    @Test
    public void CreatePersonTest() {
        ArrayList<Person> personArrayList = new ArrayList<>();
        personArrayList.add(person);
        Person person1 = new Person();
        personService.savePerson(person1);
        personArrayList.add(person1);
        assertEquals(personArrayList.size(), 2) ;

    }
    @Test
    public void GetPersonTest() {
        ArrayList<Person> personArrayList = new ArrayList<>();
        personArrayList.add(person);
        Optional<Person> personResponse = personService.getPerson("Jack,Jekyll");
        assertNotNull(personResponse);
        assertEquals(Optional.of(person), personResponse);
    }

}

