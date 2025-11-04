package com.movesync.move_sync_api.infrastructure.mapper;

import com.movesync.move_sync_api.application.dto.in.evento.EventoRequestDTO;
import com.movesync.move_sync_api.application.dto.out.evento.EventoResponseDTO;
import com.movesync.move_sync_api.domain.entity.Evento;

public class EventoMapper {

    public static Evento toEntity(EventoRequestDTO dto) {
        return Evento.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion()) // NUEVO
                .fecha(dto.getFecha())
                .duracion(dto.getDuracion())
                .ubicacion(dto.getUbicacion()) // NUEVO
                .capacidadMaxima(dto.getCapacidadMaxima()) // NUEVO
                .estado(dto.getEstado()) // NUEVO - puede ser null, se establece en el servicio
                .build();
    }

    public static EventoResponseDTO toResponse(Evento entity) {
        return EventoResponseDTO.builder()
                .idEvento(entity.getIdEvento())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion()) // NUEVO
                .fecha(entity.getFecha())
                .duracion(entity.getDuracion())
                .ubicacion(entity.getUbicacion()) // NUEVO
                .capacidadMaxima(entity.getCapacidadMaxima()) // NUEVO
                .estado(entity.getEstado()) // NUEVO
                .build();
    }
}