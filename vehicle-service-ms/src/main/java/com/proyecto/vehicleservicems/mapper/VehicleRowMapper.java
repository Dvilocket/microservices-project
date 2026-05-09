package com.proyecto.vehicleservicems.mapper;

import com.proyecto.vehicleservicems.dto.Status;
import com.proyecto.vehicleservicems.dto.VehiclesDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class VehicleRowMapper implements RowMapper<VehiclesDto> {
    @Override
    public VehiclesDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return VehiclesDto.builder()
                .id(rs.getInt("id"))
                .brand(rs.getString("brand"))
                .model(rs.getString("model"))
                .year(rs.getInt("year"))
                .plate(rs.getString("plate"))
                .status(Status.valueOf(rs.getString("status")))
                .build();
    }
}
