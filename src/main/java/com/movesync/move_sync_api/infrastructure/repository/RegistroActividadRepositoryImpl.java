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
import java.math.BigDecimal;
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
            List<RegistroActividad> result = jdbcTemplate.query(sql, new RegistroActividadRowMapper());
            System.out.println("✅ findAll() ejecutado correctamente. Registros encontrados: " + result.size());
            return result;
        } catch (Exception e) {
            System.err.println("❌ ERROR en findAll():");
            System.err.println("   Mensaje: " + e.getMessage());
            System.err.println("   Tipo: " + e.getClass().getName());
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public RegistroActividad findById(String idRegistro) {
        String sql = "SELECT * FROM registro_actividad WHERE id_registro = ?";
        try {
            RegistroActividad result = jdbcTemplate.queryForObject(sql, new RegistroActividadRowMapper(), idRegistro);
            System.out.println("✅ findById(" + idRegistro + ") ejecutado correctamente");
            return result;
        } catch (Exception e) {
            System.err.println("❌ ERROR en findById(" + idRegistro + "):");
            System.err.println("   Mensaje: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<RegistroActividad> findByUsuario(String idUsuario) {
        String sql = "SELECT * FROM registro_actividad WHERE id_usuario = ? ORDER BY fecha DESC";
        try {
            List<RegistroActividad> result = jdbcTemplate.query(sql, new RegistroActividadRowMapper(), idUsuario);
            System.out.println("findByUsuario(" + idUsuario + ") ejecutado correctamente. Registros: " + result.size());
            return result;
        } catch (Exception e) {
            System.err.println("ERROR en findByUsuario(" + idUsuario + "):");
            System.err.println("   Mensaje: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public List<RegistroActividad> findByActividad(String idActividad) {
        String sql = "SELECT * FROM registro_actividad WHERE id_actividad = ? ORDER BY fecha DESC";
        try {
            List<RegistroActividad> result = jdbcTemplate.query(sql, new RegistroActividadRowMapper(), idActividad);
            System.out.println("findByActividad(" + idActividad + ") ejecutado correctamente. Registros: " + result.size());
            return result;
        } catch (Exception e) {
            System.err.println("ERROR en findByActividad(" + idActividad + "):");
            System.err.println("   Mensaje: " + e.getMessage());
            e.printStackTrace();
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
        try {
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
            System.out.println("save() ejecutado correctamente. ID: " + registro.getIdRegistro());
        } catch (Exception e) {
            System.err.println("ERROR en save():");
            System.err.println("   Mensaje: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
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
        try {
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
            System.out.println("update() ejecutado correctamente. ID: " + registro.getIdRegistro());
        } catch (Exception e) {
            System.err.println("ERROR en update():");
            System.err.println("   Mensaje: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void deleteById(String idRegistro) {
        String sql = "DELETE FROM registro_actividad WHERE id_registro = ?";
        try {
            jdbcTemplate.update(sql, idRegistro);
            System.out.println("deleteById(" + idRegistro + ") ejecutado correctamente");
        } catch (Exception e) {
            System.err.println("ERROR en deleteById(" + idRegistro + "):");
            System.err.println("   Mensaje: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private static class RegistroActividadRowMapper implements RowMapper<RegistroActividad> {
        @Override
        public RegistroActividad mapRow(ResultSet rs, int rowNum) throws SQLException {
            try {
                // 🔧 CORRECCIÓN: Convertir BigDecimal a Double para campos NUMERIC
                BigDecimal distanciaBD = rs.getBigDecimal("distancia");
                Double distancia = (distanciaBD != null) ? distanciaBD.doubleValue() : null;

                return RegistroActividad.builder()
                        .idRegistro(rs.getString("id_registro"))
                        .idUsuario(rs.getString("id_usuario"))
                        .idActividad(rs.getString("id_actividad"))
                        .idEvento(rs.getString("id_evento"))
                        .fecha(rs.getTimestamp("fecha").toLocalDateTime())
                        .duracion(rs.getTime("duracion").toLocalTime())
                        .distancia(distancia)
                        .caloriasEstimadas(rs.getInt("calorias_estimadas"))
                        .caloriasAlcanzadas(rs.getInt("calorias_alcanzadas"))
                        .frecuenciaCardiacaPromedio(rs.getObject("frecuencia_cardiaca_promedio", Integer.class))
                        .notas(rs.getString("notas"))
                        .build();
            } catch (Exception e) {
                System.err.println("ERROR en RegistroActividadRowMapper.mapRow():");
                System.err.println("   Fila: " + rowNum);
                System.err.println("   Mensaje: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }
    }
}