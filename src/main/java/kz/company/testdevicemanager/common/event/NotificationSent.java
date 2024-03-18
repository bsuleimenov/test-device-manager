package kz.company.testdevicemanager.common.event;

import kz.company.testdevicemanager.common.valueobject.SerialNumber;
import kz.company.testdevicemanager.common.valueobject.User;

/**
 * Represents an event indicating that a notification has been sent.
 */
public record NotificationSent(SerialNumber serialNumber, User recipient, String message) {

    /**
     * Creates a new instance of {@code NotificationSent}.
     *
     * @param serialNumber The serial number of the device.
     * @param recipient    The recipient of the notification.
     * @param message    The message of the notification.
     */
    public static NotificationSent of(SerialNumber serialNumber, User recipient, String message) {
        if (serialNumber == null || recipient == null) {
            throw new NullPointerException("Serial number and recipient must not be null");
        }
        return new NotificationSent(serialNumber, recipient, message);
    }
}
