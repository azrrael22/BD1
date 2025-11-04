-- Usuarios
INSERT INTO usuario (id_usuario, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido, cedula, peso, estatura, genero, contrasena, correo, fecha_nacimiento)
VALUES
    ('u1', 'Carlos', 'Andrés', 'Pérez', 'Gómez', 'V12345678', 78.50, 175, 'M', 'pass1', 'carlos.perez@example.com', '1990-05-12'),
    ('u2', 'María', 'Lucía', 'Rodríguez', NULL, 'V87654321', 62.30, 165, 'F', 'pass2', 'maria.rodriguez@example.com', '1992-11-23'),
    ('u3', 'Alex', NULL, 'Torres', 'Ruiz', 'V11223344', 85.00, 182, 'O', 'pass3', 'alex.torres@example.com', '1985-07-03'),
    ('u4', 'Ana', NULL, 'Lopez', NULL, 'V55667788', 70.10, 168, 'F', 'pass4', 'ana.lopez@example.com', '1995-02-17');

-- Tipo de actividad
INSERT INTO tipo_actividad (id_tipo, nombre, descripcion, calorias_promedio_por_hora)
VALUES
    ('t1', 'Correr', 'Carrera al aire libre o en cinta', 600),
    ('t2', 'Yoga', 'Ejercicios de flexibilidad y respiración', 220),
    ('t3', 'Ciclismo', 'Bicicleta al aire libre o estática', 500);

-- Actividades
INSERT INTO actividad (id_actividad, id_tipo, nombre, descripcion, duracion_estimada_minutos, nivel_dificultad)
VALUES
    ('a1', 't1', 'Carrera 5K', 'Carrera continua de 5 kilómetros', 30, 'INTERMEDIO'),
    ('a2', 't2', 'Yoga Hatha 60', 'Sesión de Yoga Hatha de 60 minutos', 60, 'PRINCIPIANTE'),
    ('a3', 't3', 'Ciclismo 30', 'Entrenamiento de ciclismo indoor 30 min', 30, 'INTERMEDIO');

-- Eventos
INSERT INTO evento (id_evento, nombre, descripcion, fecha, duracion, ubicacion, capacidad_maxima, estado)
VALUES
    ('e1', 'Carrera Comunitaria 5K', 'Evento anual de la comunidad', '2025-06-15 08:00:00', '02:00:00', 'Parque Central', 200, 'PROGRAMADO'),
    ('e2', 'Clase de Yoga Matutina', 'Clase abierta de yoga', '2025-03-20 07:30:00', '01:00:00', 'Centro Deportivo', 30, 'PROGRAMADO');

-- Registro de participantes
INSERT INTO registro_participantes (id_registro_participantes, id_evento, id_usuario, estado_participacion)
VALUES
    ('rp1', 'e1', 'u1', 'CONFIRMADO'),
    ('rp2', 'e1', 'u2', 'CONFIRMADO'),
    ('rp3', 'e2', 'u4', 'PENDIENTE');

-- Metas
INSERT INTO meta (id_meta, id_usuario, fecha_inicio, fecha_fin, objetivo, perdida_calorias_diarias, estado)
VALUES
    ('m1', 'u1', '2025-01-01', '2025-03-31', 'Perder 5 kg', '500', 'ACTIVA'),
    ('m2', 'u2', '2025-02-01', '2025-05-01', 'Mejorar resistencia', '300', 'ACTIVA');

-- Historial de progreso
INSERT INTO historial_progreso (id_historial, id_usuario, id_meta, fecha, avance_objetivo, peso_actual, notas)
VALUES
    ('h1', 'u1', 'm1', '2025-01-15', '10\\% completado', 77.80, 'Buen ritmo, aumentar cardio'),
    ('h2', 'u1', 'm1', '2025-02-15', '60\\% completado', 74.50, 'Consistencia en entrenos'),
    ('h3', 'u2', 'm2', '2025-03-01', '20\\% completado', 61.90, NULL);

-- Perfil de salud
INSERT INTO perfil_salud (id_perfil, id_usuario, fecha, nivel_actividad, gasto_energetico, condicion_cardiovascular, imc, balance_energetico, presion_sistolica, presion_diastolica, frecuencia_cardiaca)
VALUES
    ('ps1', 'u1', '2025-01-10', 'Moderado', '2200 kcal/día', 'Buena', '25.5', 'Equilibrado', 120, 80, 68),
    ('ps2', 'u2', '2025-02-05', 'Bajo', '1800 kcal/día', 'Moderada', '22.8', 'Déficit ligero', 110, 70, 72);

-- Recomendaciones
INSERT INTO recomendacion (id_recomendacion, id_usuario, mensaje, tipo, prioridad)
VALUES
    ('r1', 'u1', 'Aumentar sesiones de cardio a 3x semana', 'ENTRENAMIENTO', 'ALTA'),
    ('r2', 'u2', 'Incluir proteína después de entrenar', 'NUTRICIÓN', 'MEDIA');

-- Notificaciones
INSERT INTO notificacion (id_notificacion, id_usuario, mensaje, leida, tipo)
VALUES
    ('n1', 'u1', 'Recordatorio: Carrera Comunitaria el 15 de junio', FALSE, 'EVENTO'),
    ('n2', 'u2', 'Nueva recomendación disponible', FALSE, 'SISTEMA');

-- Logros
INSERT INTO logro (id_logro, id_usuario, nombre, recompensa, descripcion, tipo, puntos)
VALUES
    ('l1', 'u1', 'Primer 5K', 'Badge 5K', 'Completar tu primera carrera 5K', 'ACTIVIDAD', 50),
    ('l2', 'u2', 'Consistencia 4 semanas', '50 puntos', 'Entrenar 4 semanas seguidas', 'CONSISTENCIA', 100);

-- Registro de actividad
INSERT INTO registro_actividad (id_registro, id_usuario, id_actividad, id_evento, fecha, duracion, distancia, calorias_estimadas, calorias_alcanzadas, frecuencia_cardiaca_promedio, notas)
VALUES
    ('ra1', 'u1', 'a1', 'e1', '2025-06-15 08:10:00', '00:28:00', 5.00, 300, 320, 150, 'Buen ritmo'),
    ('ra2', 'u4', 'a2', 'e2', '2025-03-20 07:30:00', '01:00:00', 0.00, 200, 180, 100, 'Sesión relajada'),
    ('ra3', 'u3', 'a3', NULL, '2025-02-10 18:00:00', '00:30:00', 10.00, 250, 240, 130, 'Entrenamiento indoor');
