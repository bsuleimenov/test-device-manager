package kz.company.testdevicemanager.notification.application.domain.service;

import kz.company.testdevicemanager.common.event.NotificationSent;
import kz.company.testdevicemanager.common.valueobject.SerialNumber;
import kz.company.testdevicemanager.common.valueobject.User;
import kz.company.testdevicemanager.notification.adapter.out.external.NotificationFactory;
import kz.company.testdevicemanager.notification.application.domain.model.NotificationInfo;
import kz.company.testdevicemanager.notification.application.domain.model.NotificationType;
import kz.company.testdevicemanager.notification.application.domain.model.RecipientInfo;
import kz.company.testdevicemanager.notification.application.port.out.LoadNotificationInfoPort;
import kz.company.testdevicemanager.notification.application.port.out.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.Mockito.*;

class NotifyWaitlistUsersServiceTest {
    @Mock
    private LoadNotificationInfoPort loadNotificationInfoPort;
    @Mock
    private NotificationFactory notificationFactory;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @InjectMocks
    private NotifyWaitlistUsersService notifyWaitlistUsersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void notifyWaitlistUsersOfDevice_Success() {
        SerialNumber serialNumber = SerialNumber.of("S9SN123");
        String message = String.format("Device with serial number %s is available", serialNumber.value());
        NotificationInfo notificationInfo = new NotificationInfo(message);
        RecipientInfo recipientInfo = RecipientInfo.withPushNotification(User.of("recipient"));
        notificationInfo.addNewRecipient(recipientInfo);

        when(loadNotificationInfoPort.loadNotificationInfoOfDevice(serialNumber)).thenReturn(notificationInfo);

        NotificationService notificationService = mock(NotificationService.class);
        when(notificationFactory.getNotification(NotificationType.PUSH)).thenReturn(notificationService);

        notifyWaitlistUsersService.notifyWaitlistUsersOfDevice(serialNumber);

        verify(loadNotificationInfoPort, times(1)).loadNotificationInfoOfDevice(serialNumber);
        verify(notificationFactory, times(1)).getNotification(NotificationType.PUSH);
        verify(notificationService, times(1)).sendNotification(recipientInfo.recipient(), notificationInfo.getMessage());
        verify(eventPublisher, times(1)).publishEvent(NotificationSent.of(serialNumber, recipientInfo.recipient()));
    }

    @Test
    void notifyWaitlistUsersOfDevice_WaitlistUsers() {
        SerialNumber serialNumber = SerialNumber.of("S9SN123");
        String message = String.format("Device with serial number %s is available", serialNumber.value());
        NotificationInfo notificationInfo = new NotificationInfo(message);

        when(loadNotificationInfoPort.loadNotificationInfoOfDevice(serialNumber)).thenReturn(notificationInfo);

        notifyWaitlistUsersService.notifyWaitlistUsersOfDevice(serialNumber);

        verify(loadNotificationInfoPort, times(1)).loadNotificationInfoOfDevice(serialNumber);
        verifyNoMoreInteractions(notificationFactory);
        verifyNoMoreInteractions(eventPublisher);
    }
}