package kz.company.testdevicemanager.waitlist.application.domain.service;

import kz.company.testdevicemanager.common.valueobject.SerialNumber;
import kz.company.testdevicemanager.common.valueobject.User;
import kz.company.testdevicemanager.waitlist.application.domain.model.DeviceWaitlist;
import kz.company.testdevicemanager.waitlist.application.port.in.DeviceWaitlistUseCase;
import kz.company.testdevicemanager.waitlist.application.port.out.DeviceWaitlistPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class responsible for managing device waitlists.
 */
@Service
@RequiredArgsConstructor
public class DeviceWaitlistService implements DeviceWaitlistUseCase {

    private final DeviceWaitlistPersistencePort persistencePort;

    /**
     * Adds a user to the waitlist for a device with the specified serial number.
     *
     * @param serialNumber The serial number of the device.
     * @param user         The user to add to the waitlist.
     */
    @Override
    @Transactional
    public void addToWaitlistForDevice(SerialNumber serialNumber, User user) {
        // Check if the device is available for booking
        if (persistencePort.isDeviceAvailable(serialNumber)) {
            throw new IllegalStateException("Device with serial number " + serialNumber.value() + " is already available");
        }

        DeviceWaitlist deviceWaitlist = persistencePort.findBySerialNumber(serialNumber)
                .orElseGet(() -> DeviceWaitlist.newInstance(serialNumber));
        deviceWaitlist.addToWaitlist(user);
        persistencePort.save(deviceWaitlist);
    }

    /**
     * Removes a user from the waitlist for a device with the specified serial number.
     *
     * @param serialNumber The serial number of the device.
     * @param user         The user to remove from the waitlist.
     */
    @Override
    @Transactional
    public void removeFromWaitlistForDevice(SerialNumber serialNumber, User user) {
        DeviceWaitlist waitlist = persistencePort.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new IllegalArgumentException("No waitlist found for device " + serialNumber.value()));
        waitlist.removeFromWaitlist(user);
        persistencePort.save(waitlist);
    }
}
