package kz.company.testdevicemanager.common.valueobject;

import java.util.Objects;

/**
 * Represents a serial number value object for devices like mobile phones.
 */
public record SerialNumber(String value) {

    private static final String MOBILE_SERIAL_NUMBER_CHECK = "[A-Za-z0-9]{7,15}";

    /**
     * Creates a SerialNumber instance with the specified value.
     *
     * @param value The value of the serial number.
     * @return A SerialNumber instance.
     * @throws IllegalArgumentException If the value is invalid.
     */
    public static SerialNumber of(String value) {
        Objects.requireNonNull(value, "Serial number value is required");

        if (!value.matches(MOBILE_SERIAL_NUMBER_CHECK)) {
            throw new IllegalArgumentException("Invalid serial number format for mobile device: " + value);
        }

        return new SerialNumber(value);
    }
}
