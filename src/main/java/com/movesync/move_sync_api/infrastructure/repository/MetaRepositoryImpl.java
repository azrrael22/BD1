package com.movesync.move_sync_api.infrastructure.repository;

import com.movesync.move_sync_api.application.port.output.IMetaRepository;
import com.movesync.move_sync_api.domain.entity.Meta;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp; // NUEVO
import java.time.LocalDateTime; // NUEVO
import java.util.List;
import java.util.UUID;

@Repository
public class MetaRepositoryImpl implements IMetaRepository {

    private final JdbcTemplate jdbcTemplate;

    public MetaRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Meta> findAll() {
        String sql = "SELECT * FROM meta ORDER BY fecha_creacion DESC"; // CAMBIADO
        try {
            return jdbcTemplate.query(sql, new MetaRowMapper());
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public Meta findById(String idMeta) {
        String sql = "SELECT * FROM meta WHERE id_meta = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new MetaRowMapper(), idMeta);
        } catch (Exception e) {
            return null;
        }
    }

    // NUEVO - Buscar por usuario
    public List<Meta> findByUsuario(String idUsuario) {
        String sql = "SELECT * FROM meta WHERE id_usuario = ? ORDER BY fecha_creacion DESC";
        try {
            return jdbcTemplate.query(sql, new MetaRowMapper(), idUsuario);
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public void save(Meta meta) {
        // Genera el id si no existe
        if (meta.getIdMeta() == null || meta.getIdMeta().isBlank()) {
            meta.setIdMeta(UUID.randomUUID().toString());
        }

        // Establecer fecha de creación si no existe
        if (meta.getFechaCreacion() == null) {
            meta.setFechaCreacion(LocalDateTime.now());
        }

        // Establecer estado por defecto si no existe
        if (meta.getEstado() == null || meta.getEstado().isBlank()) {
            meta.setEstado("ACTIVA");
        }

        String sql = """
                INSERT INTO meta
                (id_meta, id_usuario, fecha_inicio, fecha_fin, objetivo, 
                 perdida_calorias_diarias, estado, fecha_creacion)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        jdbcTemplate.update(sql,
                meta.getIdMeta(),
                meta.getIdUsuario(), // NUEVO
                Date.valueOf(meta.getFechaInicio()),
                Date.valueOf(meta.getFechaFin()),
                meta.getObjetivo(),
                meta.getPerdidaCaloriasDiarias(),
                meta.getEstado(), // NUEVO
                Timestamp.valueOf(meta.getFechaCreacion()) // NUEVO
        );
    }

    @Override
    public void update(Meta meta) {
        String sql = """
                UPDATE meta
                SET id_usuario = ?, fecha_inicio = ?, fecha_fin = ?, objetivo = ?, 
                    perdida_calorias_diarias = ?, estado = ?
                WHERE id_meta = ?
                """;
        jdbcTemplate.update(sql,
                meta.getIdUsuario(), // NUEVO
                Date.valueOf(meta.getFechaInicio()),
                Date.valueOf(meta.getFechaFin()),
                meta.getObjetivo(),
                meta.getPerdidaCaloriasDiarias(),
                meta.getEstado(), // NUEVO
                meta.getIdMeta()
        );
    }

    @Override
    public void deleteById(String idMeta) {
        // Borrar filas hijas que referencian a la meta antes de eliminarla
        jdbcTemplate.update("DELETE FROM historial_progreso WHERE id_meta = ?", idMeta);

        String sql = "DELETE FROM meta WHERE id_meta = ?";
        jdbcTemplate.update(sql, idMeta);
    }

    private static class MetaRowMapper implements RowMapper<Meta> {
        @Override
        public Meta mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Meta.builder()
                    .idMeta(rs.getString("id_meta"))
                    .idUsuario(rs.getString("id_usuario")) // NUEVO
                    .fechaInicio(rs.getDate("fecha_inicio").toLocalDate())
                    .fechaFin(rs.getDate("fecha_fin").toLocalDate())
                    .objetivo(rs.getString("objetivo"))
                    .perdidaCaloriasDiarias(rs.getString("perdida_calorias_diarias"))
                    .estado(rs.getString("estado")) // NUEVO
                    .fechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime()) // NUEVO
                    .build();
        }
    }
}