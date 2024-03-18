package kz.company.testdevicemanager.notification.adapter.out.persistence;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("NOTIFICATION")
class Notification {
    @Id
    private Long id;
    private String recipient;
    private String message;
    private LocalDateTime sentAt;
}
