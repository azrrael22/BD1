package com.movesync.move_sync_api.application.dto.in.registroactividad;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.movesync.move_sync_api.infrastructurecross.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class RegistroActividadRequestDTO {

    @NotBlank(message = "El id de usuario no puede estar vacío")
    private String idUsuario;

    @NotBlank(message = "El id de actividad no puede estar vacío")
    private String idActividad;

    // Opcional - puede ser null si no está asociado a un evento
    private String idEvento;

    @NotNull(message = "La fecha no puede estar vacía")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN)
    private LocalDateTime fecha;

    @NotNull(message = "La duración no puede estar vacía")
    private LocalTime duracion;

    // Opcional
    @PositiveOrZero(message = "La distancia debe ser 0 o mayor")
    private Double distancia;

    @NotNull(message = "Las calorías estimadas no pueden estar vacías")
    @PositiveOrZero(message = "Las calorías estimadas deben ser 0 o mayor")
    private Integer caloriasEstimadas;

    @NotNull(message = "Las calorías alcanzadas no pueden estar vacías")
    @PositiveOrZero(message = "Las calorías alcanzadas deben ser 0 o mayor")
    private Integer caloriasAlcanzadas;

    // Opcional
    @PositiveOrZero(message = "La frecuencia cardíaca debe ser 0 o mayor")
    private Integer frecuenciaCardiacaPromedio;

    // Opcional
    private String notas;
}