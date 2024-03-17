package kz.company.testdevicemanager.notification.adapter.out;

import kz.company.testdevicemanager.notification.application.domain.model.RecipientInfo;
import kz.company.testdevicemanager.common.valueobject.SerialNumber;
import kz.company.testdevicemanager.common.valueobject.User;
import kz.company.testdevicemanager.notification.application.domain.model.NotificationInfo;
import kz.company.testdevicemanager.notification.application.port.out.LoadNotificationInfoPort;
import kz.company.testdevicemanager.waitlist.adapter.out.DeviceWaitlistRepository;
import kz.company.testdevicemanager.waitlist.adapter.out.Waitlist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Provides functionality to load notification information for a given device.
 */
@Service
@RequiredArgsConstructor
class NotificationInfoProvider implements LoadNotificationInfoPort {

    private final DeviceWaitlistRepository deviceWaitlistRepository;

    /**
     * Retrieves notification information for the specified device.
     *
     * @param serialNumber The serial number of the device.
     * @return NotificationInfo containing information about notifications for the device.
     */
    @Override
    public NotificationInfo loadNotificationInfoOfDevice(SerialNumber serialNumber) {
        List<Waitlist> deviceWaitlist = deviceWaitlistRepository.findBySerialNumber(serialNumber.value());
        return createNotificationInfo(serialNumber, deviceWaitlist);
    }

    private NotificationInfo createNotificationInfo(SerialNumber serialNumber, List<Waitlist> deviceWaitlist) {
        String message = String.format("Device with serial number %s is available", serialNumber.value());
        NotificationInfo notificationInfo = new NotificationInfo(message);
        for (Waitlist waitlist : deviceWaitlist) {
            RecipientInfo recipientInfo = createRecipientInfo(waitlist);
            notificationInfo.addNewRecipient(recipientInfo);
        }
        return notificationInfo;
    }

    private RecipientInfo createRecipientInfo(Waitlist waitlist) {
        //TODO: Implement logic to determine the notification type
        // Currently, the default is set to PUSH notification

        User user = User.of(waitlist.getUsername());
        return RecipientInfo.withPushNotification(user);
    }
}
