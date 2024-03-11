package kz.company.testdevicemanager.booking.application.domain.model.api;


import java.time.LocalDate;

public record BookingInfo(
        LocalDate bookedDate,
        String holder
) {}
