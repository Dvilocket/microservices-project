package com.proyecto.operationservicems.mapper;

import com.proyecto.operationservicems.dto.OperationStatus;
import org.springframework.jdbc.core.RowMapper;
import com.proyecto.operationservicems.dto.OperationDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OperationRowMapper implements RowMapper<OperationDto> {
    @Override
    public OperationDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return OperationDto.builder()
                .id(rs.getInt("id"))
                .vehicleId(rs.getInt("vehicle_id"))
                .customerName(rs.getString("customer_name"))
                .status(OperationStatus.valueOf(rs.getString("status")))
                .build();
    }
}
