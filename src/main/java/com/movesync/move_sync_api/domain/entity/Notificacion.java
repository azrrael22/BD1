package com.movesync.move_sync_api.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.movesync.move_sync_api.infrastructurecross.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime; // Cambiar de LocalDate

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notificacion {
    private String idNotificacion;
    private String mensaje;
    private String idUsuario;

    // Cambiar de LocalDate a LocalDateTime
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN)
    private LocalDateTime fecha;

    // NUEVO
    private Boolean leida; // DEFAULT FALSE

    // NUEVO
    private String tipo;
}