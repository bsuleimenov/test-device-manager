package kz.company.testdevicemanager.booking.adapter.out.persistence;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Device} entities.
 */
interface DeviceRepository extends CrudRepository<Device, Long> {

    Optional<Device> findBySerialNumber(String serialNumber);

    @Query("SELECT * FROM devices WHERE available = true")
    List<Device> findAllAvailable();

    @Modifying
    @Query("UPDATE devices SET available = :available, booking_id = :bookingId WHERE id = :deviceId")
    void updateAvailabilityAndBookingId(Long deviceId, boolean available, Long bookingId);
}
