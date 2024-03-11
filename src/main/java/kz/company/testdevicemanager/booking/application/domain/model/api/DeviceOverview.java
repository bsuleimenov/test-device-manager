package kz.company.testdevicemanager.booking.application.domain.model.api;

public record DeviceOverview(
        String serialNumber,
        String modelName,
        boolean available,
        BookingInfo bookingInfo
) {}
