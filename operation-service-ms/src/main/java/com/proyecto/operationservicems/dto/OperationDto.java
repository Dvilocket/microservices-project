package com.proyecto.operationservicems.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationDto {
    private int id;
    private int vehicleId;
    private String customerName;
    private OperationStatus status;
}
