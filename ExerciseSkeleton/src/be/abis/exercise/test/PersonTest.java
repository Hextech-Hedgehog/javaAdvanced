package be.abis.exercise.test;

import be.abis.exercise.model.Address;
import be.abis.exercise.model.Company;
import be.abis.exercise.model.Person;
import be.abis.exercise.repository.CompanyRepository;
import be.abis.exercise.repository.FileCompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    Map<String, Person> persons;

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
        persons = new HashMap<>(){{
            put("Harry", new Person("Harry", "Potter", LocalDate.of(1981, 11, 3), "magicCaster@gmail.uk", "password", companies.get(2)));
            put("Peter", new Person("Peter", "Parker", LocalDate.of(1992, 5, 21), "Peter.parker@gmail.com", "spiderman", companies.get(1)));
            put("Wayne", new Person("Wayne", "Johnson", LocalDate.of(1968, 7, 7), "theRock@skynet.com", "WWF", companies.get(3)));
            put("Gwen", new Person("Gwen", "Stacy", LocalDate.of(1992, 2, 18), "greenApple@hotmail.com", "science", companies.get(0)));
            put("Harry", new Person("Harry", "Potter", LocalDate.of(1981, 11, 3), "magicCaster@gmail.uk", "test", companies.get(0)));
        }};
    }

    @Test
    void numberOfPeopleInSetIs4(){
        assertEquals(4, persons.size());
    }

    @Test
    void checkEmail() {
        assertEquals(true, persons.get("Wayne").getEmail().matches("\\b[\\w.%]+@[\\w]+\\.[a-zA-Z]{2,4}\\b"));
    }

    @Test
    void sentenceSplitNumbersWithScanner() {
        String testSentenceWithNumbers2 = "Mir47ko? Could you tell 4me without a d21oubt, that wh10at happened last week was real? Please I need a10n honest 200answer. It means a lot to me81!";
        System.setIn(new ByteArrayInputStream(testSentenceWithNumbers2.getBytes()));
        Scanner sc = new Scanner(System.in);
        String result = sc.nextLine().replaceAll("\\d+", "");
        System.out.println(result);
        assertEquals("Mirko? Could you tell me without a doubt, that what happened last week was real? Please I need an honest answer. It means a lot to me!", result);

    }
} //Mir47ko? Could you tell 4me without a d21oubt, that wh10at happened last week was real? Please I need a10n honest 200answer. It means a lot to me81!