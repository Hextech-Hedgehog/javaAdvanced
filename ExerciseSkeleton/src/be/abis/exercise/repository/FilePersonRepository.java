package be.abis.exercise.repository;
import be.abis.exercise.exception.PersonNotFoundException;
import be.abis.exercise.model.Company;
import be.abis.exercise.model.Person;
import org.apache.logging.log4j.LogManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public class FilePersonRepository implements PersonRepository {

    private List<Person> people = new ArrayList<>();
    private static final String filePath = "C:\\Users\\Duser\\Documents\\persons.csv";
    private static final String peopleCompaniesFilePath = "C:\\Users\\Duser\\Documents\\personsCompanies.txt";

    public FilePersonRepository() {
        this.addPeopleFromFile();
    }

    public FilePersonRepository(List<Person> people) {
        this.people = people;
    }

    @Override
    public List<Person> getPeople() {
        return this.people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public List<Person> getPeopleFromFile() {
        List<Person> people = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = "";
            while ((line = br.readLine()) != null)
                people.add(new Person(line.split(";")));
        } catch (IOException e) {
            LogManager.getLogger("error").error(e.getMessage());
        }
        return people;
    }

    public void addPeopleFromFile() {
        this.people.addAll(this.getPeopleFromFile());
    }

    @Override
    public void writeAllPersonsToFile() {
        writeAllPersonsToFile(filePath);
    }

    public void writeAllPersonsToFile(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            StringBuilder strB = new StringBuilder();
            for (Person p: this.people)
                strB.append(p.personData()).append("\n");
            bw.write(strB.toString());
        } catch (IOException e) {
            LogManager.getLogger("error").error(e.getMessage());
        }
    }

    public void writeCompaniesOfPersonFileToFormattedFile() {
        List<Person> people = this.getPeopleFromFile();
        try(PrintWriter pw = new PrintWriter(peopleCompaniesFilePath)) {
            people.stream().filter(Person::hasAJob).map(Person::getCompany).collect(Collectors.groupingBy(c -> c.getAddress().getCountry(), toSet())).forEach((k, v) -> {
                pw.format("%s\n", k);
                v.forEach(c -> pw.format("\t%1$-10s%2$s\n", c.getName(), c.getAddress().getBeautifulAddress()));
            });
        } catch(IOException e) {
            LogManager.getLogger("error").error(e.getMessage());
        }
    }

    @Override
    public Person findPersonById(int id) throws PersonNotFoundException {
        return this.people.stream().filter((elem) -> elem.getPersonNumber().equals("" + id)).findFirst().orElseThrow(() -> new PersonNotFoundException("there is no person with id: " + id));
    }

    @Override
    public Person findPerson(String email, String password) throws PersonNotFoundException {
        return this.people.stream().filter((elem) -> elem.getEmail().equals(email) && elem.getPassword().equals(password)).findFirst().orElseThrow(() -> new PersonNotFoundException("There is no person with email: " + email));
    }
}
