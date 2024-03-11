CREATE TABLE notification (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   recipient VARCHAR(255) NOT NULL,
   message VARCHAR(255) NOT NULL,
   sent_at TIMESTAMP
);
