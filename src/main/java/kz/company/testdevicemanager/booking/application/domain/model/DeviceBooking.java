package kz.company.testdevicemanager.booking.application.domain.model;

import kz.company.testdevicemanager.common.valueobject.SerialNumber;
import kz.company.testdevicemanager.common.valueobject.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

/**
 * Represents a device booking.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeviceBooking {

    private Long deviceId;
    private final SerialNumber serialNumber;
    private final String modelName;
    private boolean available;

    private Long bookingId;
    private User holder;
    private LocalDate bookedDate;
    private LocalDate returnedDate;

    /**
     * Creates a DeviceBooking for an available device.
     */
    public static DeviceBooking createAvailable(Long deviceId, SerialNumber serialNumber, String modelName) {
        return new DeviceBooking(
                deviceId, serialNumber, modelName, true, null, null, null, null);
    }

    /**
     * Creates a DeviceBooking for a booked device.
     */
    public static DeviceBooking createBooked(Long deviceId, SerialNumber serialNumber, String modelName, Long bookingId, User holder) {
        return new DeviceBooking(
                deviceId, serialNumber, modelName, false, bookingId, holder, null, null);
    }

    /**
     * Books the device with the specified user.
     *
     * @param bookedBy The user who is booking the device.
     * @throws IllegalStateException If the device is not available or is already booked.
     */
    public void bookDevice(User bookedBy) {
        if (!available) {
            throw new IllegalStateException(
                    String.format("Device with serial number %s (%s) is not available. Booked by: %s", serialNumber.value(), modelName, holder.username()));
        }
        this.available = false;
        this.bookedDate = LocalDate.now();
        this.holder = bookedBy;
    }

    /**
     * Returns the device by the specified user.
     *
     * @param returnedBy The user who is returning the device.
     * @throws IllegalStateException If the device is already available, or if the user trying to return the device is not the one who booked it.
     */
    public void returnDevice(User returnedBy) {
        if (available) {
            throw new IllegalStateException(
                    String.format("Device with serial number %s (%s) is already available", serialNumber.value(), modelName));
        }
        if (!returnedBy.equals(holder)) {
            throw new IllegalStateException(
                    String.format("Device with serial number %s is booked by %s. Only the user who booked the device can return it", serialNumber.value(), holder.username()));
        }
        this.available = true;
        this.returnedDate = LocalDate.now();
    }

    public DeviceBookingInfo getDeviceBookingInfo() {
        return new DeviceBookingInfo(deviceId, serialNumber.value(), available, bookingId, holder, bookedDate, returnedDate);
    }

    /**
     * Represents the information about a device booking.
     */
    public record DeviceBookingInfo(Long deviceId, String serialNumber, boolean available, Long bookingId, User holder, LocalDate bookedDate, LocalDate returnedDate) {
    }
}
