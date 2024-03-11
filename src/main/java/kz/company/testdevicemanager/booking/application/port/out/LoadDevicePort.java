package kz.company.testdevicemanager.booking.application.port.out;

import kz.company.testdevicemanager.booking.application.domain.model.api.DeviceInfo;
import kz.company.testdevicemanager.booking.application.domain.model.api.DeviceOverview;

import java.util.List;

/**
 * Port for loading device information from database.
 */
public interface LoadDevicePort {
    List<DeviceInfo> loadAvailableDevices();
    List<DeviceOverview> loadAllDevices();
}
