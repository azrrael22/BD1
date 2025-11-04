package com.movesync.move_sync_api.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.movesync.move_sync_api.infrastructurecross.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime; // NUEVO

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Meta {
    private String idMeta;

    private String idUsuario; // NUEVO - faltaba en la entidad original

    private LocalDate fechaInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATA_PATTERN)
    private LocalDate fechaFin;

    private String objetivo;

    private String perdidaCaloriasDiarias;

    // NUEVO
    private String estado; // ACTIVA, COMPLETADA, CANCELADA

    // NUEVO
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN)
    private LocalDateTime fechaCreacion;
}
