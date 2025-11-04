-- =============================================
-- MOVESYNC DATABASE - VERSIÓN CORREGIDA
-- Sistema de Gestión de Fitness y Salud
-- PostgreSQL 15+
-- =============================================

-- =============================================
-- LIMPIEZA PREVIA (Solo para desarrollo)
-- =============================================
DROP TABLE IF EXISTS historial_progreso CASCADE;
DROP TABLE IF EXISTS registro_actividad CASCADE;
DROP TABLE IF EXISTS registro_participantes CASCADE;
DROP TABLE IF EXISTS calorias_estimadas CASCADE;
DROP TABLE IF EXISTS actividad CASCADE;
DROP TABLE IF EXISTS tipo_actividad CASCADE;
DROP TABLE IF EXISTS evento CASCADE;
DROP TABLE IF EXISTS logro CASCADE;
DROP TABLE IF EXISTS notificacion CASCADE;
DROP TABLE IF EXISTS recomendacion CASCADE;
DROP TABLE IF EXISTS perfil_salud CASCADE;
DROP TABLE IF EXISTS meta CASCADE;
DROP TABLE IF EXISTS usuario CASCADE;

-- =============================================
-- TABLA: USUARIO (Entidad Central)
-- =============================================
CREATE TABLE usuario (
                         id_usuario VARCHAR(50) PRIMARY KEY,
                         primer_nombre VARCHAR(50) NOT NULL,
                         segundo_nombre VARCHAR(50),
                         primer_apellido VARCHAR(50) NOT NULL,
                         segundo_apellido VARCHAR(50),
                         cedula VARCHAR(20) NOT NULL,
                         peso NUMERIC(5,2),
                         estatura INTEGER,
                         genero CHAR(1) NOT NULL,
                         contrasena VARCHAR(100) NOT NULL,
                         correo VARCHAR(100) NOT NULL,
                         fecha_nacimiento DATE NOT NULL,
                         fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Constraints
                         CONSTRAINT uk_usuario_cedula UNIQUE(cedula),
                         CONSTRAINT uk_usuario_correo UNIQUE(correo),
                         CONSTRAINT ck_usuario_genero CHECK (genero IN ('M', 'F', 'O')),
                         CONSTRAINT ck_usuario_peso CHECK (peso > 0 AND peso < 500),
                         CONSTRAINT ck_usuario_estatura CHECK (estatura > 0 AND estatura < 300)
);

-- =============================================
-- TABLA: META
-- =============================================
CREATE TABLE meta (
                      id_meta VARCHAR(50) PRIMARY KEY,
                      id_usuario VARCHAR(50) NOT NULL,
                      fecha_inicio DATE NOT NULL,
                      fecha_fin DATE NOT NULL,
                      objetivo VARCHAR(200) NOT NULL,
                      perdida_calorias_diarias VARCHAR(50),
                      estado VARCHAR(20) DEFAULT 'ACTIVA',
                      fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Constraints
                      CONSTRAINT fk_meta_usuario FOREIGN KEY (id_usuario)
                          REFERENCES usuario(id_usuario) ON DELETE CASCADE,
                      CONSTRAINT ck_meta_fechas CHECK (fecha_fin > fecha_inicio),
                      CONSTRAINT ck_meta_estado CHECK (estado IN ('ACTIVA', 'COMPLETADA', 'CANCELADA'))
);

-- =============================================
-- TABLA: HISTORIAL_PROGRESO
-- =============================================
CREATE TABLE historial_progreso (
                                    id_historial VARCHAR(50) PRIMARY KEY,
                                    id_usuario VARCHAR(50) NOT NULL,
                                    id_meta VARCHAR(50) NOT NULL,
                                    fecha DATE NOT NULL,
                                    avance_objetivo VARCHAR(100) NOT NULL,
                                    peso_actual NUMERIC(5,2),
                                    notas TEXT,

    -- Constraints
                                    CONSTRAINT fk_historial_usuario FOREIGN KEY (id_usuario)
                                        REFERENCES usuario(id_usuario) ON DELETE CASCADE,
                                    CONSTRAINT fk_historial_meta FOREIGN KEY (id_meta)
                                        REFERENCES meta(id_meta) ON DELETE CASCADE
);

