package com.proyecto.operationservicems.service;

import com.proyecto.operationservicems.dto.OperationDto;
import com.proyecto.operationservicems.dto.OperationRequestDto;

import java.util.List;

public interface OperationService {

    OperationDto createOperation(OperationRequestDto operationRequestDto);
    List<OperationDto> getAllOperations();
    OperationDto getOperationById(int id);
    OperationDto cancelOperation(int id);
}
