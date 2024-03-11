package kz.company.testdevicemanager.waitlist.adapter.in.event;

import kz.company.testdevicemanager.common.event.NotificationSent;
import kz.company.testdevicemanager.waitlist.application.port.in.DeviceWaitlistUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Event listener responsible for handling {@link NotificationSent} events.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationSentHandler {

    private final DeviceWaitlistUseCase deviceWaitlistUseCase;

    /**
     * Asynchronously handles the {@link NotificationSent} event by removing the user from the waitlist for the device.
     *
     * @param event The {@link NotificationSent} event.
     */
    @Async
    @EventListener
    public void handleNotificationSentEvent(NotificationSent event) {
        log.info("Received event: {}, about to remove user from waitlist for device", event);
        try {
            deviceWaitlistUseCase.removeFromWaitlistForDevice(event.serialNumber(), event.recipient());
        } catch (Exception e) {
            log.error("Error occurred while removing user '{}' from the waitlist for device with serial number '{}': {}",
                    event.recipient(), event.serialNumber(), e.getMessage());
        }
    }
}