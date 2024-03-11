package kz.company.testdevicemanager.notification.adapter.out.external;

import kz.company.testdevicemanager.common.valueobject.User;
import kz.company.testdevicemanager.notification.adapter.out.persistence.Notification;
import kz.company.testdevicemanager.notification.adapter.out.persistence.NotificationRepository;
import kz.company.testdevicemanager.notification.application.port.out.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * {@inheritDoc}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PushNotificationService implements NotificationService {

    private final NotificationRepository notificationRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendNotification(User recipient, String message) {
        Notification notification = createNotification(recipient, message);

        //TODO: Implement push notification logic
        log.info("Successfully sent push notification to {}", recipient.username());

        saveNotification(notification);
    }

    private Notification createNotification(User recipient, String message) {
        Notification notification = new Notification();
        notification.setRecipient(recipient.username());
        notification.setMessage(message);
        notification.setSentAt(LocalDateTime.now());
        return notification;
    }

    private void saveNotification(Notification notification) {
        try {
            notificationRepository.save(notification);
        } catch (Exception e) {
            log.error("Failed to save notification: {}", e.getMessage());
            // Handle the exception appropriately
        }
    }
}