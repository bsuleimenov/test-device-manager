package kz.company.testdevicemanager.booking.adapter.out.persistence.tables;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("DEVICES")
public class Device {
    @Id
    private Long id; // Database-generated ID
    private String serialNumber; // Unique serial number assigned by the manufacturer
    private String modelName;
    private boolean available;
    private Long bookingId;
}
