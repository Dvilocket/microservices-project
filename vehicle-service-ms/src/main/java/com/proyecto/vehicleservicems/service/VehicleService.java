package com.proyecto.vehicleservicems.service;

import com.proyecto.vehicleservicems.dto.*;

import java.util.List;

public interface VehicleService {
    void seedDatabase(int count);
    List<VehiclesDto> getAllVehicles();
    VehiclesDto getVehicleById(int id);
    VehiclesDto saveVehicle(VehiclesPostDto vehiclesPostDto);
    VehiclesDto putVehicle(int id, VehiclePutDto vehiclePutDto);
    List<VehiclesDto> searchVehicles(String brand, String model, Status status);
    AvailabilityResponse isVehicleAvailable(int id);
    StatusResponseDto updateStatus(int id, Status status);
    String deleteVehicle(int id);
}

