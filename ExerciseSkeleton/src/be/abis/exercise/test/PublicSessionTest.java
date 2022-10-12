package be.abis.exercise.test;

import be.abis.exercise.model.*;
import be.abis.exercise.repository.FilePersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PublicSessionTest {

    PublicSession ps;

    @BeforeEach
    void setUp() {
        List<Address> addresses = new ArrayList<Address>(){{
            add(new Address("Wall street", "001", "1000AB", "Washington", "Netherlands", "NL"));
            add(new Address("Wayne avenue", "83", "1600", "Gotham city", "USA", "US"));
            add(new Address("metro boulevard", "001", "1000", "Metropolis", "USA", "US"));
            add(new Address("square plazza", "271", "6800", "Lemania", "Belgium", "BE"));
        }};
        List<Company> companies = new ArrayList<Company>(){{
            add(new Company("Mirko", addresses.get(0)));
            add(new Company("Hogwarts", addresses.get(1)));
            add(new Company("Bean", addresses.get(2)));
            add(new Company("Danone", addresses.get(3)));
        }};
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Harry", "Potter", LocalDate.of(1981, 11, 3), "magicCaster@gmail.uk", "password", companies.get(2)));
        persons.add(new Person("Peter", "Parker", LocalDate.of(1992, 5, 21), "Peter.parker@gmail.com", "spiderman", companies.get(1)));
        persons.add(new Person("Wayne", "Johnson", LocalDate.of(1968, 7, 7), "theRock@skynet.com", "WWF", companies.get(3)));
        persons.add(new Person("Gwen", "Stacy", LocalDate.of(1992, 2, 18), "greenApple@hotmail.com", "science", companies.get(0)));
        Course course = Course.JAVA_ADVANCED;
        ps = new PublicSession(course, LocalDate.of(2023, 2, 14), companies.get(1),(Instructor)persons.get(2));
        ps.addEnrolment(persons.get(0), persons.get(1), persons.get(3));
    }

    @Test
    void calculateRevenue() {
        assertEquals("948,00 €", ps.calculateRevenue());
    }

    @Test
    void testToString() {
        assertEquals("La session de Java SE advanced programming sera enseignée à Hogwarts par Wayne Johnson le 14 février 2023", ps.toString("fr"));
    }
}