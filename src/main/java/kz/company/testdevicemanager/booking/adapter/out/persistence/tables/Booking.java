package kz.company.testdevicemanager.booking.adapter.out.persistence.tables;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Table("BOOKINGS")
public class Booking {
    @Id
    private Long id;
    private String serialNumber;
    private LocalDate bookedDate;
    private LocalDate returnedDate;
    private String bookedBy;
}
