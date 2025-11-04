package com.movesync.move_sync_api.application.dto.out.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.movesync.move_sync_api.infrastructurecross.Constants;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class UsuarioResponseDTO {
    private String idUsuario;
    private String cedula;
    private String primerNombre;
    private String primerApellido;
    private String correo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATA_PATTERN)
    private LocalDate fechaNacimiento;

    // NUEVO - Opcional, puede incluirse o no según necesidad
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN)
    private LocalDateTime fechaRegistro;
}