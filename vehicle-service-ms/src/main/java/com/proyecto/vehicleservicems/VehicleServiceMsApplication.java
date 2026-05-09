package com.proyecto.vehicleservicems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class VehicleServiceMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehicleServiceMsApplication.class, args);
    }

}
