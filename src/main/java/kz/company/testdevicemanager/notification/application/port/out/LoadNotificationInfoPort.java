package kz.company.testdevicemanager.notification.application.port.out;

import kz.company.testdevicemanager.common.valueobject.SerialNumber;
import kz.company.testdevicemanager.notification.application.domain.model.NotificationInfo;

public interface LoadNotificationInfoPort {

    NotificationInfo loadNotificationInfoOfDevice(SerialNumber serialNumber);
}
