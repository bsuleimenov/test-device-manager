package kz.company.testdevicemanager.booking.application.port.out;

import kz.company.testdevicemanager.booking.application.domain.model.api.DeviceInfo;
import kz.company.testdevicemanager.booking.application.domain.model.api.DeviceOverview;
import kz.company.testdevicemanager.common.valueobject.SerialNumber;

import java.util.List;

/**
 * Port for loading device information from database.
 */
public interface LoadDevicePort {
    List<DeviceInfo> loadAvailableDevices();
    List<DeviceOverview> loadAllDevices();
    DeviceOverview loadDevice(SerialNumber serialNumber);
}
