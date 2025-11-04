package com.movesync.move_sync_api.infrastructure.repository;

import com.movesync.move_sync_api.application.port.output.IRegistroActividadRepository;
import com.movesync.move_sync_api.domain.entity.RegistroActividad;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public class RegistroActividadRepositoryImpl implements IRegistroActividadRepository {

    private final JdbcTemplate jdbcTemplate;

    public RegistroActividadRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<RegistroActividad> findAll() {
        String sql = "SELECT * FROM registro_actividad ORDER BY fecha DESC";
        try {
            return jdbcTemplate.query(sql, new RegistroActividadRowMapper());
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public RegistroActividad findById(String idRegistro) {
        String sql = "SELECT * FROM registro_actividad WHERE id_registro = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new RegistroActividadRowMapper(), idRegistro);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<RegistroActividad> findByUsuario(String idUsuario) {
        String sql = "SELECT * FROM registro_actividad WHERE id_usuario = ? ORDER BY fecha DESC";
        try {
            return jdbcTemplate.query(sql, new RegistroActividadRowMapper(), idUsuario);
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public List<RegistroActividad> findByActividad(String idActividad) {
        String sql = "SELECT * FROM registro_actividad WHERE id_actividad = ? ORDER BY fecha DESC";
        try {
            return jdbcTemplate.query(sql, new RegistroActividadRowMapper(), idActividad);
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public void save(RegistroActividad registro) {
        if (registro.getIdRegistro() == null || registro.getIdRegistro().isBlank()) {
            registro.setIdRegistro(UUID.randomUUID().toString());
        }

        String sql = """
                INSERT INTO registro_actividad
                (id_registro, id_usuario, id_actividad, id_evento, fecha, duracion,
                 distancia, calorias_estimadas, calorias_alcanzadas, frecuencia_cardiaca_promedio, notas)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        jdbcTemplate.update(sql,
                registro.getIdRegistro(),
                registro.getIdUsuario(),
                registro.getIdActividad(),
                registro.getIdEvento(), // Puede ser null
                Timestamp.valueOf(registro.getFecha()),
                Time.valueOf(registro.getDuracion()),
                registro.getDistancia(), // Puede ser null
                registro.getCaloriasEstimadas(),
                registro.getCaloriasAlcanzadas(),
                registro.getFrecuenciaCardiacaPromedio(), // Puede ser null
                registro.getNotas() // Puede ser null
        );
    }

    @Override
    public void update(RegistroActividad registro) {
        String sql = """
                UPDATE registro_actividad
                SET id_usuario = ?, id_actividad = ?, id_evento = ?, fecha = ?, duracion = ?,
                    distancia = ?, calorias_estimadas = ?, calorias_alcanzadas = ?, 
                    frecuencia_cardiaca_promedio = ?, notas = ?
                WHERE id_registro = ?
                """;
        jdbcTemplate.update(sql,
                registro.getIdUsuario(),
                registro.getIdActividad(),
                registro.getIdEvento(),
                Timestamp.valueOf(registro.getFecha()),
                Time.valueOf(registro.getDuracion()),
                registro.getDistancia(),
                registro.getCaloriasEstimadas(),
                registro.getCaloriasAlcanzadas(),
                registro.getFrecuenciaCardiacaPromedio(),
                registro.getNotas(),
                registro.getIdRegistro()
        );
    }

    @Override
    public void deleteById(String idRegistro) {
        String sql = "DELETE FROM registro_actividad WHERE id_registro = ?";
        jdbcTemplate.update(sql, idRegistro);
    }

    private static class RegistroActividadRowMapper implements RowMapper<RegistroActividad> {
        @Override
        public RegistroActividad mapRow(ResultSet rs, int rowNum) throws SQLException {
            return RegistroActividad.builder()
                    .idRegistro(rs.getString("id_registro"))
                    .idUsuario(rs.getString("id_usuario"))
                    .idActividad(rs.getString("id_actividad"))
                    .idEvento(rs.getString("id_evento"))
                    .fecha(rs.getTimestamp("fecha").toLocalDateTime())
                    .duracion(rs.getTime("duracion").toLocalTime())
                    .distancia(rs.getObject("distancia", Double.class))
                    .caloriasEstimadas(rs.getInt("calorias_estimadas"))
                    .caloriasAlcanzadas(rs.getInt("calorias_alcanzadas"))
                    .frecuenciaCardiacaPromedio(rs.getObject("frecuencia_cardiaca_promedio", Integer.class))
                    .notas(rs.getString("notas"))
                    .build();
        }
    }
}
