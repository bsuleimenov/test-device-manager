package kz.company.testdevicemanager.booking.adapter.out.persistence;

import kz.company.testdevicemanager.booking.adapter.out.persistence.tables.Booking;
import kz.company.testdevicemanager.booking.adapter.out.persistence.tables.Device;
import kz.company.testdevicemanager.booking.application.domain.model.DeviceBooking;
import kz.company.testdevicemanager.booking.application.port.out.DeviceBookingPersistencePort;
import kz.company.testdevicemanager.common.valueobject.SerialNumber;
import kz.company.testdevicemanager.common.valueobject.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Adapter class for persisting device booking information.
 */
@Service
@RequiredArgsConstructor
public class DeviceBookingPersistenceAdapter implements DeviceBookingPersistencePort {

    private final DeviceRepository deviceRepository;
    private final BookingRepository bookingRepository;

    /**
     * Retrieves a device booking by its serial number.
     *
     * @param serialNumber The serial number of the device.
     * @return The corresponding device booking.
     * @throws IllegalArgumentException If the device with the given serial number does not exist.
     */
    @Override
    public DeviceBooking findBySerialNumber(SerialNumber serialNumber) {
        Device device = deviceRepository.findBySerialNumber(serialNumber.value())
                .orElseThrow(() -> new IllegalArgumentException("Device with serial number " + serialNumber.value() + " does not exist"));

        if (device.isAvailable()) {
            return DeviceBooking.createAvailable(device.getId(), SerialNumber.of(device.getSerialNumber()), device.getModelName());
        } else {
            Booking booking = bookingRepository.findById(device.getBookingId())
                    .orElseThrow(() -> new IllegalArgumentException("Booking info for " + serialNumber.value() + " does not exist"));
            return DeviceBooking.createBooked(
                    device.getId(),
                    SerialNumber.of(device.getSerialNumber()),
                    device.getModelName(),
                    booking.getId(),
                    User.of(booking.getBookedBy()));
        }
    }

    /**
     * Handle booking device.
     *
     * @param deviceBooking The device booking information to save.
     */
    @Override
    public void saveBooking(DeviceBooking deviceBooking) {
        if (deviceBooking == null || deviceBooking.getDeviceBookingInfo() == null) {
            throw new IllegalArgumentException("Invalid device booking");
        }

        DeviceBooking.DeviceBookingInfo bookingInfo = deviceBooking.getDeviceBookingInfo();

        Booking booking = new Booking();
        booking.setSerialNumber(bookingInfo.serialNumber());
        booking.setBookedDate(bookingInfo.bookedDate());
        booking.setBookedBy(bookingInfo.holder().username());
        booking = bookingRepository.save(booking);

        deviceRepository.updateAvailabilityAndBookingId(bookingInfo.deviceId(), false, booking.getId());
    }

    /**
     * Handle returning device.
     *
     * @param deviceBooking The device booking information to save.
     */
    @Override
    public void updateBooking(DeviceBooking deviceBooking) {
        if (deviceBooking == null || deviceBooking.getDeviceBookingInfo() == null) {
            throw new IllegalArgumentException("Invalid device booking");
        }

        DeviceBooking.DeviceBookingInfo bookingInfo = deviceBooking.getDeviceBookingInfo();

        deviceRepository.updateAvailabilityAndBookingId(bookingInfo.deviceId(), true, null);
        bookingRepository.updateReturnedDate(bookingInfo.bookingId(), bookingInfo.returnedDate());
    }
}
