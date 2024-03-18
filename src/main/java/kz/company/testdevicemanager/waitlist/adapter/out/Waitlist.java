package kz.company.testdevicemanager.waitlist.adapter.out;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("DEVICE_WAITLIST")
class Waitlist {
    @Id
    private Long id;
    private String serialNumber;
    private String username;

    public Waitlist(String serialNumber, String username) {
        this.serialNumber = serialNumber;
        this.username = username;
    }
}