-- =============================================
-- TABLA: PERFIL_SALUD
-- =============================================
CREATE TABLE perfil_salud (
                              id_perfil VARCHAR(50) PRIMARY KEY,
                              id_usuario VARCHAR(50) NOT NULL,
                              fecha DATE NOT NULL DEFAULT CURRENT_DATE,
                              nivel_actividad VARCHAR(50) NOT NULL,
                              gasto_energetico VARCHAR(50) NOT NULL,
                              condicion_cardiovascular VARCHAR(50) NOT NULL,
                              imc VARCHAR(10) NOT NULL,
                              balance_energetico VARCHAR(50) NOT NULL,
                              presion_sistolica INTEGER,
                              presion_diastolica INTEGER,
                              frecuencia_cardiaca INTEGER,

    -- Constraints
                              CONSTRAINT fk_perfil_usuario FOREIGN KEY (id_usuario)
                                  REFERENCES usuario(id_usuario) ON DELETE CASCADE,
                              CONSTRAINT ck_perfil_presion_sistolica CHECK (presion_sistolica BETWEEN 70 AND 200),
                              CONSTRAINT ck_perfil_presion_diastolica CHECK (presion_diastolica BETWEEN 40 AND 130),
                              CONSTRAINT ck_perfil_frecuencia CHECK (frecuencia_cardiaca BETWEEN 30 AND 220)
);

-- =============================================
-- TABLA: RECOMENDACION
-- =============================================
CREATE TABLE recomendacion (
                               id_recomendacion VARCHAR(50) PRIMARY KEY,
                               id_usuario VARCHAR(50) NOT NULL,
                               mensaje TEXT NOT NULL,
                               fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               tipo VARCHAR(50),
                               prioridad VARCHAR(20) DEFAULT 'MEDIA',

    -- Constraints
                               CONSTRAINT fk_recomendacion_usuario FOREIGN KEY (id_usuario)
                                   REFERENCES usuario(id_usuario) ON DELETE CASCADE,
                               CONSTRAINT ck_recomendacion_prioridad CHECK (prioridad IN ('BAJA', 'MEDIA', 'ALTA'))
);

