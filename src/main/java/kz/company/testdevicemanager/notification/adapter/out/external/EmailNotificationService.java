package kz.company.testdevicemanager.notification.adapter.out.external;

import kz.company.testdevicemanager.common.valueobject.User;
import kz.company.testdevicemanager.notification.application.domain.model.NotificationType;
import kz.company.testdevicemanager.notification.application.port.out.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * {@inheritDoc}
 */
@Slf4j
@Service
class EmailNotificationService implements NotificationService {

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendNotification(User recipient, String message) {
        //TODO: Implement email notification logic
        log.info("Successfully sent email notification to {}", recipient.username());
    }

    @Override
    public NotificationType getType() {
        return NotificationType.EMAIL;
    }
}