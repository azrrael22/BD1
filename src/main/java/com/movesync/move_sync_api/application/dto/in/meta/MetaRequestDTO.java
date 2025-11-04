package com.movesync.move_sync_api.application.dto.in.meta;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.movesync.move_sync_api.infrastructurecross.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MetaRequestDTO {

    // NUEVO - CRÍTICO
    @NotBlank(message = "El id de usuario no puede estar vacío")
    private String idUsuario;

    @NotNull(message = "La fecha de inicio no puede estar vacía")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATA_PATTERN)
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha fin no puede estar vacía")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATA_PATTERN)
    private LocalDate fechaFin;

    @NotBlank(message = "El objetivo no puede estar vacío")
    private String objetivo;

    private String perdidaCaloriasDiarias;

    // NUEVO - Opcional (se establece por defecto en el servicio)
    private String estado; // ACTIVA, COMPLETADA, CANCELADA
}
