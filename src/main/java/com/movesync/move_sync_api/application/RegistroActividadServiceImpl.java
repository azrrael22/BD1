package com.movesync.move_sync_api.application;

import com.movesync.move_sync_api.application.port.interactor.IRegistroActividadService;
import com.movesync.move_sync_api.application.port.output.IRegistroActividadRepository;
import com.movesync.move_sync_api.domain.entity.RegistroActividad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroActividadServiceImpl implements IRegistroActividadService {

    @Autowired
    private IRegistroActividadRepository registroActividadRepository;

    @Override
    public List<RegistroActividad> listarRegistros() {
        return registroActividadRepository.findAll();
    }

    @Override
    public RegistroActividad obtenerPorId(String idRegistro) {
        return registroActividadRepository.findById(idRegistro);
    }

    @Override
    public List<RegistroActividad> obtenerPorUsuario(String idUsuario) {
        return registroActividadRepository.findByUsuario(idUsuario);
    }

    @Override
    public List<RegistroActividad> obtenerPorActividad(String idActividad) {
        return registroActividadRepository.findByActividad(idActividad);
    }

    @Override
    public void registrarActividad(RegistroActividad registro) {
        validarRegistro(registro);
        registroActividadRepository.save(registro);
    }

    @Override
    public void actualizarRegistro(RegistroActividad registro) {
        validarRegistro(registro);
        registroActividadRepository.update(registro);
    }

    @Override
    public void eliminarRegistro(String idRegistro) {
        registroActividadRepository.deleteById(idRegistro);
    }

    private void validarRegistro(RegistroActividad registro) {
        if (registro.getIdUsuario() == null || registro.getIdUsuario().isBlank()) {
            throw new IllegalArgumentException("El id de usuario es obligatorio.");
        }
        if (registro.getIdActividad() == null || registro.getIdActividad().isBlank()) {
            throw new IllegalArgumentException("El id de actividad es obligatorio.");
        }
        if (registro.getFecha() == null) {
            throw new IllegalArgumentException("La fecha es obligatoria.");
        }
        if (registro.getDuracion() == null) {
            throw new IllegalArgumentException("La duración es obligatoria.");
        }
        if (registro.getCaloriasEstimadas() == null || registro.getCaloriasEstimadas() < 0) {
            throw new IllegalArgumentException("Las calorías estimadas son obligatorias y deben ser 0 o mayor.");
        }
        if (registro.getCaloriasAlcanzadas() == null || registro.getCaloriasAlcanzadas() < 0) {
            throw new IllegalArgumentException("Las calorías alcanzadas son obligatorias y deben ser 0 o mayor.");
        }

        // Validaciones opcionales
        if (registro.getDistancia() != null && registro.getDistancia() < 0) {
            throw new IllegalArgumentException("La distancia debe ser 0 o mayor.");
        }
        if (registro.getFrecuenciaCardiacaPromedio() != null && registro.getFrecuenciaCardiacaPromedio() < 0) {
            throw new IllegalArgumentException("La frecuencia cardíaca debe ser 0 o mayor.");
        }
    }
}