package com.proyecto.vehicleservicems.service;

import com.proyecto.vehicleservicems.dto.*;
import com.proyecto.vehicleservicems.mapper.VehicleRowMapper;
import com.proyecto.vehicleservicems.model.VehicleModel;
import com.proyecto.vehicleservicems.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService{

    private  final VehicleRepository vehicleRepository;

    @Override
    public void seedDatabase(int count) {
        if (count > 0 && count <= 20 ) {

            this.vehicleRepository.executeUpdate("delete from vehicles");

            String sql = """
                    INSERT INTO vehicles (brand, model, year, plate, status, created_at)
                    VALUES (?, ?, ?, ?, CAST(? AS vehicle_status), NOW())
                """;

            IntFunction<String> generatePlate = i -> "PLATE" + System.currentTimeMillis() + i;

            String[] brands = {"Toyota", "Mazda", "Kia", "Chevrolet", "Hyundai"};
            String[] models = {"Corolla", "CX5", "Rio", "Spark", "Elantra"};

            for(int i = 0; i < count; i++) {

                VehicleModel vehicleModel = VehicleModel.builder()
                        .brand(brands[i % brands.length])
                        .model(models[i % models.length])
                        .year(200 + (i % 25))
                        .plate(generatePlate.apply(i))
                        .status(Status.AVAILABLE)
                        .build();

                this.vehicleRepository.executeUpdate(sql,
                        vehicleModel.getBrand(),
                        vehicleModel.getModel(),
                        vehicleModel.getYear(),
                        vehicleModel.getPlate(),
                        vehicleModel.getStatus().name()
                );
            }
        } else {
            throw new IllegalArgumentException("el valor es incorrecto");
        }
    }

    @Override
    public List<VehiclesDto> getAllVehicles() {
        String sql = "select id, brand, model, year, plate, status from vehicles";
        return this.vehicleRepository.executeQuery(sql, new VehicleRowMapper());
    }

    @Override
    public VehiclesDto getVehicleById(int id) {
        String sql = "select id, brand, model, year, plate, status from vehicles where id = ?";

        List<VehiclesDto> list  = this.vehicleRepository.executeQuery(sql, new VehicleRowMapper(), id);

        return list.stream()
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se encontró vehículo con id " + id
                ));
    }

    @Override
    public VehiclesDto saveVehicle(VehiclesPostDto vehiclesPostDto) {
        String sqlConsult = "SELECT * FROM vehicles WHERE plate = ?";

        List<VehiclesDto> resultConsult = this.vehicleRepository.executeQuery(
                sqlConsult,
                new VehicleRowMapper(),
                vehiclesPostDto.getPlate()
        );

        if (!resultConsult.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A vehicle with this plate already exists"
            );
        }

        String sql = """
            INSERT INTO vehicles (brand, model, year, plate, status, created_at)
            VALUES (?, ?, ?, ?, CAST(? AS vehicle_status), NOW())
            RETURNING id, brand, model, year, plate, status
        """;

        List<VehiclesDto> result = this.vehicleRepository.executeQuery(
                sql,
                new VehicleRowMapper(),
                vehiclesPostDto.getBrand(),
                vehiclesPostDto.getModel(),
                vehiclesPostDto.getYear(),
                vehiclesPostDto.getPlate(),
                Status.AVAILABLE.name()
        );

        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public VehiclesDto putVehicle(int id, VehiclePutDto vehiclePutDto) {
        String sql = "SELECT * FROM vehicles WHERE id = ?";

        List<VehiclesDto> result = this.vehicleRepository.executeQuery(
                sql,
                new VehicleRowMapper(),
                id
        );

        if (result.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Vehicle not found with id: " + id
            );
        }

        sql = "UPDATE vehicles SET brand = ?, model = ?, year = ?, plate = ? WHERE id = ?";

        this.vehicleRepository.executeUpdate(
                sql,
                vehiclePutDto.getBrand(),
                vehiclePutDto.getModel(),
                vehiclePutDto.getYear(),
                vehiclePutDto.getPlate(),
                id
        );
        return this.vehicleRepository.executeQuery(
                "SELECT * FROM vehicles WHERE id = ?",
                new VehicleRowMapper(),
                id
        ).get(0);
    }

    @Override
    public List<VehiclesDto> searchVehicles(String brand, String model, Status status) {
        StringBuilder sql = new StringBuilder("SELECT * FROM vehicles WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (brand != null && !brand.isEmpty()) {
            sql.append(" AND brand ILIKE ?");
            params.add("%" + brand + "%");
        }

        if (model != null && !model.isEmpty()) {
            sql.append(" AND model ILIKE ? ");
            params.add("%" + model + "%");
        }

        if (status != null) {
            sql.append(" AND status = CAST(? AS vehicle_status) ");
            params.add(status.name());
        }

        return this.vehicleRepository.executeQuery(
                sql.toString(),
                new VehicleRowMapper(),
                params.toArray()
        );
    }

    @Override
    public AvailabilityResponse isVehicleAvailable(int id) {
        String sql = "SELECT * FROM vehicles WHERE id = ?";

        List<VehiclesDto> result = this.vehicleRepository.executeQuery(
                sql,
                new VehicleRowMapper(),
                id
        );
        if (result.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Vehicle not found with id: " + id
            );
        }

        VehiclesDto vehicle = result.get(0);

        boolean resultComparation = "AVAILABLE".equals(vehicle.getStatus().name());

        return new AvailabilityResponse(resultComparation);
    }

    @Override
    public StatusResponseDto updateStatus(int id, Status status) {
        String sql = "SELECT * FROM vehicles WHERE id = ?";

        List<VehiclesDto> result = this.vehicleRepository.executeQuery(
                sql,
                new VehicleRowMapper(),
                id
        );

        if (result.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Vehicle not found with id: " + id
            );
        }

        sql = "UPDATE vehicles SET status = CAST(? as vehicle_status) WHERE id = ?";

        this.vehicleRepository.executeUpdate(
                sql,
                status.name(),
                id
        );

        return new StatusResponseDto(status);
    }

    @Override
    public String deleteVehicle(int id) {
        String sql = "SELECT * FROM vehicles WHERE id = ?";

        List<VehiclesDto> result = this.vehicleRepository.executeQuery(
                sql,
                new VehicleRowMapper(),
                id
        );

        if (result.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Vehicle not found with id: " + id
            );
        }

        VehiclesDto vehicleResponse = result.get(0);

        if (vehicleResponse.getStatus() == Status.NOT_AVAILABLE) {
            throw  new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "The vehicle is not available (it is already rented)."
            );
        }

        sql = "DELETE FROM vehicles WHERE id = ?";

        this.vehicleRepository.executeUpdate(sql, id);

        return "The vehicle was successfully deleted.";
    }
}
