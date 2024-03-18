package kz.company.testdevicemanager.booking.application.port.in;

import kz.company.testdevicemanager.booking.application.domain.model.api.DeviceInfo;
import kz.company.testdevicemanager.booking.application.domain.model.api.DeviceOverview;
import kz.company.testdevicemanager.common.valueobject.SerialNumber;

import java.util.List;

/**
 * Use case interface for retrieving device information.
 */
public interface GetDeviceUseCase {

    /**
     * Retrieves information for all available devices.
     *
     * @return List of DeviceInfo objects representing available devices.
     */
    List<DeviceInfo> getAllAvailableDevices();

    /**
     * Retrieves information for all devices.
     *
     * @return List of DeviceOverview objects representing all devices.
     */
    List<DeviceOverview> getAllDevices();

    /**
     * Checks if the device with the given serial number is available for booking.
     *
     * @param serialNumber The serial number of the device to check.
     * @return true if the device is available, false otherwise.
     */
    boolean isDeviceAvailable(SerialNumber serialNumber);
}
