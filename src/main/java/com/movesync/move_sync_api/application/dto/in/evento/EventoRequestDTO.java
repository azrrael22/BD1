package com.movesync.move_sync_api.application.dto.in.evento;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.movesync.move_sync_api.infrastructurecross.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class EventoRequestDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    // NUEVO
    private String descripcion;

    @NotNull(message = "La fecha no puede estar vacía")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN)
    private LocalDateTime fecha;

    @NotNull(message = "La duración no puede estar vacía")
    private LocalTime duracion;

    // NUEVO
    private String ubicacion;

    // NUEVO
    @Positive(message = "La capacidad máxima debe ser mayor a 0")
    private Integer capacidadMaxima;

    // NUEVO - Opcional (se establece por defecto en el servicio)
    private String estado; // PROGRAMADO, EN_CURSO, FINALIZADO, CANCELADO
}