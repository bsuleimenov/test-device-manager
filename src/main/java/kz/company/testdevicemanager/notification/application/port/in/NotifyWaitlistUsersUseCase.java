package kz.company.testdevicemanager.notification.application.port.in;

import kz.company.testdevicemanager.common.valueobject.SerialNumber;

public interface NotifyWaitlistUsersUseCase {
    void notifyWaitlistUsersOfDevice(SerialNumber serialNumber);
}
