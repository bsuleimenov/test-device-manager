package kz.company.testdevicemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class TestDeviceManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestDeviceManagerApplication.class, args);
    }

}
