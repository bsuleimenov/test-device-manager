package kz.company.testdevicemanager.booking.application.domain.service;

import kz.company.testdevicemanager.booking.application.domain.model.DeviceBooking;
import kz.company.testdevicemanager.booking.application.port.in.BookDeviceUseCase;
import kz.company.testdevicemanager.booking.application.port.out.DeviceBookingPersistencePort;
import kz.company.testdevicemanager.common.valueobject.SerialNumber;
import kz.company.testdevicemanager.common.event.DeviceReturned;
import kz.company.testdevicemanager.common.valueobject.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class responsible for booking and returning devices.
 */
@Service
@RequiredArgsConstructor
public class BookDeviceService implements BookDeviceUseCase {

    private final DeviceBookingPersistencePort deviceBookingPersistencePort;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Books a device with the specified serial number.
     *
     * @param serialNumber The serial number of the device to book.
     * @param bookedBy     The user who is booking the device.
     */
    @Override
    @Transactional
    public void bookDevice(SerialNumber serialNumber, User bookedBy) {
        DeviceBooking deviceBooking = deviceBookingPersistencePort.findBySerialNumber(serialNumber);
        deviceBooking.bookDevice(bookedBy);
        deviceBookingPersistencePort.saveBooking(deviceBooking);
    }


    /**
     * Returns a device with the specified serial number.
     * It also publishes an event to notify users in waiting list about the device return.
     *
     * @param serialNumber The serial number of the device to return.
     * @param returnedBy   The user who is returning the device.
     */
    @Override
    @Transactional
    public void returnDevice(SerialNumber serialNumber, User returnedBy) {
        DeviceBooking deviceBooking = deviceBookingPersistencePort.findBySerialNumber(serialNumber);
        deviceBooking.returnDevice(returnedBy);
        deviceBookingPersistencePort.updateBooking(deviceBooking);

        eventPublisher.publishEvent(DeviceReturned.of(serialNumber));
    }
}
