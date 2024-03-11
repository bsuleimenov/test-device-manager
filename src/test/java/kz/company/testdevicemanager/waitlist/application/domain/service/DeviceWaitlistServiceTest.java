package kz.company.testdevicemanager.waitlist.application.domain.service;

import kz.company.testdevicemanager.common.valueobject.SerialNumber;
import kz.company.testdevicemanager.common.valueobject.User;
import kz.company.testdevicemanager.waitlist.application.domain.model.DeviceWaitlist;
import kz.company.testdevicemanager.waitlist.application.port.out.DeviceWaitlistPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeviceWaitlistServiceTest {

    @Mock
    private DeviceWaitlistPersistencePort persistencePort;

    @InjectMocks
    private DeviceWaitlistService deviceWaitlistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addToWaitlistForDevice_DeviceAvailable_ThrowsException() {
        SerialNumber serialNumber = SerialNumber.of("S9SN123");
        User user = User.of("user1");

        when(persistencePort.isDeviceAvailable(serialNumber)).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> deviceWaitlistService.addToWaitlistForDevice(serialNumber, user));

        verify(persistencePort, never()).findBySerialNumber(serialNumber);
        verify(persistencePort, never()).save(any(DeviceWaitlist.class));
    }

    @Test
    void addToWaitlistForDevice_NoWaitlistForDevice_CreatesNewWaitlist() {
        SerialNumber serialNumber = SerialNumber.of("S9SN123");
        User user = User.of("user1");

        when(persistencePort.isDeviceAvailable(serialNumber)).thenReturn(false);
        when(persistencePort.findBySerialNumber(serialNumber)).thenReturn(Optional.empty());

        deviceWaitlistService.addToWaitlistForDevice(serialNumber, user);

        verify(persistencePort).findBySerialNumber(serialNumber);
        verify(persistencePort).save(any(DeviceWaitlist.class));
    }

    @Test
    void addToWaitlistForDevice_WaitlistExists_AddsUserToWaitlist() {
        SerialNumber serialNumber = SerialNumber.of("S9SN123");
        User user = User.of("user1");
        DeviceWaitlist existingWaitlist = DeviceWaitlist.newInstance(serialNumber);

        when(persistencePort.isDeviceAvailable(serialNumber)).thenReturn(false);
        when(persistencePort.findBySerialNumber(serialNumber)).thenReturn(Optional.of(existingWaitlist));

        deviceWaitlistService.addToWaitlistForDevice(serialNumber, user);

        verify(persistencePort).findBySerialNumber(serialNumber);
        verify(persistencePort).save(existingWaitlist);
    }

    @Test
    void removeFromWaitlistForDevice_WaitlistExists_RemovesUserFromWaitlist() {
        SerialNumber serialNumber = SerialNumber.of("S9SN123");
        User user = User.of("user1");
        DeviceWaitlist existingWaitlist = DeviceWaitlist.newInstance(serialNumber);
        existingWaitlist.addToWaitlist(user);

        when(persistencePort.findBySerialNumber(serialNumber)).thenReturn(Optional.of(existingWaitlist));

        deviceWaitlistService.removeFromWaitlistForDevice(serialNumber, user);

        verify(persistencePort).findBySerialNumber(serialNumber);
        verify(persistencePort).save(existingWaitlist);
    }

    @Test
    void removeFromWaitlistForDevice_WaitlistDoesNotExist_ThrowsException() {
        SerialNumber serialNumber = SerialNumber.of("S9SN123");
        User user = User.of("user1");

        when(persistencePort.findBySerialNumber(serialNumber)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> deviceWaitlistService.removeFromWaitlistForDevice(serialNumber, user));

        verify(persistencePort).findBySerialNumber(serialNumber);
        verify(persistencePort, never()).save(any(DeviceWaitlist.class));
    }
}
