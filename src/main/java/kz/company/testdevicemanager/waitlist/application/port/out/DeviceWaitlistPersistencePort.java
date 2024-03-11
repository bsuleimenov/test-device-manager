package kz.company.testdevicemanager.waitlist.application.port.out;

import kz.company.testdevicemanager.common.valueobject.SerialNumber;
import kz.company.testdevicemanager.waitlist.application.domain.model.DeviceWaitlist;

import java.util.Optional;

/**
 * Port for managing persistence operations related to device waitlists.
 */
public interface DeviceWaitlistPersistencePort {

    /**
     * Checks if a device with the specified serial number is available.
     *
     * @param serialNumber The serial number of the device to check.
     * @return true if the device is available, false otherwise.
     */
    boolean isDeviceAvailable(SerialNumber serialNumber);



    /**
     * Retrieves the waitlist for a device with the specified serial number.
     *
     * @param serialNumber The serial number of the device to retrieve the waitlist for.
     * @return An optional containing the device waitlist if found, or empty if not found.
     */
    Optional<DeviceWaitlist> findBySerialNumber(SerialNumber serialNumber);

    /**
     * Saves the device waitlist.
     *
     * @param deviceWaitlist The device waitlist to save.
     */
    void save(DeviceWaitlist deviceWaitlist);
}
