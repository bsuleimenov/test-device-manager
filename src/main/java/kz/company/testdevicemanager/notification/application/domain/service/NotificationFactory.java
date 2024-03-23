package kz.company.testdevicemanager.notification.application.domain.service;

import kz.company.testdevicemanager.notification.application.domain.model.NotificationType;
import kz.company.testdevicemanager.notification.application.port.out.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Factory class for obtaining the appropriate NotificationService based on the notification type.
 */
@Component
@RequiredArgsConstructor
class NotificationFactory {

    private final List<NotificationService> notificationServices;

    /**
     * Retrieves the NotificationService implementation based on the given notification type.
     *
     * @param type The type of notification.
     * @return NotificationService implementation corresponding to the given type.
     */
    public Optional<NotificationService> getNotification(NotificationType type) {
        return notificationServices.stream().filter(service -> service.getType().equals(type)).findFirst();
    }
}
