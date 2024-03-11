CREATE TABLE bookings (
     id BIGINT PRIMARY KEY AUTO_INCREMENT,
     serial_number VARCHAR(255) NOT NULL,
     booked_date DATE NOT NULL,
     returned_date DATE,
     booked_by VARCHAR(255) NOT NULL
);

CREATE TABLE devices (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    serial_number VARCHAR(255) UNIQUE NOT NULL,
    model_name VARCHAR(255) NOT NULL,
    available BOOLEAN DEFAULT true,
    booking_id BIGINT,
    CONSTRAINT fk_booking FOREIGN KEY (booking_id) REFERENCES bookings(id)
);