package kz.company.testdevicemanager.notification.adapter.out.persistence;

import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
}