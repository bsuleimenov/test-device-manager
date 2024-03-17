package kz.company.testdevicemanager.notification.adapter.in;

import kz.company.testdevicemanager.common.event.DeviceReturned;
import kz.company.testdevicemanager.notification.application.port.in.NotifyWaitlistUsersUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Handles the DeviceReturned event by sending notifications to users from waitlist for this device.
 */
@Slf4j
@Component
@RequiredArgsConstructor
class DeviceReturnedHandler {

    private final NotifyWaitlistUsersUseCase notifyWaitlistUsersUseCase;

    /**
     * Asynchronously handles the DeviceReturned event and notifies users from waitlist.
     *
     * @param event The DeviceReturned event.
     */
    @Async
    @TransactionalEventListener
    public void handleDeviceReturnedEvent(DeviceReturned event) {
        log.info("Received event '{}'. Preparing to notify users in the device waitlist, if any.", event);
        try {
            notifyWaitlistUsersUseCase.notifyWaitlistUsersOfDevice(event.serialNumber());
        } catch (Exception e) {
            log.error("Error occurred while sending notifications for event {}: {}", event, e.getMessage());

            // Optionally, retry sending notifications
        }
    }
}