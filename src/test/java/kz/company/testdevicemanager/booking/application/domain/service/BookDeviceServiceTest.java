package kz.company.testdevicemanager.booking.application.domain.service;

import kz.company.testdevicemanager.booking.application.domain.model.DeviceBooking;
import kz.company.testdevicemanager.booking.application.port.out.DeviceBookingPersistencePort;
import kz.company.testdevicemanager.common.event.DeviceReturned;
import kz.company.testdevicemanager.common.valueobject.SerialNumber;
import kz.company.testdevicemanager.common.valueobject.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BookDeviceServiceTest {

    @Mock
    private DeviceBookingPersistencePort persistencePort;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @InjectMocks
    private BookDeviceService bookDeviceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testBookDevice_Success() {
        SerialNumber serialNumber = SerialNumber.of("S9SN123");
        User bookedBy = User.of("user1");
        DeviceBooking availableDevice = DeviceBooking.createAvailable(1L, serialNumber, "Samsung Galaxy S9");

        when(persistencePort.findBySerialNumber(serialNumber)).thenReturn(availableDevice);

        bookDeviceService.bookDevice(serialNumber, bookedBy);

        verify(persistencePort, times(1)).saveBooking(availableDevice);
    }

    @Test
    public void testReturnDevice_Success() {
        SerialNumber serialNumber = SerialNumber.of("S9SN123");
        User returnedBy = User.of("user1");

        DeviceBooking bookedDevice = DeviceBooking.createBooked(1L, serialNumber, "Samsung Galaxy S9", 1L, returnedBy);

        when(persistencePort.findBySerialNumber(serialNumber)).thenReturn(bookedDevice);

        bookDeviceService.returnDevice(serialNumber, returnedBy);

        verify(persistencePort, times(1)).updateBooking(bookedDevice);
        verify(eventPublisher, times(1)).publishEvent(DeviceReturned.of(serialNumber));
    }

    @Test
    void testBookDevice_DeviceAlreadyBooked() {
        SerialNumber serialNumber = SerialNumber.of("S9SN123");
        User bookedBy = User.of("user1");
        DeviceBooking bookedDevice = DeviceBooking.createBooked(1L, serialNumber, "TestModel", 1L, bookedBy);

        when(persistencePort.findBySerialNumber(serialNumber)).thenReturn(bookedDevice);

        // Ensure an exception is thrown when attempting to book an already booked device
        assertThrows(IllegalStateException.class, () -> bookDeviceService.bookDevice(serialNumber, bookedBy));

        // Verify that persistencePort.saveBooking() was not called
        verify(persistencePort, never()).saveBooking(bookedDevice);
    }

    @Test
    void testReturnDevice_DeviceAlreadyAvailable() {
        SerialNumber serialNumber = SerialNumber.of("S9SN123");
        User returnedBy = User.of("user1");
        DeviceBooking availableDevice = DeviceBooking.createAvailable(1L, serialNumber, "Samsung Galaxy S9");

        when(persistencePort.findBySerialNumber(serialNumber)).thenReturn(availableDevice);

        // Ensure an exception is thrown when attempting to return an already available device
        assertThrows(IllegalStateException.class, () -> bookDeviceService.returnDevice(serialNumber, returnedBy));

        // Verify that persistencePort.updateBooking() and eventPublisher.publishEvent() were not called
        verify(persistencePort, never()).updateBooking(availableDevice);
        verify(eventPublisher, never()).publishEvent(DeviceReturned.of(serialNumber));
    }
}