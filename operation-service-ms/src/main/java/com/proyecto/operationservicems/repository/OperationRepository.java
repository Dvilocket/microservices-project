package com.proyecto.operationservicems.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OperationRepository {
    private final JdbcTemplate jdbcTemplate;

    public void executeUpdate(String sql, Object... params) {
        this.jdbcTemplate.update(sql, params);
    }

    public <T> List<T> executeQuery(String sql, RowMapper<T> mapper, Object... params) {
        return this.jdbcTemplate.query(sql, mapper, params);
    }
}
