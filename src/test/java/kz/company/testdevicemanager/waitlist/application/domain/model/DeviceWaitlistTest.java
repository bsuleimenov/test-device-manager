package kz.company.testdevicemanager.waitlist.application.domain.model;

import kz.company.testdevicemanager.common.valueobject.SerialNumber;
import kz.company.testdevicemanager.common.valueobject.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DeviceWaitlistTest {

    private DeviceWaitlist deviceWaitlist;
    private final SerialNumber serialNumber = SerialNumber.of("S9SN123");
    private final User user1 = User.of("user1");
    private final User user2 = User.of("user2");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        deviceWaitlist = DeviceWaitlist.newInstance(serialNumber);
    }

    @Test
    void testAddToWaitlist() {
        deviceWaitlist.addToWaitlist(user1);
        assertTrue(deviceWaitlist.getNewlyAddedUsers().contains(user1));
    }

    @Test
    void testAddToWaitlist_ThrowsExceptionWhenUserAlreadyInWaitlist() {
        deviceWaitlist.addToWaitlist(user1);
        assertThrows(IllegalStateException.class, () -> deviceWaitlist.addToWaitlist(user1));
    }

    @Test
    void testRemoveFromWaitlist() {
        deviceWaitlist.addToWaitlist(user1);
        deviceWaitlist.removeFromWaitlist(user1);
        assertTrue(deviceWaitlist.getRemovedUsers().contains(user1));
    }

    @Test
    void testRemoveFromWaitlist_ThrowsExceptionWhenUserNotInWaitlist() {
        assertThrows(IllegalStateException.class, () -> deviceWaitlist.removeFromWaitlist(user1));
    }

    @Test
    void testGetRemovedUsers() {
        deviceWaitlist.addToWaitlist(user1);
        deviceWaitlist.addToWaitlist(user2);
        deviceWaitlist.removeFromWaitlist(user1);
        Set<User> removedUsers = deviceWaitlist.getRemovedUsers();
        assertEquals(1, removedUsers.size());
        assertEquals(Set.of(user1), removedUsers);
    }

    @Test
    void testIsUserInWaitlist() {
        deviceWaitlist.addToWaitlist(user1);
        assertTrue(deviceWaitlist.isUserInWaitlist(user1));
        assertFalse(deviceWaitlist.isUserInWaitlist(user2));
    }
}
