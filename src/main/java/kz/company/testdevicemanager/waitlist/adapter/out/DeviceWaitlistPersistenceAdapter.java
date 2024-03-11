package kz.company.testdevicemanager.waitlist.adapter.out;

import kz.company.testdevicemanager.booking.adapter.out.persistence.DeviceRepository;
import kz.company.testdevicemanager.booking.adapter.out.persistence.tables.Device;
import kz.company.testdevicemanager.common.valueobject.SerialNumber;
import kz.company.testdevicemanager.common.valueobject.User;
import kz.company.testdevicemanager.waitlist.application.domain.model.DeviceWaitlist;
import kz.company.testdevicemanager.waitlist.application.port.out.DeviceWaitlistPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Adapter responsible for interacting with the database to store and retrieve device waitlists.
 */
@Service
@RequiredArgsConstructor
public class DeviceWaitlistPersistenceAdapter implements DeviceWaitlistPersistencePort {

    private final DeviceWaitlistRepository deviceWaitlistRepository;
    private final DeviceRepository deviceRepository;

    /**
     * Checks if the device with the given serial number is available for booking.
     *
     * @param serialNumber The serial number of the device to check.
     * @return True if the device is available for booking, false otherwise.
     * @throws IllegalArgumentException If the device with the specified serial number does not exist.
     */
    @Override
    public boolean isDeviceAvailable(SerialNumber serialNumber) {
        Device device = deviceRepository.findBySerialNumber(serialNumber.value())
                .orElseThrow(() -> new IllegalArgumentException("Device with serial number " + serialNumber.value() + " does not exist"));
        return device.isAvailable();
    }

    /**
     * Retrieves the waitlist for the device with the specified serial number.
     *
     * @param serialNumber The serial number of the device.
     * @return An Optional containing the DeviceWaitlist if found, empty otherwise.
     */
    @Override
    public Optional<DeviceWaitlist> findBySerialNumber(SerialNumber serialNumber) {
        List<Waitlist> waitlists = deviceWaitlistRepository.findBySerialNumber(serialNumber.value());
        if (CollectionUtils.isEmpty(waitlists)) {
            return Optional.empty();
        }

        Set<User> initialUsers = waitlists.stream()
                .map(waitlist -> User.of(waitlist.getUsername()))
                .collect(Collectors.toSet());

        DeviceWaitlist deviceWaitlist = DeviceWaitlist.createWithInitialWaitlist(serialNumber, initialUsers);

        return Optional.of(deviceWaitlist);
    }

    /**
     * Saves the given DeviceWaitlist to the database.
     * It deletes records for removed users and adds records for newly added users.
     *
     * @param deviceWaitlist The DeviceWaitlist to save.
     */
    @Override
    public void save(DeviceWaitlist deviceWaitlist) {
        SerialNumber serialNumber = deviceWaitlist.getSerialNumber();
        Set<User> newlyAddedUsers = deviceWaitlist.getNewlyAddedUsers();
        Set<User> removedUsers = deviceWaitlist.getRemovedUsers();

        // Delete records for removed users
        if (!CollectionUtils.isEmpty(removedUsers)) {
            Set<String> users = removedUsers.stream()
                    .map(User::username)
                    .collect(Collectors.toSet());

            deviceWaitlistRepository.deleteBySerialNumberAndUserIn(serialNumber.value(), users);
        }

        // Add records for newly added users
        if (!CollectionUtils.isEmpty(newlyAddedUsers)) {
            Set<Waitlist> waitlists = newlyAddedUsers.stream()
                    .map(user -> new Waitlist(serialNumber.value(), user.username()))
                    .collect(Collectors.toSet());

            deviceWaitlistRepository.saveAll(waitlists);
        }
    }
}
