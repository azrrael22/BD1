package com.movesync.move_sync_api.application.dto.out.registroactividad;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.movesync.move_sync_api.infrastructurecross.Constants;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
public class RegistroActividadResponseDTO {
    private String idRegistro;
    private String idUsuario;
    private String idActividad;
    private String idEvento;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN)
    private LocalDateTime fecha;

    private LocalTime duracion;
    private Double distancia;
    private Integer caloriasEstimadas;
    private Integer caloriasAlcanzadas;
    private Integer frecuenciaCardiacaPromedio;
    private String notas;
}