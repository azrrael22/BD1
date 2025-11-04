package com.movesync.move_sync_api.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.movesync.move_sync_api.infrastructurecross.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate; // NUEVO

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerfilSalud {
    private String idPerfil;
    private String idUsuario;

    // NUEVO
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATA_PATTERN)
    private LocalDate fecha;

    private String nivelActividad;
    private String gastoEnergetico;
    private String condicionCardiovascular;
    private String imc;
    private String balanceEnergetico;

    // NUEVO
    private Integer presionSistolica;

    // NUEVO
    private Integer presionDiastolica;

    // NUEVO
    private Integer frecuenciaCardiaca;
}