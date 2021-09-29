package com.project.apisafetynet.ServiceTest;


import com.project.apisafetynet.Repository.PersonRepository;
import com.project.apisafetynet.Service.PersonServiceImpl;
import com.project.apisafetynet.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    private Person person ;
    private List<Person> personArrayList = new ArrayList<>();


    @Mock
    private PersonRepository personRepository;
    @InjectMocks
    private PersonServiceImpl personService;


    @BeforeEach
    private void setUpBeforeAllTests() {
        person =  new Person();
        person.setId("Jack,Jekyll");
        person.setFirstName("Jack");
        person.setLastName("Jekyll");
        person.setAddress("33 rue du test");
        person.setZip("0000");
        person.setPhone("00000000");
        person.setCity("TestVille");
        person.email("Test@PersonTest.com");
        personArrayList.add(person);

        lenient().when(personRepository.findAll()).thenReturn(personArrayList);
        //@FIXME this mock have to be corrected
        lenient().when(personRepository.save(person)).thenCallRealMethod();

    }
    @Test
    public void getPersonsTest() {
        Iterable<Person> personList = personService.getPersons();
        assertNotNull(personList);
        assertEquals(personArrayList, personList);
    }

    @Test
    public void CreatePersonTest() {

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
    public void GetPersonTest() {
        ArrayList<Person> personArrayList = new ArrayList<>();
        personArrayList.add(person);
        Optional<Person> personResponse = personService.getPerson("Jack,Jekyll");
        assertNotNull(personResponse);
        assertEquals(Optional.of(person), personResponse);
    }

}
