package com.movesync.move_sync_api.infrastructure.mapper;

import com.movesync.move_sync_api.application.dto.in.registroactividad.RegistroActividadRequestDTO;
import com.movesync.move_sync_api.application.dto.out.registroactividad.RegistroActividadResponseDTO;
import com.movesync.move_sync_api.domain.entity.RegistroActividad;

public class RegistroActividadMapper {

    public static RegistroActividad toEntity(RegistroActividadRequestDTO dto) {
        return RegistroActividad.builder()
                .idUsuario(dto.getIdUsuario())
                .idActividad(dto.getIdActividad())
                .idEvento(dto.getIdEvento()) // puede ser null
                .fecha(dto.getFecha())
                .duracion(dto.getDuracion())
                .distancia(dto.getDistancia()) // puede ser null
                .caloriasEstimadas(dto.getCaloriasEstimadas())
                .caloriasAlcanzadas(dto.getCaloriasAlcanzadas())
                .frecuenciaCardiacaPromedio(dto.getFrecuenciaCardiacaPromedio()) // puede ser null
                .notas(dto.getNotas()) // puede ser null
                .build();
    }

    public static RegistroActividadResponseDTO toResponse(RegistroActividad entity) {
        return RegistroActividadResponseDTO.builder()
                .idRegistro(entity.getIdRegistro())
                .idUsuario(entity.getIdUsuario())
                .idActividad(entity.getIdActividad())
                .idEvento(entity.getIdEvento())
                .fecha(entity.getFecha())
                .duracion(entity.getDuracion())
                .distancia(entity.getDistancia())
                .caloriasEstimadas(entity.getCaloriasEstimadas())
                .caloriasAlcanzadas(entity.getCaloriasAlcanzadas())
                .frecuenciaCardiacaPromedio(entity.getFrecuenciaCardiacaPromedio())
                .notas(entity.getNotas())
                .build();
    }
}