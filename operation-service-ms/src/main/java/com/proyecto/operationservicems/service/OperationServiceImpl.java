package com.proyecto.operationservicems.service;

import com.proyecto.operationservicems.dto.OperationDto;
import com.proyecto.operationservicems.dto.OperationRequestDto;
import com.proyecto.operationservicems.dto.OperationStatus;
import com.proyecto.operationservicems.mapper.OperationRowMapper;
import com.proyecto.operationservicems.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final RestTemplate restTemplate;

    @Override
    public OperationDto createOperation(OperationRequestDto operationRequestDto) {

        String availabilityUrl = "http://VEHICLE-SERVICE-MS/api/vehicles/" + operationRequestDto.getVehicleId() + "/availability";

        Map<String, Boolean> response = this.restTemplate.getForObject(availabilityUrl, Map.class);

        assert response != null;

        Boolean available = response.get("available");

        if (Boolean.FALSE.equals(available)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Vehicle not available"
            );
        }


        System.out.println("entro por aqui");

        String updateUrl = "http://VEHICLE-SERVICE-MS/api/vehicles/" + operationRequestDto.getVehicleId() +
                "/status?status=NOT_AVAILABLE";

        this.restTemplate.exchange(updateUrl, HttpMethod.PATCH, null, Void.class);

        System.out.println("salio por aqui");

        String sql = "INSERT INTO operations (vehicle_id, customer_name, status) VALUES (?, ?, CAST(? AS operation_status)) RETURNING *";

        List<OperationDto> operations = this.operationRepository.executeQuery(
                sql,
                new OperationRowMapper(),
                operationRequestDto.getVehicleId(),
                operationRequestDto.getCustomerName(),
                OperationStatus.ACTIVE.name()
        );
        return operations.get(0);
    }

    @Override
    public List<OperationDto> getAllOperations() {
        String sql = "SELECT * FROM operations";
        return this.operationRepository.executeQuery(sql, new OperationRowMapper());
    }

    @Override
    public OperationDto getOperationById(int id) {

        String sql = "SELECT * FROM operations WHERE id = ?";

        List<OperationDto> operations =
                this.operationRepository.executeQuery(
                        sql,
                        new OperationRowMapper(),
                        id
                );

        if (operations.isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Operation not found"
            );
        }

        return operations.get(0);
    }

    @Override
    public OperationDto cancelOperation(int id) {

        String findSql = "SELECT * FROM operations WHERE id = ?";

        List<OperationDto> operations =
                this.operationRepository.executeQuery(
                        findSql,
                        new OperationRowMapper(),
                        id
                );

        if (operations.isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Operation not found"
            );
        }

        OperationDto operation = operations.get(0);

        String updateVehicleUrl = "http://VEHICLE-SERVICE-MS/api/vehicles/" + operation.getVehicleId() + "/status?status=AVAILABLE";

        this.restTemplate.exchange(
                updateVehicleUrl,
                HttpMethod.PATCH,
                null,
                Void.class
        );

        String updateSql = """
            UPDATE operations
            SET status = CAST(? AS operation_status)
            WHERE id = ?
            RETURNING *
            """;

        List<OperationDto> updatedOperations =
                this.operationRepository.executeQuery(
                        updateSql,
                        new OperationRowMapper(),
                        OperationStatus.CANCELLED.name(),
                        id
                );

        return updatedOperations.get(0);
    }
}
