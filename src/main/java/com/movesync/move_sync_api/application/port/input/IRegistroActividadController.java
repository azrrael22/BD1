package com.movesync.move_sync_api.application.port.input;

import com.movesync.move_sync_api.application.dto.ApiResponse;
import com.movesync.move_sync_api.application.dto.in.registroactividad.RegistroActividadRequestDTO;
import com.movesync.move_sync_api.application.dto.out.registroactividad.RegistroActividadResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IRegistroActividadController {
    ResponseEntity<ApiResponse<List<RegistroActividadResponseDTO>>> listarRegistros();
    ResponseEntity<ApiResponse<RegistroActividadResponseDTO>> obtenerPorId(@PathVariable String idRegistro);
    ResponseEntity<ApiResponse<List<RegistroActividadResponseDTO>>> obtenerPorUsuario(@PathVariable String idUsuario);
    ResponseEntity<ApiResponse<List<RegistroActividadResponseDTO>>> obtenerPorActividad(@PathVariable String idActividad);
    ResponseEntity<ApiResponse<RegistroActividadResponseDTO>> registrarActividad(@Valid @RequestBody RegistroActividadRequestDTO request);
    ResponseEntity<ApiResponse<RegistroActividadResponseDTO>> actualizarRegistro(@PathVariable String id, @Valid @RequestBody RegistroActividadRequestDTO request);
    ResponseEntity<ApiResponse<Void>> eliminarRegistro(@PathVariable String id);
}