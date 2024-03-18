package kz.company.testdevicemanager.notification.adapter.out;

import kz.company.testdevicemanager.common.valueobject.SerialNumber;
import kz.company.testdevicemanager.common.valueobject.User;
import kz.company.testdevicemanager.notification.application.domain.model.NotificationInfo;
import kz.company.testdevicemanager.notification.application.domain.model.RecipientInfo;
import kz.company.testdevicemanager.notification.application.port.out.LoadNotificationInfoPort;
import kz.company.testdevicemanager.waitlist.application.port.in.DeviceWaitlistUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Provides functionality to load notification information for a given device.
 */
@Service
@RequiredArgsConstructor
class NotificationInfoProvider implements LoadNotificationInfoPort {

    private final DeviceWaitlistUseCase deviceWaitlistUseCase;

    /**
     * Retrieves notification information for the specified device.
     *
     * @param serialNumber The serial number of the device.
     * @return NotificationInfo containing information about notifications for the device.
     */
    @Override
    public NotificationInfo loadNotificationInfoOfDevice(SerialNumber serialNumber) {
        Set<User> users = deviceWaitlistUseCase.getAllUsersFromWaitlist(serialNumber);
        return createNotificationInfo(serialNumber, users);
    }

    private NotificationInfo createNotificationInfo(SerialNumber serialNumber, Set<User> users) {
        String message = String.format("Device with serial number %s is available", serialNumber.value());
        NotificationInfo notificationInfo = new NotificationInfo(message);
        for (User user : users) {
            RecipientInfo recipientInfo = createRecipientInfo(user);
            notificationInfo.addNewRecipient(recipientInfo);
        }
        return notificationInfo;
    }

    private RecipientInfo createRecipientInfo(User user) {
        //TODO: Implement logic to determine the notification type
        // Currently, the default is set to PUSH notification

        return RecipientInfo.withPushNotification(user);
    }
}
