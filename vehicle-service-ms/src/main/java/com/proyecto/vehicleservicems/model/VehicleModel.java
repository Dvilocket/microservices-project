package com.proyecto.vehicleservicems.model;

import lombok.*;
import com.proyecto.vehicleservicems.dto.Status;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleModel {

    private Long id;
    private String brand;
    private String model;
    private Integer year;
    private String plate;
    private Status status;
    private LocalDateTime createdAt;
}