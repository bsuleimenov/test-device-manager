package kz.company.testdevicemanager.booking.application.domain.model;

import kz.company.testdevicemanager.common.valueobject.SerialNumber;
import kz.company.testdevicemanager.common.valueobject.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeviceBookingTest {

    private DeviceBooking availableDevice;
    private DeviceBooking bookedDevice;
    private User user;

    @BeforeEach
    void setUp() {
        SerialNumber serialNumber = SerialNumber.of("S9SN123");
        String modelName = "Samsung Galaxy S9";
        user = User.of("User1");

        availableDevice = DeviceBooking.createAvailable(1L, serialNumber, modelName);
        bookedDevice = DeviceBooking.createBooked(2L, serialNumber, modelName, 1L, user);
    }

    @Test
    void testCreateAvailableDeviceBooking() {
        assertNotNull(availableDevice);
        assertTrue(availableDevice.getDeviceBookingInfo().available());
        assertNull(availableDevice.getDeviceBookingInfo().bookingId());
    }

    @Test
    void testCreateBookedDeviceBooking() {
        assertNotNull(bookedDevice);
        assertFalse(bookedDevice.getDeviceBookingInfo().available());
        assertEquals(1L, bookedDevice.getDeviceBookingInfo().bookingId());
        assertEquals(user.username(), bookedDevice.getDeviceBookingInfo().holder().username());
    }

    @Test
    void testBookDevice() {
        availableDevice.bookDevice(user);
        assertFalse(availableDevice.getDeviceBookingInfo().available());
        assertNotNull(availableDevice.getDeviceBookingInfo().bookedDate());
        assertEquals(user.username(), availableDevice.getDeviceBookingInfo().holder().username());
    }

    @Test
    void testReturnDevice() {
        bookedDevice.returnDevice(user);
        assertTrue(bookedDevice.getDeviceBookingInfo().available());
        assertNotNull(bookedDevice.getDeviceBookingInfo().returnedDate());
    }

    @Test
    void testBookAlreadyBookedDevice() {
        assertThrows(IllegalStateException.class, () -> bookedDevice.bookDevice(user));
    }

    @Test
    void testReturnAlreadyAvailableDevice() {
        assertThrows(IllegalStateException.class, () -> availableDevice.returnDevice(user));
    }

    @Test
    void testReturnDeviceByDifferentUser() {
        User anotherUser = User.of("User2");
        assertThrows(IllegalStateException.class, () -> bookedDevice.returnDevice(anotherUser));
    }

}