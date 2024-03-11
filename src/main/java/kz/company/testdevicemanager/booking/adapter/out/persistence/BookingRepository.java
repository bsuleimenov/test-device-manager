package kz.company.testdevicemanager.booking.adapter.out.persistence;

import kz.company.testdevicemanager.booking.adapter.out.persistence.tables.Booking;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

/**
 * Repository interface for managing {@link Booking} entities.
 */
public interface BookingRepository extends CrudRepository<Booking, Long> {

    /**
     * Updates the returned date for a booking with the given ID
     */
    @Modifying
    @Query("UPDATE bookings SET returned_date = :returnedDate WHERE id = :id")
    void updateReturnedDate(Long id, LocalDate returnedDate);
}
