package be.abis.exercise.test;

import be.abis.exercise.exception.ZipCodeNotCorrectException;
import be.abis.exercise.model.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    private Address[] addresses = new Address[2];

    @BeforeEach
    void setUp() {
        addresses[0] = new Address("Oorlog straat", "17", "1200AB", "Rotterdam", "Netherlands", "NL");
        addresses[1] = new Address("rue du globe", "23", "1200AB", "Arlon", "Belgium", "BE");
    }

    @Test
    void checkZipCode() throws ZipCodeNotCorrectException {
        assertEquals(true, addresses[0].checkZipCode());
        assertThrows(ZipCodeNotCorrectException.class, () -> addresses[1].checkZipCode());
    }
}