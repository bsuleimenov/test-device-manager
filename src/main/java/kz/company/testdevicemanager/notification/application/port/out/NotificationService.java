package kz.company.testdevicemanager.notification.application.port.out;

import kz.company.testdevicemanager.common.valueobject.User;

/**
 * Service responsible for sending notifications
 */
public interface NotificationService {

    /**
     * Sends a notification to the specified recipient.
     *
     * @param recipient The recipient of the notification.
     * @param message   The content of the notification message.
     */
    void sendNotification(User recipient, String message);
}