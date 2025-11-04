package com.movesync.move_sync_api.infrastructure.controller;

import com.movesync.move_sync_api.application.dto.ApiResponse;
import com.movesync.move_sync_api.application.dto.in.registroactividad.RegistroActividadRequestDTO;
import com.movesync.move_sync_api.application.dto.out.registroactividad.RegistroActividadResponseDTO;
import com.movesync.move_sync_api.application.port.input.IRegistroActividadController;
import com.movesync.move_sync_api.application.port.interactor.IRegistroActividadService;
import com.movesync.move_sync_api.domain.entity.RegistroActividad;
import com.movesync.move_sync_api.infrastructure.mapper.RegistroActividadMapper;
import com.movesync.move_sync_api.infrastructurecross.Constants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registros-actividad")
public class RegistroActividadController implements IRegistroActividadController {

    @Autowired
    private IRegistroActividadService registroActividadService;

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<List<RegistroActividadResponseDTO>>> listarRegistros() {
        List<RegistroActividad> registros = registroActividadService.listarRegistros();
        List<RegistroActividadResponseDTO> response = registros.stream()
                .map(RegistroActividadMapper::toResponse)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(Constants.REGISTRO_ACTIVIDAD_OBTENIDOS, response));
    }

    @Override
    @GetMapping("/{idRegistro}")
    public ResponseEntity<ApiResponse<RegistroActividadResponseDTO>> obtenerPorId(@PathVariable String idRegistro) {
        RegistroActividad registro = registroActividadService.obtenerPorId(idRegistro);
        RegistroActividadResponseDTO response = RegistroActividadMapper.toResponse(registro);
        return ResponseEntity.ok(ApiResponse.success(Constants.REGISTRO_ACTIVIDAD_OBTENIDO, response));
    }

    @Override
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<ApiResponse<List<RegistroActividadResponseDTO>>> obtenerPorUsuario(@PathVariable String idUsuario) {
        List<RegistroActividad> registros = registroActividadService.obtenerPorUsuario(idUsuario);
        List<RegistroActividadResponseDTO> response = registros.stream()
                .map(RegistroActividadMapper::toResponse)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(Constants.REGISTRO_ACTIVIDAD_OBTENIDOS, response));
    }

    @Override
    @GetMapping("/actividad/{idActividad}")
    public ResponseEntity<ApiResponse<List<RegistroActividadResponseDTO>>> obtenerPorActividad(@PathVariable String idActividad) {
        List<RegistroActividad> registros = registroActividadService.obtenerPorActividad(idActividad);
        List<RegistroActividadResponseDTO> response = registros.stream()
                .map(RegistroActividadMapper::toResponse)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(Constants.REGISTRO_ACTIVIDAD_OBTENIDOS, response));
    }

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<RegistroActividadResponseDTO>> registrarActividad(
            @Valid @RequestBody RegistroActividadRequestDTO request) {
        RegistroActividad registro = RegistroActividadMapper.toEntity(request);
        registroActividadService.registrarActividad(registro);
        RegistroActividadResponseDTO response = RegistroActividadMapper.toResponse(registro);
        return ResponseEntity.ok(ApiResponse.success(Constants.REGISTRO_ACTIVIDAD_REGISTRADO, response));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RegistroActividadResponseDTO>> actualizarRegistro(
            @PathVariable String id,
            @Valid @RequestBody RegistroActividadRequestDTO request) {
        RegistroActividad registro = RegistroActividadMapper.toEntity(request);
        registro.setIdRegistro(id);
        registroActividadService.actualizarRegistro(registro);
        RegistroActividadResponseDTO response = RegistroActividadMapper.toResponse(registro);
        return ResponseEntity.ok(ApiResponse.success(Constants.REGISTRO_ACTIVIDAD_ACTUALIZADO, response));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarRegistro(@PathVariable String id) {
        registroActividadService.eliminarRegistro(id);
        return ResponseEntity.ok(ApiResponse.success(Constants.REGISTRO_ACTIVIDAD_ELIMINADO, null));
    }
}