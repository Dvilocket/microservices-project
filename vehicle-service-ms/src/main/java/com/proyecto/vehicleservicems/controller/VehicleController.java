package com.proyecto.vehicleservicems.controller;

import com.proyecto.vehicleservicems.dto.*;
import com.proyecto.vehicleservicems.service.VehicleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/api/vehicles"})
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping({"/seed/{count}"})
    public ResponseEntity<Void> seedData(@PathVariable @Min(1) @Max(20) int count) {
        this.vehicleService.seedDatabase(count);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<VehiclesDto>> getAllVehicles() {
        return ResponseEntity.ok(this.vehicleService.getAllVehicles());
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<VehiclesDto> getVehicleById(@PathVariable int id) {
        return ResponseEntity.ok(this.vehicleService.getVehicleById(id));
    }

    @PostMapping()
    public ResponseEntity<VehiclesDto> createVehicle(@RequestBody @Valid VehiclesPostDto vehiclesPostDto) {
        return ResponseEntity.ok(this.vehicleService.saveVehicle(vehiclesPostDto));
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<VehiclesDto> updateVehicle(@PathVariable int id, @RequestBody @Valid VehiclePutDto vehiclePutDto) {
        return ResponseEntity.ok(this.vehicleService.putVehicle(id, vehiclePutDto));
    }

    @GetMapping({"/search"})
    public ResponseEntity<List<VehiclesDto>> searchVehicles(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Status status
    ) {
        this.vehicleService.searchVehicles(brand, model, status);
        return ResponseEntity.ok(this.vehicleService.searchVehicles(brand, model, status));
    }

    @GetMapping({"/{id}/availability"})
    public ResponseEntity<AvailabilityResponse> isAvailable(@PathVariable int id) {
        return ResponseEntity.ok(this.vehicleService.isVehicleAvailable(id));
    }

    @PatchMapping({"/{id}/status"})
    public ResponseEntity<StatusResponseDto> updateStatus(
            @PathVariable int id,
            @RequestParam Status status
    ) {
        return ResponseEntity.ok(this.vehicleService.updateStatus(id, status));
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<String> deleteVehicle(@PathVariable int id) {
        return ResponseEntity.ok(this.vehicleService.deleteVehicle(id));
    }
}