package kz.company.testdevicemanager.booking.adapter.out.persistence;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Table("BOOKINGS")
class Booking {
    @Id
    private Long id;
    private String serialNumber;
    private LocalDate bookedDate;
    private LocalDate returnedDate;
    private String bookedBy;
}
