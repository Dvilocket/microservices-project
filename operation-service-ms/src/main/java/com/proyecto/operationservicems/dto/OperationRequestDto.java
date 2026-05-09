package com.proyecto.operationservicems.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationRequestDto {

    @NotNull(message = "Vehicle ID is required")
    @Min(value = 1, message = "Vehicle ID must be greater than 0")
    private Integer vehicleId;

    @NotBlank(message = "Customer Name is required")
    private String customerName;
}
