package kz.company.testdevicemanager.notification.application.domain.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents information about a notification and its recipients.
 */
public class NotificationInfo {

    @Getter
    private String message;
    private List<RecipientInfo> recipientInfoList;

    /**
     * Constructs a NotificationInfo object with the specified message.
     *
     * @param message The message of the notification.
     */
    public NotificationInfo(String message) {
        this.message = message;
        this.recipientInfoList = new ArrayList<>();
    }

    /**
     * Adds a new recipient to the notification.
     *
     * @param recipientInfo The recipient information to add.
     */
    public void addNewRecipient(RecipientInfo recipientInfo) {
        if (recipientInfo != null) {
            recipientInfoList.add(recipientInfo);
        }
    }

    /**
     * Retrieves a list of all recipients of the notification.
     *
     * @return The list of recipient information.
     */
    public List<RecipientInfo> getAllRecipients() {
        return Collections.unmodifiableList(recipientInfoList);
    }
}
