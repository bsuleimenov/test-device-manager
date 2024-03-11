package kz.company.testdevicemanager.booking.application.port.in;

import kz.company.testdevicemanager.common.valueobject.SerialNumber;
import kz.company.testdevicemanager.common.valueobject.User;

/**
 * Use case interface for booking and returning devices.
 */
public interface BookDeviceUseCase {

    /**
     * Books a device with the specified serial number.
     *
     * @param serialNumber The serial number of the device to book.]
     * @param bookedBy     The user who is booking the device.
     */
    void bookDevice(SerialNumber serialNumber, User bookedBy);


    /**
     * Returns a device with the specified serial number.
     *
     * @param serialNumber The serial number of the device to return.
     * @param returnedBy   The user who is returning the device.
     */
    void returnDevice(SerialNumber serialNumber, User returnedBy);
}
