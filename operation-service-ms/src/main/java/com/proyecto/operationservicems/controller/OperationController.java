package com.proyecto.operationservicems.controller;

import com.proyecto.operationservicems.dto.OperationDto;
import com.proyecto.operationservicems.dto.OperationRequestDto;
import com.proyecto.operationservicems.service.OperationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping({"api/operations"})
public class OperationController {

    private final OperationService operationService;

    @PostMapping
    public OperationDto createOperation(@RequestBody @Valid OperationRequestDto operationRequestDto) {
        return this.operationService.createOperation(operationRequestDto);
    }

    @GetMapping
    public List<OperationDto> getAllOperations() {
        return this.operationService.getAllOperations();
    }

    @GetMapping({"/{id}"})
    public OperationDto getOperationById(@PathVariable int id) {
        return this.operationService.getOperationById(id);
    }

    @DeleteMapping({"/{id}"})
    public OperationDto cancelOperation(@PathVariable int id) {
        return this.operationService.cancelOperation(id);
    }
}
