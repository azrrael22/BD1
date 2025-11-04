package com.movesync.move_sync_api.infrastructure.repository;

import com.movesync.move_sync_api.application.port.output.IUsuarioRepository;
import com.movesync.move_sync_api.domain.entity.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class UsuarioRepositoryImpl implements IUsuarioRepository {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Usuario> findAll() {
        String sql = "SELECT * FROM usuario ORDER BY fecha_registro DESC";
        try {
            return jdbcTemplate.query(sql, new UsuarioRowMapper());
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public Usuario findById(String idUsuario) {
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new UsuarioRowMapper(), idUsuario);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Usuario findByCedula(String cedula) {
        String sql = "SELECT * FROM usuario WHERE cedula = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new UsuarioRowMapper(), cedula);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void save(Usuario usuario) {
        if (usuario.getIdUsuario() == null || usuario.getIdUsuario().isBlank()) {
            usuario.setIdUsuario(UUID.randomUUID().toString());
        }

        if (usuario.getFechaRegistro() == null) {
            usuario.setFechaRegistro(LocalDateTime.now());
        }

        String sql = """
                INSERT INTO usuario
                (id_usuario, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido,
                 cedula, peso, estatura, genero, contrasena, correo, fecha_nacimiento, fecha_registro)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        jdbcTemplate.update(sql,
                usuario.getIdUsuario(),
                usuario.getPrimerNombre(),
                usuario.getSegundoNombre(),
                usuario.getPrimerApellido(),
                usuario.getSegundoApellido(),
                usuario.getCedula(),
                usuario.getPeso(),
                usuario.getEstatura(),
                usuario.getGenero(),
                usuario.getContrasena(),
                usuario.getCorreo(),
                Date.valueOf(usuario.getFechaNacimiento()),
                Timestamp.valueOf(usuario.getFechaRegistro())
        );
    }

    @Override
    public void update(Usuario usuario) {
        String sql = """
                UPDATE usuario
                SET primer_nombre = ?, segundo_nombre = ?, primer_apellido = ?, segundo_apellido = ?,
                    cedula = ?, peso = ?, estatura = ?, genero = ?, contrasena = ?, correo = ?,
                    fecha_nacimiento = ?
                WHERE id_usuario = ?
                """;
        jdbcTemplate.update(sql,
                usuario.getPrimerNombre(),
                usuario.getSegundoNombre(),
                usuario.getPrimerApellido(),
                usuario.getSegundoApellido(),
                usuario.getCedula(),
                usuario.getPeso(),
                usuario.getEstatura(),
                usuario.getGenero(),
                usuario.getContrasena(),
                usuario.getCorreo(),
                Date.valueOf(usuario.getFechaNacimiento()),
                usuario.getIdUsuario()
        );
    }

    @Override
    public void deleteById(String idUsuario) {
        jdbcTemplate.update("DELETE FROM recomendacion WHERE id_usuario = ?", idUsuario);
        jdbcTemplate.update("DELETE FROM perfil_salud WHERE id_usuario = ?", idUsuario);
        jdbcTemplate.update("DELETE FROM historial_progreso WHERE id_usuario = ?", idUsuario);
        jdbcTemplate.update("DELETE FROM registro_participantes WHERE id_usuario = ?", idUsuario);
        jdbcTemplate.update("DELETE FROM registro_actividad WHERE id_usuario = ?", idUsuario);
        jdbcTemplate.update("DELETE FROM logro WHERE id_usuario = ?", idUsuario);
        jdbcTemplate.update("DELETE FROM notificacion WHERE id_usuario = ?", idUsuario);
        jdbcTemplate.update("DELETE FROM meta WHERE id_usuario = ?", idUsuario);

        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        jdbcTemplate.update(sql, idUsuario);
    }

    private static class UsuarioRowMapper implements RowMapper<Usuario> {
        @Override
        public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
            // 🔧 CORRECCIÓN: Convertir BigDecimal a Double para el campo peso (NUMERIC)
            BigDecimal pesoBD = rs.getBigDecimal("peso");
            Double peso = (pesoBD != null) ? pesoBD.doubleValue() : null;

            return Usuario.builder()
                    .idUsuario(rs.getString("id_usuario"))
                    .primerNombre(rs.getString("primer_nombre"))
                    .segundoNombre(rs.getString("segundo_nombre"))
                    .primerApellido(rs.getString("primer_apellido"))
                    .segundoApellido(rs.getString("segundo_apellido"))
                    .cedula(rs.getString("cedula"))
                    .peso(peso) // 🔧 CORREGIDO
                    .estatura(rs.getInt("estatura"))
                    .genero(rs.getString("genero"))
                    .contrasena(rs.getString("contrasena"))
                    .correo(rs.getString("correo"))
                    .fechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate())
                    .fechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime())
                    .build();
        }
    }
}