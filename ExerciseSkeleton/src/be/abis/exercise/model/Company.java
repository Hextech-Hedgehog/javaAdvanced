package be.abis.exercise.model;

import org.apache.logging.log4j.LogManager;

import java.lang.reflect.Field;
import java.util.Objects;

public class Company implements Comparable<Company>{
	
	private String name;
	private Address address;
	
	public Company(){}

	public Company(String name) {
		this();
		this.name=name;
	}
	
	public Company(String name, Address address) {
		this(name);
		this.address=address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		StringBuilder strB = new StringBuilder();
		try {
			Field[] fields = getClass().getDeclaredFields();
			for (int index = 0; index < fields.length - 1; index++)
				strB.append(fields[index].get(this)).append(";");
			strB.append(fields[fields.length - 1].get(this));
		} catch (IllegalAccessException e) {
			LogManager.getLogger("error").error(e.getMessage());
		}

		return strB.toString();
	}

	@Override
	public int compareTo(Company o) {
		return this.getName().compareTo(o.getName());
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Company) {
			Company company = (Company)o;
			return company.name.equals(this.name) && company.getAddress().equals(this.getAddress());
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = 13;
		hashCode += 11 * name.hashCode();
		hashCode += 11 * address.hashCode();
		return hashCode;
	}
}