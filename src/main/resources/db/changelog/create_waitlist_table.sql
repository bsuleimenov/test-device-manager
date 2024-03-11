CREATE TABLE DEVICE_WAITLIST (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   serial_number VARCHAR(255) NOT NULL,
   username VARCHAR(255) NOT NULL,
   CONSTRAINT fk_serial_number FOREIGN KEY (serial_number) REFERENCES devices(serial_number)
);

create unique index ix_serial_number_username on DEVICE_WAITLIST (serial_number,username);