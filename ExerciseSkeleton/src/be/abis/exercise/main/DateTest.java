package be.abis.exercise.main;

import be.abis.exercise.model.Person;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.YEARS;

public class DateTest {

    public static void main(String[] args) {
        ex1A();
        ex1B();
        ex1C();
        ex1D();
        ex1E();
        ex2A();
    }

    public static void ex1A() {
        LocalDate now = LocalDate.now();
        LocalDate future = now.plusYears(3).plusMonths(3).plusDays(15);
        System.out.println("The day from 3 years, 3 months and 15 days from now is a: " + future.getDayOfWeek());
    }

    public static void ex1B() {
        LocalDate birthDate = LocalDate.of(1996, 9, 25);
        System.out.println("Day of birth was a: " + birthDate.getDayOfWeek());
    }

    public static void ex1C() {
        LocalDate now = LocalDate.now();
        LocalDate birthDate = LocalDate.of(1996, 9, 25);
        long years = YEARS.between(birthDate, now);
        LocalDate birthDay = birthDate.plusYears(years + 1);
        System.out.println("Days left until next birthday: " + DAYS.between(now, birthDay));
    }

    public static void ex1D() {
        LocalDate now = LocalDate.now();
        LocalDate birthDate = LocalDate.of(1996, 9, 25);
        System.out.println("We are: " + DAYS.between(birthDate, now) + " days old..");
    }

    public static void ex1E() {
        ZonedDateTime belgium = ZonedDateTime.now(ZoneId.of("Europe/Brussels"));
        ZonedDateTime india = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
        System.out.println("Time difference between Brussels and Calcutta in hours: " + HOURS.between(belgium.toLocalDateTime(), india.toLocalDateTime()));
    }

    public static void ex2A() {
        Person harry = new Person("Harry", "Potter", LocalDate.of(1981, 11, 3), "magicCaster@gmail.uk", "password");
        System.out.println(harry.getFirstName() + "'s age: " + harry.calculateAge());
    }

}
