package be.abis.exercise.model;

import be.abis.exercise.exception.ZipCodeNotCorrectException;
import org.apache.logging.log4j.LogManager;

import java.lang.reflect.Field;

public class Address {
	
	private String street;
	private String nr;
	private String zipCode;
	private String town;
	private String country;
	private String countryCode;
	
	public Address(){
		
	}
	
	public Address(String street, String nr, String zipCode, String town, String country, String countryCode) {
		this();
		this.street = street;
		this.nr = nr;
		this.zipCode = zipCode;
		this.town = town;
		this.country = country;
		this.countryCode = countryCode;
	}

	public Address(String... addressData) {
		Field[] fields = this.getClass().getDeclaredFields();
		if (addressData.length != fields.length)
			throw new IllegalArgumentException("incorrect address data");
		try {
			for (int index = 0; index < fields.length; index++)
				fields[index].set(this, addressData[index]);
		} catch (IllegalAccessException e) {
			LogManager.getLogger("error").error(e.getMessage());
		}
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNr() {
		return nr;
	}

	public void setNr(String nr) {
		this.nr = nr;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
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

	public String getBeautifulAddress() {
		return this.street + " " + this.nr + ", " + this.zipCode + " " + this.town;
	}

	public boolean checkZipCode() throws ZipCodeNotCorrectException{
		boolean result = true;
		if (this.countryCode.equalsIgnoreCase("BE"))
			result = this.zipCode.matches("\\d{4}");
		else if (this.countryCode.equalsIgnoreCase("NL"))
			result = this.zipCode.matches("\\d{4}[A-Z]{2}");
		if (!result)
			throw new ZipCodeNotCorrectException("ZipCode mismatch");
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Address) {
			Address address = (Address)o;
			return address.street.equals(street) && address.nr.equals(nr) && address.zipCode.equals(zipCode)
					&& address.town.equals(town) && address.country.equals(country) && address.countryCode.equals(countryCode);
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = 3;
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field: fields)
			hashCode += 7 * field.hashCode();
		return hashCode;
	}
}
