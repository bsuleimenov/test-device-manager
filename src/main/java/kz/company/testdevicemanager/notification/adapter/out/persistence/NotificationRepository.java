package kz.company.testdevicemanager.notification.adapter.out.persistence;

import org.springframework.data.repository.CrudRepository;

interface NotificationRepository extends CrudRepository<Notification, Long> {
}
