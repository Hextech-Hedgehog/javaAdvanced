package be.abis.exercise.model;


import be.abis.exercise.exception.InvoiceException;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class PublicSession extends Session {
	
	public final static Company ABIS = new Company("Abis", new Address("Wayne boulevard", "13", "1500", "Gotham", "Puerto Rico", "EP"));
	private ArrayList<CourseParticipant> enrolments = new ArrayList<>();

	public PublicSession(Course course, LocalDate date, Company location,
			Instructor instructor) {
		super(course, date, location, instructor);
	}

	@Override
	public Company getOrganizer() {
		return ABIS;
	}

	public ArrayList<CourseParticipant> getEnrolments() {
		return enrolments;
	}

	public void setEnrolments(ArrayList<CourseParticipant> enrolments) {
		this.enrolments = enrolments;
	}

	@Override
	public double invoice() throws InvoiceException {
		System.out.println("Invoice in PublicSession");
		return 500;
	}

	public void addEnrolment(CourseParticipant... cps) {
		for (CourseParticipant c : cps)
			this.addEnrolment(c);
	}

	protected void addEnrolment(CourseParticipant cp) {
		if (!enrolments.contains(cp)) {
			enrolments.add(cp);
			LogManager.getLogger("informative").info("Enrolment added to the list, now "
					+ enrolments.size() + " enrolments.");
			LogManager.getLogger("informative").info("enrollee is: " + cp);
		} else {
			LogManager.getLogger("informative").info("Couldn't add " + cp + " as enrollee, since he was already enrolled");
		}
	}

	public void removeEnrolment(CourseParticipant... cps) {
		for (CourseParticipant c : cps)
			removeEnrolment(c);
	}

	protected void removeEnrolment(CourseParticipant cp) {
		if (enrolments.contains(cp)) {
			enrolments.remove(cp);
			LogManager.getLogger("informative").info("Enrollee " + cp + " removed from the list, now "
					+ enrolments.size() + " enrolments.");
		} else {
			LogManager.getLogger("informative").info("Couldn't remove enrolment.");
		}
	}
	
	public Iterator<CourseParticipant> getEnrolmentsIterator(){
		return enrolments.iterator();
	}
	
	public void printListOfParticipants() {
		String instructor = "Instructor:";
		String location = "Location:";
		String delimiter = "-------------------------------------------------------------------------------------";
		String instructorFirstName = ((Person)this.getInstructor()).getFirstName();
		String instructorLastName = ((Person)this.getInstructor()).getLastName();

		System.out.printf("%1$-25S\n%2$-4s\n%3$-20s%4$-4s%5$-16s\n", this.getCourse().getTitle(), delimiter, instructor, instructorFirstName, instructorLastName);
		System.out.printf("%-20s%-4s%-4s\n%-1s\n", location, this.getOrganizer().getName(), this.getOrganizer().getAddress().getBeautifulAddress(), delimiter);
		for (CourseParticipant cpa: this.enrolments) {
			Person person = (Person)cpa;
			System.out.printf("%-10s%-24s%-4s %-4s\n", person.getPersonNumber(), person.getCompany().getName(), person.getFirstName(), person.getLastName());
		}
	}

	public void writeListOfParticipants() {
		String instructor = "Instructor:";
		String location = "Location:";
		String delimiter = "-------------------------------------------------------------------------------------";
		String instructorFirstName = ((Person)this.getInstructor()).getFirstName();
		String instructorLastName = ((Person)this.getInstructor()).getLastName();

		try (PrintWriter pf = new PrintWriter("C:\\Users\\Duser\\Documents\\participants-" + this.getSessionId() + ".txt");) {
			pf.printf("%1$-25S\n%2$-4s\n%3$-20s%4$-4s%5$-16s\n", this.getCourse().getTitle(), delimiter, instructor, instructorFirstName, instructorLastName);
			pf.printf("%-20s%-4s%-4s\n%-1s\n", location, this.getOrganizer().getName(), this.getOrganizer().getAddress().getBeautifulAddress(), delimiter);
			for (CourseParticipant cpa: this.enrolments) {
				Person person = (Person)cpa;
				pf.printf("%-10s%-24s%-4s %-4s\n", person.getPersonNumber(), person.getCompany().getName(), person.getFirstName(), person.getLastName());
			}
		} catch (IOException e) {
			LogManager.getLogger("error").error(e.getMessage());
		}
	}

	public String calculateRevenue() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("fr", "BE"));
		BigDecimal bd = new BigDecimal(1200);
		BigDecimal total = bd.multiply(BigDecimal.valueOf(0.21));
		total = bd.subtract(total);

		return nf.format(total);
	}
	
}