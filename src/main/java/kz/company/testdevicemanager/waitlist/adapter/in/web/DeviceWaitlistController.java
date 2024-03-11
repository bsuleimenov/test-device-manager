package kz.company.testdevicemanager.waitlist.adapter.in.web;

import kz.company.testdevicemanager.common.valueobject.SerialNumber;
import kz.company.testdevicemanager.common.valueobject.User;
import kz.company.testdevicemanager.common.AuthenticatedUser;
import kz.company.testdevicemanager.waitlist.application.port.in.DeviceWaitlistUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling device waitlist operations.
 */
@Slf4j
@RestController
@RequestMapping("/devices/{serialNumber}/waitlist")
@RequiredArgsConstructor
public class DeviceWaitlistController {

    private final DeviceWaitlistUseCase deviceWaitlistUseCase;

    /**
     * Endpoint for adding a user to the waitlist for a device.
     *
     * @param serialNumber The serial number of the device to add to the waitlist.
     * @return ResponseEntity with a success message or an error message if the operation fails.
     */
    @PostMapping("/add")
    public ResponseEntity<String> addToWaitlistForDevice(@PathVariable(value = "serialNumber") String serialNumber) {
        try {
            User currentUser = AuthenticatedUser.getCurrentUser();
            deviceWaitlistUseCase.addToWaitlistForDevice(SerialNumber.of(serialNumber), currentUser);
            return ResponseEntity.ok("Successfully added to waitlist for device with serial number " + serialNumber);
        } catch (Exception e) {
            log.error("Error adding new user to waitlist of device {}: {}", serialNumber, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint for removing a user from the waitlist for a device.
     *
     * @param serialNumber The serial number of the device to remove from the waitlist.
     * @return ResponseEntity with a success message or an error message if the operation fails.
     */
    @PostMapping("/remove")
    public ResponseEntity<String> removeFromWaitlistForDevice(@PathVariable(value = "serialNumber") String serialNumber) {
        try {
            User currentUser = AuthenticatedUser.getCurrentUser();
            deviceWaitlistUseCase.removeFromWaitlistForDevice(SerialNumber.of(serialNumber), currentUser);
            return ResponseEntity.ok("Successfully removed from waitlist for device with serial number " + serialNumber);
        } catch (Exception e) {
            log.error("Error removing user from waitlist of device {}: {}", serialNumber, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
