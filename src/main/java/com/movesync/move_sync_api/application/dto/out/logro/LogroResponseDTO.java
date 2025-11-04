package com.movesync.move_sync_api.application.dto.out.logro;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.movesync.move_sync_api.infrastructurecross.Constants;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LogroResponseDTO {
    private String idLogro;
    private String nombre;
    private String recompensa;
    private String descripcion;
    private String tipo;
    private String idUsuario;

    // NUEVO
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN)
    private LocalDateTime fechaObtenido;

    // NUEVO
    private Integer puntos;
}
