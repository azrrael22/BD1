package com.movesync.move_sync_api.application.port.output;

import com.movesync.move_sync_api.domain.entity.RegistroActividad;

import java.util.List;

public interface IRegistroActividadRepository {
    List<RegistroActividad> findAll();
    RegistroActividad findById(String idRegistro);
    List<RegistroActividad> findByUsuario(String idUsuario);
    List<RegistroActividad> findByActividad(String idActividad);
    void save(RegistroActividad registro);
    void update(RegistroActividad registro);
    void deleteById(String idRegistro);
}
