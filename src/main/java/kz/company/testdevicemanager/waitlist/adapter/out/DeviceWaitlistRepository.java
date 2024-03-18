package kz.company.testdevicemanager.waitlist.adapter.out;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

interface DeviceWaitlistRepository extends CrudRepository<Waitlist, Long> {

    List<Waitlist> findBySerialNumber(String serialNumber);

    @Modifying
    @Query("DELETE FROM device_waitlist w WHERE w.serial_number = :serialNumber AND w.username IN (:usernames)")
    void deleteBySerialNumberAndUserIn(@Param("serialNumber") String serialNumber, @Param("usernames") Set<String> usernames);

}
