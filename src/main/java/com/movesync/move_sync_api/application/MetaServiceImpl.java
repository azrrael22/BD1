package com.movesync.move_sync_api.application;

import com.movesync.move_sync_api.application.port.interactor.IMetaService;
import com.movesync.move_sync_api.application.port.output.IMetaRepository;
import com.movesync.move_sync_api.domain.entity.Meta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MetaServiceImpl implements IMetaService {

    @Autowired
    private IMetaRepository metaRepository;

    @Override
    public List<Meta> listarMetas() {
        return metaRepository.findAll();
    }

    @Override
    public Meta obtenerPorId(String idMeta) {
        return metaRepository.findById(idMeta);
    }

    // NUEVO - Obtener metas por usuario
    @Override
    public List<Meta> obtenerPorUsuario(String idUsuario) {
        return metaRepository.findByUsuario(idUsuario);
    }

    @Override
    public void registrarMeta(Meta meta) {
        validarMeta(meta);

        // NUEVO - Establecer estado por defecto si no se proporciona
        if (meta.getEstado() == null || meta.getEstado().isBlank()) {
            meta.setEstado("ACTIVA");
        }

        // NUEVO - Establecer fecha de creación
        if (meta.getFechaCreacion() == null) {
            meta.setFechaCreacion(LocalDateTime.now());
        }

        metaRepository.save(meta);
    }

    @Override
    public void actualizarMeta(Meta meta) {
        validarMeta(meta);

        // NUEVO - Validar estado si se proporciona
        if (meta.getEstado() != null && !meta.getEstado().isBlank()) {
            validarEstado(meta.getEstado());
        }

        metaRepository.update(meta);
    }

    @Override
    public void eliminarMeta(String idMeta) {
        metaRepository.deleteById(idMeta);
    }

    private void validarMeta(Meta meta) {
        // NUEVO - Validar idUsuario
        if (meta.getIdUsuario() == null || meta.getIdUsuario().isBlank()) {
            throw new IllegalArgumentException("El id de usuario es obligatorio.");
        }

        if (meta.getFechaInicio() == null) {
            throw new IllegalArgumentException("La fecha de inicio es obligatoria.");
        }
        if (meta.getFechaFin() == null) {
            throw new IllegalArgumentException("La fecha fin es obligatoria.");
        }
        if (meta.getFechaFin().isBefore(meta.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha fin no puede ser anterior a la fecha de inicio.");
        }
        if (meta.getObjetivo() == null || meta.getObjetivo().isBlank()) {
            throw new IllegalArgumentException("El objetivo es obligatorio.");
        }
    }

    // NUEVO - Validar estados permitidos
    private void validarEstado(String estado) {
        List<String> estadosPermitidos = List.of("ACTIVA", "COMPLETADA", "CANCELADA");
        if (!estadosPermitidos.contains(estado)) {
            throw new IllegalArgumentException("Estado inválido. Los estados permitidos son: ACTIVA, COMPLETADA, CANCELADA");
        }
    }
}