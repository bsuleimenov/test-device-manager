package kz.company.testdevicemanager.waitlist.application.domain.service;

import kz.company.testdevicemanager.common.valueobject.SerialNumber;
import kz.company.testdevicemanager.common.valueobject.User;
import kz.company.testdevicemanager.waitlist.application.domain.model.DeviceWaitlist;
import kz.company.testdevicemanager.waitlist.application.port.in.DeviceWaitlistUseCase;
import kz.company.testdevicemanager.waitlist.application.port.out.DeviceWaitlistPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * Service class responsible for managing device waitlists.
 */
@Slf4j
@Service
@RequiredArgsConstructor
class DeviceWaitlistService implements DeviceWaitlistUseCase {

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
        log.info("User {} added to waitlist for device {}", user.username(), serialNumber.value());
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
        log.info("User {} removed from waitlist for device {} ", user.username(), serialNumber.value());
    }

    @Override
    public Set<User> getAllUsersFromWaitlist(SerialNumber serialNumber) {
        Optional<DeviceWaitlist> deviceWaitlistOpt = persistencePort.findBySerialNumber(serialNumber);
        if (deviceWaitlistOpt.isEmpty()) {
            return Collections.emptySet();
        }

        DeviceWaitlist deviceWaitlist = deviceWaitlistOpt.get();
        return deviceWaitlist.getAllUsers();
    }
}
