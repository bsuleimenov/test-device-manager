package kz.company.testdevicemanager.notification.application.domain.service;

import kz.company.testdevicemanager.common.event.NotificationSent;
import kz.company.testdevicemanager.common.valueobject.SerialNumber;
import kz.company.testdevicemanager.notification.adapter.out.external.NotificationFactory;
import kz.company.testdevicemanager.notification.application.domain.model.NotificationInfo;
import kz.company.testdevicemanager.notification.application.domain.model.RecipientInfo;
import kz.company.testdevicemanager.notification.application.port.in.NotifyWaitlistUsersUseCase;
import kz.company.testdevicemanager.notification.application.port.out.LoadNotificationInfoPort;
import kz.company.testdevicemanager.notification.application.port.out.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * A service responsible for notifying waitlisted users that the device is now available for booking.
 */
@Slf4j
@Service
@RequiredArgsConstructor
class NotifyWaitlistUsersService implements NotifyWaitlistUsersUseCase {

    private final LoadNotificationInfoPort loadNotificationInfoPort;
    private final NotificationFactory notificationFactory;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Notifies all waitlisted users of a device based on its serial number.
     *
     * @param serialNumber The serial number of the device.
     */
    @Override
    public void notifyWaitlistUsersOfDevice(SerialNumber serialNumber) {
        NotificationInfo notificationInfo = loadNotificationInfoPort.loadNotificationInfoOfDevice(serialNumber);
        if (notificationInfo.hasRecipientsToNotify()) {
            notifyWaitlistUsers(serialNumber, notificationInfo);
        } else {
            log.info("No users found to notify in the waitlist for device '{}'", serialNumber.value());
        }
    }

    /**
     * Notifies waitlist users based on the provided notification information.
     *
     * @param serialNumber    The serial number of the device.
     * @param notificationInfo The notification information.
     */
    private void notifyWaitlistUsers(SerialNumber serialNumber, NotificationInfo notificationInfo) {
        List<RecipientInfo> allRecipients = notificationInfo.getAllRecipients();
        for (RecipientInfo recipientInfo : allRecipients) {
            NotificationService notificationService = notificationFactory.getNotification(recipientInfo.notificationType());
            notificationService.sendNotification(recipientInfo.recipient(), notificationInfo.getMessage());

            eventPublisher.publishEvent(
                    NotificationSent.of(serialNumber, recipientInfo.recipient(), notificationInfo.getMessage()));
        }
    }
}
