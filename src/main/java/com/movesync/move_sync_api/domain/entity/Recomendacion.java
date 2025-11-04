package com.movesync.move_sync_api.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.movesync.move_sync_api.infrastructurecross.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime; // NUEVO

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recomendacion {
    private String idRecomendacion;
    private String mensaje;
    private String idUsuario;

    // NUEVO
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN)
    private LocalDateTime fecha;

    // NUEVO
    private String tipo;

    // NUEVO
    private String prioridad; // BAJA, MEDIA, ALTA
}
