package com.movesync.move_sync_api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Actividad {
    private String idActividad;
    private String idTipo;

    // NUEVO
    private String nombre;

    private String descripcion;

    // NUEVO
    private Integer duracionEstimadaMinutos;

    // NUEVO
    private String nivelDificultad; // PRINCIPIANTE, INTERMEDIO, AVANZADO
}
