package kz.company.testdevicemanager.waitlist.application.port.in;

import kz.company.testdevicemanager.common.valueobject.SerialNumber;
import kz.company.testdevicemanager.common.valueobject.User;

/**
 * Use case interface for managing device waitlist.
 */
public interface DeviceWaitlistUseCase {

    /**
     * Adds a user to the waitlist for a device with the specified serial number.
     *
     * @param serialNumber The serial number of the device to add to the waitlist.
     * @param user         The user to add to the waitlist.
     */
    void addToWaitlistForDevice(SerialNumber serialNumber, User user);

    /**
     * Removes a user from the waitlist for a device with the specified serial number.
     *
     * @param serialNumber The serial number of the device to remove from the waitlist.
     * @param user         The user to remove from the waitlist.
     */
    void removeFromWaitlistForDevice(SerialNumber serialNumber, User user);
}
