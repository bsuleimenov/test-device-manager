package kz.company.testdevicemanager.booking.application.port.out;

import kz.company.testdevicemanager.booking.application.domain.model.DeviceBooking;
import kz.company.testdevicemanager.common.valueobject.SerialNumber;

/**
 * Port for accessing device booking persistence operations.
 */
public interface DeviceBookingPersistencePort {

    /**
     * Retrieves device booking information by serial number.
     *
     * @param serialNumber The serial number of the device to find.
     * @return The booking information for the device with the specified serial number.
     */
    DeviceBooking findBySerialNumber(SerialNumber serialNumber);

    /**
     * Saves device booking information.
     *
     * @param deviceBooking The device booking information to save.
     */
    void saveBooking(DeviceBooking deviceBooking);

    /**
     * Updates device booking information for returning the device.
     *
     * @param deviceBooking The device booking information to update.
     */
    void updateBooking(DeviceBooking deviceBooking);
}