-- =============================================
-- TABLA: NOTIFICACION
-- =============================================
CREATE TABLE notificacion (
                              id_notificacion VARCHAR(50) PRIMARY KEY,
                              id_usuario VARCHAR(50) NOT NULL,
                              mensaje TEXT NOT NULL,
                              fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              leida BOOLEAN DEFAULT FALSE,
                              tipo VARCHAR(50),

    -- Constraints
                              CONSTRAINT fk_notificacion_usuario FOREIGN KEY (id_usuario)
                                  REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

-- =============================================
-- TABLA: LOGRO
-- =============================================
CREATE TABLE logro (
                       id_logro VARCHAR(50) PRIMARY KEY,
                       id_usuario VARCHAR(50) NOT NULL,
                       nombre VARCHAR(100) NOT NULL,
                       recompensa VARCHAR(100) NOT NULL,
                       descripcion VARCHAR(200) NOT NULL,
                       tipo VARCHAR(50) NOT NULL,
                       fecha_obtenido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       puntos INTEGER DEFAULT 0,

    -- Constraints
                       CONSTRAINT fk_logro_usuario FOREIGN KEY (id_usuario)
                           REFERENCES usuario(id_usuario) ON DELETE CASCADE,
                       CONSTRAINT ck_logro_tipo CHECK (tipo IN ('ACTIVIDAD', 'CONSISTENCIA', 'HABILIDAD', 'SOCIAL', 'META'))
);

-- =============================================
-- TABLA: EVENTO
-- =============================================
CREATE TABLE evento (
                        id_evento VARCHAR(50) PRIMARY KEY,
                        nombre VARCHAR(100) NOT NULL,
                        descripcion TEXT,
                        fecha TIMESTAMP NOT NULL,
                        duracion TIME NOT NULL,
                        ubicacion VARCHAR(200),
                        capacidad_maxima INTEGER,
                        estado VARCHAR(20) DEFAULT 'PROGRAMADO',

    -- Constraints
                        CONSTRAINT ck_evento_estado CHECK (estado IN ('PROGRAMADO', 'EN_CURSO', 'FINALIZADO', 'CANCELADO')),
                        CONSTRAINT ck_evento_capacidad CHECK (capacidad_maxima > 0)
);

-- =============================================
-- TABLA: REGISTRO_PARTICIPANTES
-- =============================================
CREATE TABLE registro_participantes (
                                        id_registro_participantes VARCHAR(50) PRIMARY KEY,
                                        id_evento VARCHAR(50) NOT NULL,
                                        id_usuario VARCHAR(50) NOT NULL,
                                        fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        estado_participacion VARCHAR(20) DEFAULT 'CONFIRMADO',

    -- Constraints
                                        CONSTRAINT fk_registro_evento FOREIGN KEY (id_evento)
                                            REFERENCES evento(id_evento) ON DELETE CASCADE,
                                        CONSTRAINT fk_registro_usuario FOREIGN KEY (id_usuario)
                                            REFERENCES usuario(id_usuario) ON DELETE CASCADE,
                                        CONSTRAINT uk_registro_evento_usuario UNIQUE(id_evento, id_usuario),
                                        CONSTRAINT ck_registro_estado CHECK (estado_participacion IN ('CONFIRMADO', 'PENDIENTE', 'CANCELADO', 'ASISTIO'))
);

-- =============================================
-- TABLA: TIPO_ACTIVIDAD
-- =============================================
CREATE TABLE tipo_actividad (
                                id_tipo VARCHAR(50) PRIMARY KEY,
                                nombre VARCHAR(100) NOT NULL,
                                descripcion TEXT,
                                calorias_promedio_por_hora INTEGER,

    -- Constraints
                                CONSTRAINT uk_tipo_nombre UNIQUE(nombre)
);

-- =============================================
-- TABLA: ACTIVIDAD
-- =============================================
CREATE TABLE actividad (
                           id_actividad VARCHAR(50) PRIMARY KEY,
                           id_tipo VARCHAR(50) NOT NULL,
                           nombre VARCHAR(100) NOT NULL,
                           descripcion TEXT,
                           duracion_estimada_minutos INTEGER,
                           nivel_dificultad VARCHAR(20),

    -- Constraints
                           CONSTRAINT fk_actividad_tipo FOREIGN KEY (id_tipo)
                               REFERENCES tipo_actividad(id_tipo) ON DELETE CASCADE,
                           CONSTRAINT ck_actividad_nivel CHECK (nivel_dificultad IN ('PRINCIPIANTE', 'INTERMEDIO', 'AVANZADO'))
);

-- =============================================
-- TABLA: REGISTRO_ACTIVIDAD (NUEVA - Reemplaza calorias_estimadas)
-- =============================================
CREATE TABLE registro_actividad (
                                    id_registro VARCHAR(50) PRIMARY KEY,
                                    id_usuario VARCHAR(50) NOT NULL,
                                    id_actividad VARCHAR(50) NOT NULL,
                                    id_evento VARCHAR(50),
                                    fecha TIMESTAMP NOT NULL,
                                    duracion TIME NOT NULL,
                                    distancia NUMERIC(10,2),
                                    calorias_estimadas INTEGER,
                                    calorias_alcanzadas INTEGER,
                                    frecuencia_cardiaca_promedio INTEGER,
                                    notas TEXT,

    -- Constraints
                                    CONSTRAINT fk_regactividad_usuario FOREIGN KEY (id_usuario)
                                        REFERENCES usuario(id_usuario) ON DELETE CASCADE,
                                    CONSTRAINT fk_regactividad_actividad FOREIGN KEY (id_actividad)
                                        REFERENCES actividad(id_actividad) ON DELETE CASCADE,
                                    CONSTRAINT fk_regactividad_evento FOREIGN KEY (id_evento)
                                        REFERENCES evento(id_evento) ON DELETE SET NULL,
                                    CONSTRAINT ck_regactividad_calorias CHECK (calorias_estimadas >= 0 AND calorias_alcanzadas >= 0),
                                    CONSTRAINT ck_regactividad_distancia CHECK (distancia >= 0)
);

-- =============================================
-- ÍNDICES PARA OPTIMIZACIÓN
-- =============================================

-- Índices en Usuario
CREATE INDEX idx_usuario_correo ON usuario(correo);
CREATE INDEX idx_usuario_cedula ON usuario(cedula);

-- Índices en Meta
CREATE INDEX idx_meta_usuario ON meta(id_usuario);
CREATE INDEX idx_meta_estado ON meta(estado);
CREATE INDEX idx_meta_fechas ON meta(fecha_inicio, fecha_fin);

-- Índices en Historial Progreso
CREATE INDEX idx_historial_usuario ON historial_progreso(id_usuario);
CREATE INDEX idx_historial_meta ON historial_progreso(id_meta);
CREATE INDEX idx_historial_fecha ON historial_progreso(fecha);

-- Índices en Perfil Salud
CREATE INDEX idx_perfil_usuario ON perfil_salud(id_usuario);
CREATE INDEX idx_perfil_fecha ON perfil_salud(fecha);

-- Índices en Logro
CREATE INDEX idx_logro_usuario ON logro(id_usuario);
CREATE INDEX idx_logro_tipo ON logro(tipo);
CREATE INDEX idx_logro_fecha ON logro(fecha_obtenido);

-- Índices en Evento
CREATE INDEX idx_evento_fecha ON evento(fecha);
CREATE INDEX idx_evento_estado ON evento(estado);

-- Índices en Registro Actividad
CREATE INDEX idx_regactividad_usuario ON registro_actividad(id_usuario);
CREATE INDEX idx_regactividad_actividad ON registro_actividad(id_actividad);
CREATE INDEX idx_regactividad_evento ON registro_actividad(id_evento);
CREATE INDEX idx_regactividad_fecha ON registro_actividad(fecha);

-- Índices en Notificación
CREATE INDEX idx_notificacion_usuario ON notificacion(id_usuario);
CREATE INDEX idx_notificacion_leida ON notificacion(leida);
CREATE INDEX idx_notificacion_fecha ON notificacion(fecha);

-- =============================================
-- COMENTARIOS EN TABLAS
-- =============================================
COMMENT ON TABLE usuario IS 'Almacena información de los usuarios del sistema';
COMMENT ON TABLE meta IS 'Objetivos personales que los usuarios desean alcanzar';
COMMENT ON TABLE historial_progreso IS 'Seguimiento del progreso de los usuarios en sus metas';
COMMENT ON TABLE perfil_salud IS 'Evaluaciones del estado físico y salud del usuario';
COMMENT ON TABLE recomendacion IS 'Sugerencias personalizadas del sistema para los usuarios';
COMMENT ON TABLE notificacion IS 'Alertas y recordatorios para los usuarios';
COMMENT ON TABLE logro IS 'Reconocimientos y badges por hitos alcanzados';
COMMENT ON TABLE evento IS 'Actividades grupales programadas';
COMMENT ON TABLE registro_participantes IS 'Inscripciones de usuarios a eventos';
COMMENT ON TABLE tipo_actividad IS 'Categorías de actividades físicas';
COMMENT ON TABLE actividad IS 'Catálogo de actividades físicas disponibles';
COMMENT ON TABLE registro_actividad IS 'Registro individual de actividades realizadas por los usuarios';
