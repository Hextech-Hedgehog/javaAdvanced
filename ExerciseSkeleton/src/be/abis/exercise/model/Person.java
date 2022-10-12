package be.abis.exercise.model;

import be.abis.exercise.utils.DateUtils;
import org.apache.logging.log4j.LogManager;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

import static java.time.temporal.ChronoUnit.YEARS;

public class Person implements Instructor, CourseParticipant, Comparable<CourseParticipant> {

	private static int counter = 0;

	private String personNumber;
	private String firstName;
	private String lastName;
	private LocalDate birthDate;
	private String email;
	private String password;
	private Company company;

	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		personNumber = "" + ++counter;
	}

	public Person(String firstName, String lastName, Company company) {
		this(firstName, lastName);
		this.company = company;
	}
	
	public Person(String firstName, String lastName, LocalDate birthDate, String email,
			String password, Company company) {
		this(firstName,lastName,company);
		this.birthDate = birthDate;
		this.email = email;
		this.password = password;
	}

	public Person(String... personData) {
		if (personData.length < 6)
			throw new IllegalArgumentException("incorrect person data");
		String[] personElements = Arrays.copyOfRange(personData, 0, 6);
		String[] addressElements = Arrays.copyOfRange(personData, 7, personData.length);
		Field[] fields = Arrays.stream(this.getClass().getDeclaredFields()).skip(1).toArray(Field[]::new);
		IntStream.range(0, personElements.length).forEach(idx -> {
			if (idx != 3 && idx != fields.length) {
				try {
					fields[idx].set(this, personElements[idx]);
				} catch (IllegalAccessException e) {
					LogManager.getLogger("error").error(e.getMessage());
				}
			}
		});
		this.birthDate = DateUtils.parse(personData[3]);
		this.company = !personData[6].equals("null") ? new Company(personData[6], new Address(addressElements)) : null;
	}

	public Person(String firstName, String lastName, LocalDate birthDate, String email,
			String password) {
		this(firstName,lastName);
		this.birthDate = birthDate;
		this.email = email;
		this.password = password;
	}

	public String getPersonNumber() {
		return personNumber;
	}
	
	public void setPersonNumber(String personNumber) {
		this.personNumber = personNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String fName) {
		firstName = fName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lName) {
		lastName = lName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public static int getNumberOfPersons() {
		return counter;
	}

	@Override
	public String toString() {
		return this.firstName + " " + this.lastName;
	}

	public void teach(Course course) {
		System.out.println(this + " teaches " + course.getTitle());
	}

	public void attendCourse(Course course) {
		System.out.println(this + " follows " + course.getTitle());
	}
	
	@Override
	public int compareTo(CourseParticipant o) {
		return this.lastName.compareTo(((Person)o).lastName);
	}

	public boolean hasAJob() {
		return this.getCompany() != null;
	}

	public String personData() {
		StringBuilder strB = new StringBuilder();
		try {
			Field[] fields = getClass().getDeclaredFields();
			fields = Arrays.asList(fields).subList(1, fields.length).toArray(Field[]::new);
			for (Field field: fields)
				strB.append(field.get(this)).append(";");
		} catch (IllegalAccessException e) {
			LogManager.getLogger("error").error(e.getMessage());
		}

		return strB.toString();
	}

	public int calculateAge() {
		LocalDate now = LocalDate.now();
		LocalDate birthDate = this.getBirthDate();
		return (int)YEARS.between(birthDate, now);
	}

    public static class FirstNameComparator implements Comparator<CourseParticipant>{
		@Override
		public int compare(CourseParticipant o1, CourseParticipant o2) {
			return ((Person)o1).getFirstName().compareToIgnoreCase(((Person)o2).getFirstName());
		}
    }

	@Override
	public int hashCode() {
		int hashCode = 3;
		hashCode += 17 * firstName.hashCode();
		hashCode += 17 * lastName.hashCode();
		hashCode += 17 * birthDate.hashCode();
		hashCode += 17 * email.hashCode();
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Person) {
			Person person = (Person)obj;
			if (person.getFirstName().equals(this.firstName) && person.getLastName().equals(this.lastName)
					&& person.getEmail().equals(this.email) && person.getBirthDate().equals(this.birthDate))
				return true;
		}
		return false;
	}
}