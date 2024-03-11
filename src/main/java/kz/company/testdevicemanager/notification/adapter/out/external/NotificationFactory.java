package kz.company.testdevicemanager.notification.adapter.out.external;

import kz.company.testdevicemanager.notification.application.domain.model.NotificationType;
import kz.company.testdevicemanager.notification.application.port.out.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Factory class for obtaining the appropriate NotificationService based on the notification type.
 */
@Component
@RequiredArgsConstructor
public class NotificationFactory {

    private final SmsNotificationService smsNotificationService;
    private final EmailNotificationService emailNotificationService;
    private final PushNotificationService pushNotificationService;
    // Add other services as needed for the new notification types

    /**
     * Retrieves the NotificationService implementation based on the given notification type.
     *
     * @param type The type of notification.
     * @return NotificationService implementation corresponding to the given type.
     * @throws IllegalArgumentException If the provided notification type is not supported.
     */
    public NotificationService getNotification(NotificationType type) {
        switch (type) {
            case SMS:
                return smsNotificationService;
            case EMAIL:
                return emailNotificationService;
            case PUSH:
                return pushNotificationService;
            // Add cases for other notification types and their corresponding services
            default:
                throw new IllegalArgumentException("Unsupported notification type: " + type);
        }
    }
}
