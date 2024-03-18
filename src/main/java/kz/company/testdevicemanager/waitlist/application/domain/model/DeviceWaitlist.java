package kz.company.testdevicemanager.waitlist.application.domain.model;

import kz.company.testdevicemanager.common.valueobject.SerialNumber;
import kz.company.testdevicemanager.common.valueobject.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a waitlist for a device.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeviceWaitlist {

    @Getter
    private final SerialNumber serialNumber;
    @Getter
    private final Set<User> newlyAddedUsers;
    private final Map<User, Boolean> waitlistUsers;

    /**
     * Creates a new waitlist for the specified device.
     *
     * @param serialNumber The serial number of the device to create the waitlist for.
     * @return The created DeviceWaitlist.
     */
    public static DeviceWaitlist newInstance(SerialNumber serialNumber) {
        return new DeviceWaitlist(serialNumber, new HashSet<>(), new HashMap<>());
    }

    /**
     * Creates a new DeviceWaitlist instance with pre-existing waitlist users.
     *
     * @param serialNumber   The serial number of the device.
     * @param users          The set of users already on the waitlist.
     * @return The created DeviceWaitlist instance.
     */
    public static DeviceWaitlist createWithInitialWaitlist(SerialNumber serialNumber, Set<User> users) {
        Map<User, Boolean> initialWaitlistUsers =
                users.stream().collect(Collectors.toMap(user -> user, user -> true));
        return new DeviceWaitlist(serialNumber, new HashSet<>(), initialWaitlistUsers);
    }

    /**
     * Adds a user to the waitlist for the device.
     *
     * @param user The user to add to the waitlist.
     * @throws IllegalStateException If the user is already in the waitlist for this device.
     */
    public void addToWaitlist(User user) {
        if (isUserInWaitlist(user)) {
            throw new IllegalStateException("User is already in the waitlist for this device");
        }
        waitlistUsers.put(user, true);
        newlyAddedUsers.add(user);
    }

    /**
     * Removes a user from the waitlist for the device.
     *
     * @param user The user to remove from the waitlist.
     * @throws IllegalStateException If the user is not in the waitlist for this device.
     */
    public void removeFromWaitlist(User user) {
        if (isUserInWaitlist(user)) {
            waitlistUsers.put(user, false);
        } else {
            throw new IllegalStateException("User is not in the waitlist for this device");
        }
    }

    /**
     * Get removed users from the waitlist.
     *
     * @return Set of removed users.
     */
    public Set<User> getRemovedUsers() {
        return waitlistUsers.entrySet().stream()
                .filter(entry -> !entry.getValue()) // Filter out users with value false
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    /**
     * Get all users from the waitlist.
     *
     * @return Set of users.
     */
    public Set<User> getAllUsers() {
        return waitlistUsers.entrySet().stream()
                .filter(entry -> entry.getValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    /**
     * Checks if a user is in the waitlist for the device.
     *
     * @param user The user to check.
     * @return true if the user is in the waitlist, false otherwise.
     */
    public boolean isUserInWaitlist(User user) {
        return waitlistUsers.containsKey(user);
    }
}
