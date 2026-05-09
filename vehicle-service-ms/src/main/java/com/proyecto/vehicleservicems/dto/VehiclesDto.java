package com.proyecto.vehicleservicems.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehiclesDto {
    private int id;
    private String brand;
    private String model;
    private int year;
    private String plate;
    private Status status;
}