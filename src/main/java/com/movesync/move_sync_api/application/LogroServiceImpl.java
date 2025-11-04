package com.movesync.move_sync_api.application;

import com.movesync.move_sync_api.application.port.interactor.ILogroService;
import com.movesync.move_sync_api.application.port.output.ILogroRepository;
import com.movesync.move_sync_api.domain.entity.Logro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogroServiceImpl implements ILogroService {

    @Autowired
    private ILogroRepository logroRepository;

    @Override
    public List<Logro> listarLogros() {
        return logroRepository.findAll();
    }

    @Override
    public Logro obtenerPorId(String idLogro) {
        return logroRepository.findById(idLogro);
    }

    @Override
    public List<Logro> obtenerPorUsuario(String idUsuario) {
        return logroRepository.findByUsuario(idUsuario);
    }

    @Override
    public void registrarLogro(Logro logro) {
        validarLogro(logro);

        // NUEVO - Establecer fecha de obtención
        if (logro.getFechaObtenido() == null) {
            logro.setFechaObtenido(LocalDateTime.now());
        }

        // NUEVO - Establecer puntos por defecto si no se proporciona
        if (logro.getPuntos() == null) {
            logro.setPuntos(0);
        }

        logroRepository.save(logro);
    }

    @Override
    public void actualizarLogro(Logro logro) {
        validarLogro(logro);
        logroRepository.update(logro);
    }

    @Override
    public void eliminarLogro(String idLogro) {
        logroRepository.deleteById(idLogro);
    }

    private void validarLogro(Logro logro) {
        if (logro.getNombre() == null || logro.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del logro es obligatorio.");
        }
        if (logro.getRecompensa() == null || logro.getRecompensa().isBlank()) {
            throw new IllegalArgumentException("La recompensa es obligatoria.");
        }
        if (logro.getDescripcion() == null || logro.getDescripcion().isBlank()) {
            throw new IllegalArgumentException("La descripción es obligatoria.");
        }
        if (logro.getTipo() == null || logro.getTipo().isBlank()) {
            throw new IllegalArgumentException("El tipo es obligatorio.");
        }
        if (logro.getIdUsuario() == null || logro.getIdUsuario().isBlank()) {
            throw new IllegalArgumentException("El id de usuario es obligatorio.");
        }

        // NUEVO - Validar tipo
        validarTipo(logro.getTipo());

        // NUEVO - Validar puntos si se proporcionan
        if (logro.getPuntos() != null && logro.getPuntos() < 0) {
            throw new IllegalArgumentException("Los puntos deben ser 0 o mayor.");
        }
    }

    // NUEVO - Validar tipos permitidos
    private void validarTipo(String tipo) {
        List<String> tiposPermitidos = List.of("ACTIVIDAD", "CONSISTENCIA", "HABILIDAD", "SOCIAL", "META");
        if (!tiposPermitidos.contains(tipo)) {
            throw new IllegalArgumentException("Tipo inválido. Los tipos permitidos son: ACTIVIDAD, CONSISTENCIA, HABILIDAD, SOCIAL, META");
        }
    }
}