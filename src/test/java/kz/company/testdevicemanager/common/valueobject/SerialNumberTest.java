package kz.company.testdevicemanager.common.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SerialNumberTest {

    @Test
    void serialNumber_ValidFormat_Success() {
        // Valid serial number format (7-15 alphanumeric characters)
        String validSerialNumber = "ABC12345";
        SerialNumber serialNumber = SerialNumber.of(validSerialNumber);
        assertNotNull(serialNumber);
        assertEquals(validSerialNumber, serialNumber.value());
    }

    @Test
    void serialNumber_NullInput_ThrowsException() {
        // Null serial number input
        assertThrows(NullPointerException.class, () -> SerialNumber.of(null));
    }

    @Test
    void serialNumber_InvalidFormat_LessThan7Characters_ThrowsException() {
        // Invalid serial number format (less than 7 characters)
        String invalidSerialNumber = "ABC123";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> SerialNumber.of(invalidSerialNumber));
        assertEquals("Invalid serial number format for mobile device: " + invalidSerialNumber, exception.getMessage());
    }

    @Test
    void serialNumber_InvalidFormat_MoreThan15Characters_ThrowsException() {
        // Invalid serial number format (more than 15 characters)
        String invalidSerialNumber = "ABC1234567890123";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> SerialNumber.of(invalidSerialNumber));
        assertEquals("Invalid serial number format for mobile device: " + invalidSerialNumber, exception.getMessage());
    }

    @Test
    void serialNumber_InvalidFormat_SpecialCharacters_ThrowsException() {
        // Invalid serial number format (special characters)
        String invalidSerialNumber = "ABC!@#$%^";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> SerialNumber.of(invalidSerialNumber));
        assertEquals("Invalid serial number format for mobile device: " + invalidSerialNumber, exception.getMessage());
    }
}