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
public class Logro {
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
    private Integer puntos; // DEFAULT 0
}
