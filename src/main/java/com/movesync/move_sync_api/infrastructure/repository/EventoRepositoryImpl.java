package com.movesync.move_sync_api.infrastructure.repository;

import com.movesync.move_sync_api.application.port.output.IEventoRepository;
import com.movesync.move_sync_api.domain.entity.Evento;
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
public class EventoRepositoryImpl implements IEventoRepository {

    private final JdbcTemplate jdbcTemplate;

    public EventoRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Evento> findAll() {
        String sql = "SELECT * FROM evento ORDER BY fecha DESC";
        try {
            return jdbcTemplate.query(sql, new EventoRowMapper());
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public Evento findById(String idEvento) {
        String sql = "SELECT * FROM evento WHERE id_evento = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new EventoRowMapper(), idEvento);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void save(Evento evento) {
        // Genera el id si no existe
        if (evento.getIdEvento() == null || evento.getIdEvento().isBlank()) {
            evento.setIdEvento(UUID.randomUUID().toString());
        }

        // Establecer estado por defecto si no existe
        if (evento.getEstado() == null || evento.getEstado().isBlank()) {
            evento.setEstado("PROGRAMADO");
        }

        String sql = """
                INSERT INTO evento
                (id_evento, nombre, descripcion, fecha, duracion, 
                 ubicacion, capacidad_maxima, estado)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        jdbcTemplate.update(sql,
                evento.getIdEvento(),
                evento.getNombre(),
                evento.getDescripcion(), // NUEVO
                Timestamp.valueOf(evento.getFecha()),
                Time.valueOf(evento.getDuracion()),
                evento.getUbicacion(), // NUEVO
                evento.getCapacidadMaxima(), // NUEVO
                evento.getEstado() // NUEVO
        );
    }

    @Override
    public void update(Evento evento) {
        String sql = """
                UPDATE evento
                SET nombre = ?, descripcion = ?, fecha = ?, duracion = ?, 
                    ubicacion = ?, capacidad_maxima = ?, estado = ?
                WHERE id_evento = ?
                """;
        jdbcTemplate.update(sql,
                evento.getNombre(),
                evento.getDescripcion(), // NUEVO
                Timestamp.valueOf(evento.getFecha()),
                Time.valueOf(evento.getDuracion()),
                evento.getUbicacion(), // NUEVO
                evento.getCapacidadMaxima(), // NUEVO
                evento.getEstado(), // NUEVO
                evento.getIdEvento()
        );
    }

    @Override
    public void deleteById(String idEvento) {
        // Borrar filas hijas que referencian al evento antes de eliminarlo
        jdbcTemplate.update("DELETE FROM registro_actividad WHERE id_evento = ?", idEvento); // ACTUALIZADO
        jdbcTemplate.update("DELETE FROM registro_participantes WHERE id_evento = ?", idEvento);

        String sql = "DELETE FROM evento WHERE id_evento = ?";
        jdbcTemplate.update(sql, idEvento);
    }

    private static class EventoRowMapper implements RowMapper<Evento> {
        @Override
        public Evento mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Evento.builder()
                    .idEvento(rs.getString("id_evento"))
                    .nombre(rs.getString("nombre"))
                    .descripcion(rs.getString("descripcion")) // NUEVO
                    .fecha(rs.getTimestamp("fecha").toLocalDateTime())
                    .duracion(rs.getTime("duracion").toLocalTime())
                    .ubicacion(rs.getString("ubicacion")) // NUEVO
                    .capacidadMaxima(rs.getObject("capacidad_maxima", Integer.class)) // NUEVO
                    .estado(rs.getString("estado")) // NUEVO
                    .build();
        }
    }
}