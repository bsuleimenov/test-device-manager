package kz.company.testdevicemanager.booking.adapter.out.persistence;

import kz.company.testdevicemanager.booking.application.domain.model.api.BookingInfo;
import kz.company.testdevicemanager.booking.application.domain.model.api.DeviceInfo;
import kz.company.testdevicemanager.booking.application.domain.model.api.DeviceOverview;
import kz.company.testdevicemanager.booking.application.port.out.LoadDevicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter class for loading device information from the database.
 */
@Service
@RequiredArgsConstructor
class DevicePersistenceAdapter implements LoadDevicePort {

    private final DeviceRepository deviceRepository;
    private final BookingRepository bookingRepository;

    @Override
    public List<DeviceInfo> loadAvailableDevices() {
        List<Device> availableDevices = deviceRepository.findAllAvailable();
        return availableDevices.stream()
                .map(this::convertToDeviceInfo)
                .collect(Collectors.toList());
    }

    @Override
    public List<DeviceOverview> loadAllDevices() {
        List<Device> allDevices = (List<Device>) deviceRepository.findAll();
        return allDevices.stream()
                .map(this::convertToDeviceOverview)
                .collect(Collectors.toList());
    }

    /**
     * Converts a Device entity to a DeviceInfo object.
     *
     * @param device The Device entity to convert.
     * @return DeviceInfo object representing the device.
     */
    private DeviceInfo convertToDeviceInfo(Device device) {
        return new DeviceInfo(device.getSerialNumber(), device.getModelName());
    }

    /**
     * Converts a Device entity to a DeviceOverview object.
     *
     * @param device The Device entity to convert.
     * @return DeviceOverview object representing the device.
     */
    private DeviceOverview convertToDeviceOverview(Device device) {
        BookingInfo bookingInfo = null;
        if (!device.isAvailable()) {
            Optional<Booking> bookingOpt = bookingRepository.findById(device.getBookingId());
            bookingInfo = bookingOpt
                    .map(booking -> new BookingInfo(booking.getBookedDate(), booking.getBookedBy()))
                    .orElse(null);
        }
        return new DeviceOverview(device.getSerialNumber(), device.getModelName(), device.isAvailable(), bookingInfo);
    }
}
