package kz.company.testdevicemanager.common.event;

import kz.company.testdevicemanager.common.valueobject.SerialNumber;

/**
 * Represents an event indicating that a device has been returned.
 */
public record DeviceReturned(SerialNumber serialNumber) {

    /**
     * Creates a new instance of {@code DeviceReturned}.
     *
     * @param serialNumber The serial number of the returned device.
     */
    public static DeviceReturned of(SerialNumber serialNumber) {
        if (serialNumber == null) {
            throw new NullPointerException("Serial number must not be null");
        }
        return new DeviceReturned(serialNumber);
    }
}
