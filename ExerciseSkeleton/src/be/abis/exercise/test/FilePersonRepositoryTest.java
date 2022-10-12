package be.abis.exercise.test;

import be.abis.exercise.exception.PersonNotFoundException;
import be.abis.exercise.model.Person;
import be.abis.exercise.repository.FilePersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class FilePersonRepositoryTest {

    FilePersonRepository fpr;

    @BeforeEach
    void setUp() {
        fpr = new FilePersonRepository();
    }

    @Test
    void findPersonById() throws PersonNotFoundException {
        assertEquals("Harry", fpr.findPersonById(1).getFirstName());
    }

    @Test
    void findPerson() throws PersonNotFoundException {
        assertEquals("Harry", fpr.findPerson("magicCaster@gmail.uk", "password").getFirstName());
    }

    @Test
    void findPersonThrowsException() throws PersonNotFoundException {
        assertThrows(PersonNotFoundException.class, () -> fpr.findPersonById(10));
    }

    @Test
    void removeJoblessPeople() {
        assertEquals(6, fpr.getPeople().stream().filter(elem -> elem.hasAJob()).count());
    }

    @Test
    void selectByLastNameCharSOrderedByFirstNameMapToLastNameToUpperCase() {
        System.out.println(fpr.getPeople().stream().filter(elem -> elem.getLastName().charAt(0) == 'S').sorted(Comparator.comparing(Person::getFirstName)).map(elem -> elem.getLastName().toUpperCase()).collect(Collectors.toList()));
        assertEquals(4, fpr.getPeople().stream().filter(elem -> elem.getLastName().charAt(0) == 'S').count());
    }

    @Test
    void selectCompaniesOnce() {
        System.out.println(fpr.getPeople().stream().filter(p -> p.hasAJob()).map(p -> p.getCompany().getName()).distinct().collect(Collectors.toList()));
        assertEquals(4, fpr.getPeople().stream().filter(p -> p.hasAJob()).map(p -> p.getCompany().getName()).distinct().count());
    }

    @Test
    void countPeopleWorkingInLeuven() {
        assertEquals(3, fpr.getPeople().stream().filter(p -> p.hasAJob() && p.getCompany().getAddress().getTown().equals("Leuven")).count());
    }

    @Test
    void getYoungestPerson() {
        assertEquals("Peter", fpr.getPeople().stream().sorted(Comparator.comparing(Person::calculateAge)).map(p -> p.getFirstName()).findFirst().orElse(null));
    }

    @Test
    void getFirstOlderThan50() {
        assertEquals("Wayne Johnson", fpr.getPeople().stream().filter(p -> p.calculateAge() > 49).map(Person::toString).findFirst().orElse(null));
    }

    @Test
    void groupByCompany() {
        Map<String, List<Person>> peopleByCompany = fpr.getPeople().stream().filter(Person::hasAJob).collect(Collectors.groupingBy(p -> p.getCompany().getName()));
        assertEquals(2, peopleByCompany.get("Mirko").size());
    }

    @Test
    void countPeoplePerCompany() {
        Map<String, List<Person>> peopleByCompany = fpr.getPeople().stream().filter(Person::hasAJob).collect(Collectors.groupingBy(p -> p.getCompany().getName()));
        for (Map.Entry<String, List<Person>> entry: peopleByCompany.entrySet())
            System.out.println(entry.getValue().size() + " people work at " + entry.getKey());
    }

    @Test
    void averageAgeOfPeople() {
        System.out.println(fpr.getPeople().stream().mapToInt(Person::calculateAge).average().orElse(0));
        assertEquals(68.625, fpr.getPeople().stream().mapToInt(Person::calculateAge).average().orElse(0));
    }

    @Test
    void removeJoblessPeopleWithoutStream() {
        Iterator<Person> it = fpr.getPeople().iterator();
        while(it.hasNext())
            if (!it.next().hasAJob())
                it.remove();
        assertEquals(6, fpr.getPeople().size());
    }

    @Test
    void WriteInFileFilteredPeopleFromFileWorkingForBelgianCompanyAndOlderThan40() {
        String filePath = "C:\\Users\\Duser\\Documents\\personsfiltered.txt";
        fpr.setPeople(fpr.getPeople().stream().filter(Person::hasAJob).filter(p -> p.calculateAge() > 40).filter(p -> p.getCompany().getAddress().getCountry().equals("Belgium")).collect(Collectors.toList()));
        fpr.writeAllPersonsToFile(filePath);
    }
}