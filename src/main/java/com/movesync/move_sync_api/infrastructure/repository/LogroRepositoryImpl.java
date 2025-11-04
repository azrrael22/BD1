package com.movesync.move_sync_api.infrastructure.repository;

import com.movesync.move_sync_api.application.port.output.ILogroRepository;
import com.movesync.move_sync_api.domain.entity.Logro;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp; // NUEVO
import java.time.LocalDateTime; // NUEVO
import java.util.List;
import java.util.UUID;

@Repository
public class LogroRepositoryImpl implements ILogroRepository {

    private final JdbcTemplate jdbcTemplate;

    public LogroRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Logro> findAll() {
        String sql = "SELECT * FROM logro ORDER BY fecha_obtenido DESC"; // CAMBIADO
        try {
            return jdbcTemplate.query(sql, new LogroRowMapper());
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public Logro findById(String idLogro) {
        String sql = "SELECT * FROM logro WHERE id_logro = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new LogroRowMapper(), idLogro);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Logro> findByUsuario(String idUsuario) {
        String sql = "SELECT * FROM logro WHERE id_usuario = ? ORDER BY fecha_obtenido DESC"; // CAMBIADO
        try {
            return jdbcTemplate.query(sql, new LogroRowMapper(), idUsuario);
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public void save(Logro logro) {
        // Genera el id si no existe
        if (logro.getIdLogro() == null || logro.getIdLogro().isBlank()) {
            logro.setIdLogro(UUID.randomUUID().toString());
        }

        // Establecer fecha de obtención si no existe
        if (logro.getFechaObtenido() == null) {
            logro.setFechaObtenido(LocalDateTime.now());
        }

        // Establecer puntos por defecto si no existe
        if (logro.getPuntos() == null) {
            logro.setPuntos(0);
        }

        String sql = """
                INSERT INTO logro
                (id_logro, id_usuario, nombre, recompensa, descripcion, 
                 tipo, fecha_obtenido, puntos)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        jdbcTemplate.update(sql,
                logro.getIdLogro(),
                logro.getIdUsuario(),
                logro.getNombre(),
                logro.getRecompensa(),
                logro.getDescripcion(),
                logro.getTipo(),
                Timestamp.valueOf(logro.getFechaObtenido()), // NUEVO
                logro.getPuntos() // NUEVO
        );
    }

    @Override
    public void update(Logro logro) {
        String sql = """
                UPDATE logro
                SET nombre = ?, recompensa = ?, descripcion = ?, tipo = ?, 
                    id_usuario = ?, puntos = ?
                WHERE id_logro = ?
                """;
        jdbcTemplate.update(sql,
                logro.getNombre(),
                logro.getRecompensa(),
                logro.getDescripcion(),
                logro.getTipo(),
                logro.getIdUsuario(),
                logro.getPuntos(), // NUEVO
                logro.getIdLogro()
        );
        // Nota: fecha_obtenido no se actualiza porque es cuando se obtuvo originalmente
    }

    @Override
    public void deleteById(String idLogro) {
        String sql = "DELETE FROM logro WHERE id_logro = ?";
        jdbcTemplate.update(sql, idLogro);
    }

    private static class LogroRowMapper implements RowMapper<Logro> {
        @Override
        public Logro mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Logro.builder()
                    .idLogro(rs.getString("id_logro"))
                    .idUsuario(rs.getString("id_usuario"))
                    .nombre(rs.getString("nombre"))
                    .recompensa(rs.getString("recompensa"))
                    .descripcion(rs.getString("descripcion"))
                    .tipo(rs.getString("tipo"))
                    .fechaObtenido(rs.getTimestamp("fecha_obtenido").toLocalDateTime()) // NUEVO
                    .puntos(rs.getInt("puntos")) // NUEVO
                    .build();
        }
    }
}