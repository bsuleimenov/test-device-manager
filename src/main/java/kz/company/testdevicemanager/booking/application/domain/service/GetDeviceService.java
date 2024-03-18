package kz.company.testdevicemanager.booking.application.domain.service;

import kz.company.testdevicemanager.booking.application.domain.model.api.DeviceInfo;
import kz.company.testdevicemanager.booking.application.domain.model.api.DeviceOverview;
import kz.company.testdevicemanager.booking.application.port.in.GetDeviceUseCase;
import kz.company.testdevicemanager.booking.application.port.out.LoadDevicePort;
import kz.company.testdevicemanager.common.valueobject.SerialNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for retrieving device information.
 */
@Service
@RequiredArgsConstructor
class GetDeviceService implements GetDeviceUseCase {

    private final LoadDevicePort loadDevicePort;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DeviceInfo> getAllAvailableDevices() {
        return loadDevicePort.loadAvailableDevices();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DeviceOverview> getAllDevices() {
        return loadDevicePort.loadAllDevices();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDeviceAvailable(SerialNumber serialNumber) {
        DeviceOverview deviceOverview = loadDevicePort.loadDevice(serialNumber);
        return deviceOverview.available();
    }
}
