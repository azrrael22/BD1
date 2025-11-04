package com.movesync.move_sync_api.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.movesync.move_sync_api.infrastructurecross.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime; // NUEVO

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistroParticipantes {
    private String idRegistroParticipantes;
    private String idEvento;
    private String idUsuario;

    // NUEVO
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN)
    private LocalDateTime fechaRegistro;

    // NUEVO
    private String estadoParticipacion; // CONFIRMADO, PENDIENTE, CANCELADO, ASISTIO
}
