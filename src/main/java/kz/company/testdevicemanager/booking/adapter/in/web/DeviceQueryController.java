package kz.company.testdevicemanager.booking.adapter.in.web;

import kz.company.testdevicemanager.booking.application.domain.model.api.DeviceInfo;
import kz.company.testdevicemanager.booking.application.domain.model.api.DeviceOverview;
import kz.company.testdevicemanager.booking.application.port.in.GetDeviceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling device queries.
 */
@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceQueryController {

    private final GetDeviceUseCase getDeviceUseCase;

    /**
     * Retrieves information for all devices.
     *
     * @return ResponseEntity containing a list of DeviceOverview objects.
     */
    @GetMapping
    public ResponseEntity<List<DeviceOverview>> getAllDevices() {
        List<DeviceOverview> allDevices = getDeviceUseCase.getAllDevices();
        return ResponseEntity.ok(allDevices);
    }

    /**
     * Retrieves information for all available devices.
     *
     * @return ResponseEntity containing a list of DeviceInfo objects.
     */
    @GetMapping("/available")
    public ResponseEntity<List<DeviceInfo>> getAvailableDevices() {
        List<DeviceInfo> availableDevices = getDeviceUseCase.getAllAvailableDevices();
        return ResponseEntity.ok(availableDevices);
    }
}
