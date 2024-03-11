package kz.company.testdevicemanager.booking.adapter.in.web;

import kz.company.testdevicemanager.booking.application.port.in.BookDeviceUseCase;
import kz.company.testdevicemanager.common.AuthenticatedUser;
import kz.company.testdevicemanager.common.valueobject.SerialNumber;
import kz.company.testdevicemanager.common.valueobject.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling device booking operations.
 */
@RestController
@RequestMapping("/devices/{serialNumber}")
@RequiredArgsConstructor
public class DeviceBookingController {

    private final BookDeviceUseCase bookDeviceUseCase;

    /**
     * Endpoint for booking a device.
     *
     * @param serialNumber The serial number of the device to book.
     * @return ResponseEntity with a success message or an error message if the operation fails.
     */
    @PreAuthorize("hasRole('TESTER')")
    @PostMapping("/book")
    public ResponseEntity<String> bookDevice(@PathVariable String serialNumber) {
        try {
            User currentUser = AuthenticatedUser.getCurrentUser();
            bookDeviceUseCase.bookDevice(SerialNumber.of(serialNumber), currentUser);

            return ResponseEntity.ok("Device with serial number " + serialNumber + " booked successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint for returning a device.
     *
     * @param serialNumber The serial number of the device to return.
     * @return ResponseEntity with a success message or an error message if the operation fails.
     */
    @PreAuthorize("hasRole('TESTER')")
    @PostMapping("/return")
    public ResponseEntity<String> returnDevice(@PathVariable String serialNumber) {
        try {
            User currentUser = AuthenticatedUser.getCurrentUser();
            bookDeviceUseCase.returnDevice(SerialNumber.of(serialNumber), currentUser);

            return ResponseEntity.ok("Device with serial number " + serialNumber + " returned successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
