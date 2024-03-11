package kz.company.testdevicemanager.notification.application.domain.model;

import kz.company.testdevicemanager.common.valueobject.User;

/**
 * Represents information about a notification recipient.
 */
public record RecipientInfo(User recipient, NotificationType notificationType, String contactInfo) {
    
    /**
     * Factory method to create RecipientInfo with push notification.
     *
     * @param recipient The recipient's identifier.
     * @return RecipientInfo object with push notification.
     */
    public static RecipientInfo withPushNotification(User recipient) {
        return new RecipientInfo(recipient, NotificationType.PUSH, null);
    }

    /**
     * Factory method to create RecipientInfo with email notification.
     *
     * @param recipient The recipient's identifier.
     * @param email     The recipient's email address.
     * @return RecipientInfo object with email notification.
     */
    public static RecipientInfo withEmailNotification(User recipient, String email) {
        return new RecipientInfo(recipient, NotificationType.EMAIL, email);
    }

    /**
     * Factory method to create RecipientInfo with SMS notification.
     *
     * @param recipient The recipient's identifier.
     * @param phone     The recipient's phone number.
     * @return RecipientInfo object with SMS notification.
     */
    public static RecipientInfo withSmsNotification(User recipient, String phone) {
        return new RecipientInfo(recipient, NotificationType.SMS, phone);
    }
}
