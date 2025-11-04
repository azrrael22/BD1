package com.movesync.move_sync_api.application.port.interactor;

import com.movesync.move_sync_api.domain.entity.RegistroActividad;

import java.util.List;

public interface IRegistroActividadService {
    List<RegistroActividad> listarRegistros();
    RegistroActividad obtenerPorId(String idRegistro);
    List<RegistroActividad> obtenerPorUsuario(String idUsuario);
    List<RegistroActividad> obtenerPorActividad(String idActividad);
    void registrarActividad(RegistroActividad registro);
    void actualizarRegistro(RegistroActividad registro);
    void eliminarRegistro(String idRegistro);
}