package be.abis.exercise.main;

import be.abis.exercise.exception.PersonNotFoundException;
import be.abis.exercise.model.*;
import be.abis.exercise.repository.FilePersonRepository;
import org.apache.logging.log4j.LogManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        /*List<Address> addresses = new ArrayList<Address>(){{
            add(new Address("Wall street", "001", "1000AB", "Leuven", "Belgium", "BE"));
            add(new Address("Wayne avenue", "83", "1600", "Gotham city", "USA", "US"));
            add(new Address("metro boulevard", "001", "1000", "Metropolis", "USA", "US"));
            add(new Address("square plazza", "271", "6800", "Leuven", "Belgium", "BE"));
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
        persons.add(new Person("Wayne", "Johnson", LocalDate.of(1932, 7, 7), "theRock@skynet.com", "WWF", companies.get(3)));
        persons.add(new Person("Gwen", "Stacy", LocalDate.of(1992, 2, 18), "greenApple@hotmail.com", "science", companies.get(0)));
        persons.add(new Person("Homeless", "Emperor", LocalDate.of(1974, 3, 5), "hoboking@hotmail.com", "god"));
        persons.add(new Person("Hans", "Stark", LocalDate.of(1974, 3, 5), "stark@outlook.com", "howling"));
        persons.add(new Person("Selene", "Starlight", LocalDate.of(1807, 3, 5), "pleiades@skynet.jp", "sunnyday", companies.get(0)));
        persons.add(new Person("Xion", "Sekeu", LocalDate.of(1974, 3, 5), "gamingadvice@gmail.com", "ftl", companies.get(1)));*/
        FilePersonRepository fpr = new FilePersonRepository();
        //fpr.writeAllPersonsToFile();
        /*Course course = Course.JAVA_ADVANCED;
        PublicSession ps = new PublicSession(course, LocalDate.of(2023, 2, 14), companies.get(1),(Instructor)persons.get(2));
        ps.addEnrolment(persons.get(0), persons.get(1), persons.get(3));
        ps.printListOfParticipants();
        System.out.println(ps.calculateRevenue());
        try {
            fpr.findPersonById(11);
        }
        catch (PersonNotFoundException e) {
            LogManager.getLogger("error").error(e.getMessage());
        }*/
        fpr.writeCompaniesOfPersonFileToFormattedFile();
    }

}
