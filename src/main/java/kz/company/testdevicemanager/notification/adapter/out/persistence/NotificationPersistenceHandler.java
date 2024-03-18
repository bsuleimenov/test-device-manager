package kz.company.testdevicemanager.notification.adapter.out.persistence;

import kz.company.testdevicemanager.common.event.NotificationSent;
import kz.company.testdevicemanager.common.valueobject.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Handles the persistence of notifications triggered by {@link NotificationSent} events.
 */
@Slf4j
@Component
@RequiredArgsConstructor
class NotificationPersistenceHandler {

    private final NotificationRepository notificationRepository;

    /**
     * Asynchronously handles the {@link NotificationSent} event by persisting the notification data.
     *
     * @param event The {@link NotificationSent} event.
     */
    @Async
    @EventListener
    public void handleNotificationSentEvent(NotificationSent event) {
        try {
            Notification notification = createNotification(event.recipient(), event.message());
            notificationRepository.save(notification);
            log.info("Notification saved successfully for recipient: {}", event.recipient().username());
        } catch (Exception e) {
            log.error("Error occurred while handling NotificationSent event: {}", e.getMessage());
        }
    }

    /**
     * Creates a new {@link Notification} object.
     *
     * @param recipient The recipient of the notification.
     * @param message   The message content of the notification.
     * @return The created {@link Notification} object.
     */
    private Notification createNotification(User recipient, String message) {
        Notification notification = new Notification();
        notification.setRecipient(recipient.username());
        notification.setMessage(message);
        notification.setSentAt(LocalDateTime.now());
        return notification;
    }
}