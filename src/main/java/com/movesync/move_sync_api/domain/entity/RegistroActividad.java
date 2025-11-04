package com.movesync.move_sync_api.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.movesync.move_sync_api.infrastructurecross.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistroActividad {
    private String idRegistro;
    private String idUsuario;
    private String idActividad;
    private String idEvento; // Puede ser null

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN)
    private LocalDateTime fecha;

    private LocalTime duracion;
    private Double distancia; // Puede ser null
    private Integer caloriasEstimadas;
    private Integer caloriasAlcanzadas;
    private Integer frecuenciaCardiacaPromedio; // Puede ser null
    private String notas; // Puede ser null
}
