-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 07-04-2018 a las 16:36:17
-- Versión del servidor: 5.7.21-0ubuntu0.16.04.1
-- Versión de PHP: 7.0.28-0ubuntu0.16.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ligaMX`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `posicion` ()  BEGIN
		
	DECLARE i int DEFAULT 1;
    DECLARE Gvisitantes int;
    DECLARE Glocales int;
    DECLARE locales int;
    DECLARE visitantes int;
    DECLARE puntosL int DEFAULT 0;
    DECLARE puntosV int DEFAULT 0;
    DECLARE z int DEFAULT 1;
    DECLARE jor int DEFAULT 15;
    DECLARE tlpuntos int;
    Declare tvpuntos int;
    
    CREATE TEMPORARY TABLE posiciones (idEquipo int,equipo varchar(50), puntosE int DEFAULT 0);
    WHILE z<17 DO
    	INSERT into posiciones(idEquipo,equipo) values ((select idEquipo from equipo where idEquipo=z),(select nombreEquipo from equipo where idEquipo=z));
        SET z=z+1;
    END WHILE;
    
	WHILE i<17 and jor<=16 DO
        SET visitantes=(SELECT idEquipoVisitante from partido WHERE idPartido=i and idjornada=jor);
        SET locales=(SELECT idEquipoLocal from partido WHERE idPartido=i and idjornada=jor);
        SET Gvisitantes=(SELECT golesVisitante from partido WHERE idPartido=i and idjornada=jor);
        SET Glocales=(SELECT golesLocal from partido WHERE idPartido=i and idjornada=jor);

        

        IF Glocales>Gvisitantes THEN
            SET puntosL=puntosL+3;
            SET puntosV=puntosV+0;
        ELSE
            IF Gvisitantes>Glocales THEN
                set puntosL=puntosL+0;
                SET puntosV=puntosV+3;
            ELSE
                SET puntosL=puntosL+1;
                SET puntosV=puntosV+1;
            END IF;
        END IF;
        SET tlpuntos=(SELECT puntosE from posiciones WHERE idEquipo=locales);
        SET tvpuntos=(SELECT puntosE from posiciones WHERE idEquipo=visitantes);
        UPDATE posiciones SET puntosE=(tlpuntos+puntosL) WHERE idEquipo=locales;
        UPDATE posiciones SET puntosE=(tvpuntos+puntosV) WHERE idEquipo=visitantes;
        SET i=i+1;
        SET puntosL=0;
        SET puntosV=0;
        IF i=9 AND jor=15 THEN
            SET jor=jor+1;
        END IF;
    END WHILE;
    SELECT * from posiciones order by puntosE DESC;
    DROP table IF EXISTS posiciones;
    
    
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dt`
--

CREATE TABLE `dt` (
  `idDT` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `dt`
--

INSERT INTO `dt` (`idDT`, `nombre`) VALUES
(1, 'Almeyda Matias'),
(2, 'Herrera Aguirre Miguel Ernesto'),
(3, 'Ambriz Ignacio'),
(4, 'Almaguer Sergio'),
(5, 'Allison Revuelta Guillermo'),
(6, 'Bogado Flecha Alejandro Daniel'),
(7, 'Corona Jose'),
(8, 'Pelaez Alejandro'),
(9, 'Aldrete Adrian'),
(10, 'Domínguez Julio'),
(11, 'Flores Gerardo'),
(12, 'Mendoza Martin Omar Israel'),
(13, 'Salas Javier'),
(14, 'Silva Jordan'),
(15, 'Velázquez Julián'),
(16, 'Cota Rosario');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `equipo`
--

CREATE TABLE `equipo` (
  `idEquipo` int(11) NOT NULL,
  `nombreEquipo` varchar(45) NOT NULL,
  `DT_idDT` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `equipo`
--

INSERT INTO `equipo` (`idEquipo`, `nombreEquipo`, `DT_idDT`) VALUES
(1, 'Guadalajara', 1),
(2, 'Club América', 2),
(3, 'Necaxa', 3),
(4, 'Querétaro', 4),
(5, 'Atlas', 5),
(6, 'Chiapas', 6),
(7, 'Cruz Azul', 7),
(8, 'León', 8),
(9, 'Monterrey', 9),
(10, 'Morelia', 10),
(11, 'Pachuca', 11),
(12, 'Puebla', 12),
(13, 'Santos Laguna', 13),
(14, 'Tijuana', 14),
(15, 'Toluca', 15),
(16, 'Veracruz', 16);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `jornada`
--

CREATE TABLE `jornada` (
  `idJornada` int(11) NOT NULL,
  `idTemporada` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `jornada`
--

INSERT INTO `jornada` (`idJornada`, `idTemporada`) VALUES
(15, 1),
(16, 1),
(17, 1),
(18, 1),
(19, 1),
(20, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `jugador`
--

CREATE TABLE `jugador` (
  `idJugador` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `idEquipo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `jugador`
--

INSERT INTO `jugador` (`idJugador`, `nombre`, `idEquipo`) VALUES
(1, 'Cota Rodolfo', 1),
(2, 'Hernandez Edwin William', 2),
(3, 'Jimenez Oscar', 3),
(4, 'Aguilar Paul', 4),
(5, 'Chávez Luis', 5),
(6, 'Colula Bryan', 6),
(7, 'Alcala Gil', 7),
(8, 'Corral George', 8),
(9, 'Mena Delgado Angel Israel', 9),
(10, 'Mora Felipe Andrés', 10),
(11, 'Rodriguez Martin', 11),
(12, 'Silva Francisco', 12),
(13, 'Cauteruccio Martin', 13),
(14, 'Méndez Edgar', 14),
(15, 'Pena Carlos	', 15),
(16, 'Fierro Carlos', 16);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `partido`
--

CREATE TABLE `partido` (
  `idPartido` int(11) NOT NULL,
  `idEquipoLocal` int(11) NOT NULL,
  `idEquipoVisitante` int(11) NOT NULL,
  `golesLocal` int(11) DEFAULT NULL,
  `golesVisitante` int(11) DEFAULT NULL,
  `idJornada` int(11) NOT NULL,
  `fecha` datetime NOT NULL,
  `TipoPartido_idTipoPartido` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `partido`
--

INSERT INTO `partido` (`idPartido`, `idEquipoLocal`, `idEquipoVisitante`, `golesLocal`, `golesVisitante`, `idJornada`, `fecha`, `TipoPartido_idTipoPartido`) VALUES
(1, 1, 2, 2, 1, 15, '2017-08-01 00:00:00', 1),
(2, 3, 4, 1, 1, 15, '2017-08-02 00:00:00', 1),
(3, 5, 6, 2, 0, 15, '2017-08-03 00:00:00', 1),
(4, 7, 8, 0, 1, 15, '2017-08-04 00:00:00', 1),
(5, 9, 10, 2, 2, 15, '2017-08-05 00:00:00', 1),
(6, 11, 12, 0, 0, 15, '2017-08-06 00:00:00', 1),
(7, 13, 14, 1, 3, 15, '2017-08-07 00:00:00', 1),
(8, 15, 16, 2, 1, 15, '2017-08-08 00:00:00', 1),
(9, 1, 3, 2, 2, 16, '2017-08-09 00:00:00', 1),
(10, 2, 4, 5, 3, 17, '2017-08-10 00:00:00', 4),
(11, 5, 7, 1, 0, 16, '2017-08-11 00:00:00', 1),
(12, 6, 8, 3, 2, 16, '2017-08-12 00:00:00', 1),
(13, 9, 11, 3, 3, 16, '2017-08-13 00:00:00', 1),
(14, 10, 12, 1, 0, 16, '2017-08-14 00:00:00', 1),
(15, 13, 15, 2, 2, 16, '2017-08-15 00:00:00', 1),
(16, 14, 16, 1, 1, 16, '2017-08-16 00:00:00', 1),
(17, 1, 10, 3, 2, 16, '2017-08-20 00:00:00', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `temporada`
--

CREATE TABLE `temporada` (
  `idTemporada` int(11) NOT NULL,
  `nombreTemporada` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `temporada`
--

INSERT INTO `temporada` (`idTemporada`, `nombreTemporada`) VALUES
(1, 'Clausura 2017');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipopartido`
--

CREATE TABLE `tipopartido` (
  `idTipoPartido` int(11) NOT NULL,
  `nombreTipoPartido` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tipopartido`
--

INSERT INTO `tipopartido` (`idTipoPartido`, `nombreTipoPartido`) VALUES
(1, 'Normal'),
(2, 'Octavos de final'),
(3, 'Cuartos de final'),
(4, 'Semifinal'),
(5, 'Final'),
(6, 'Amistoso');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `idUsuario` int(11) NOT NULL,
  `Nom_usuario` varchar(16) NOT NULL,
  `contraseña` varchar(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`idUsuario`, `Nom_usuario`, `contraseña`) VALUES
(1, 'Elias_ZZZ', '1234'),
(2, 'Martin', '1234');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `dt`
--
ALTER TABLE `dt`
  ADD PRIMARY KEY (`idDT`);

--
-- Indices de la tabla `equipo`
--
ALTER TABLE `equipo`
  ADD PRIMARY KEY (`idEquipo`),
  ADD KEY `fk_Equipo_DT1_idx` (`DT_idDT`);

--
-- Indices de la tabla `jornada`
--
ALTER TABLE `jornada`
  ADD PRIMARY KEY (`idJornada`),
  ADD KEY `fk_Jornada_Temporada1_idx` (`idTemporada`);

--
-- Indices de la tabla `jugador`
--
ALTER TABLE `jugador`
  ADD PRIMARY KEY (`idJugador`),
  ADD KEY `fk_Jugador_Equipo1_idx` (`idEquipo`);

--
-- Indices de la tabla `partido`
--
ALTER TABLE `partido`
  ADD PRIMARY KEY (`idPartido`),
  ADD KEY `fk_Partido_Equipo1_idx` (`idEquipoLocal`),
  ADD KEY `fk_Partido_Equipo2_idx` (`idEquipoVisitante`),
  ADD KEY `fk_Partido_Jornada1_idx` (`idJornada`),
  ADD KEY `fk_Partido_TipoPartido1_idx` (`TipoPartido_idTipoPartido`);

--
-- Indices de la tabla `temporada`
--
ALTER TABLE `temporada`
  ADD PRIMARY KEY (`idTemporada`);

--
-- Indices de la tabla `tipopartido`
--
ALTER TABLE `tipopartido`
  ADD PRIMARY KEY (`idTipoPartido`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`idUsuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `equipo`
--
ALTER TABLE `equipo`
  MODIFY `idEquipo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT de la tabla `partido`
--
ALTER TABLE `partido`
  MODIFY `idPartido` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `idUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `equipo`
--
ALTER TABLE `equipo`
  ADD CONSTRAINT `fk_Equipo_DT1` FOREIGN KEY (`DT_idDT`) REFERENCES `dt` (`idDT`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `jornada`
--
ALTER TABLE `jornada`
  ADD CONSTRAINT `fk_Jornada_Temporada1` FOREIGN KEY (`idTemporada`) REFERENCES `temporada` (`idTemporada`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `jugador`
--
ALTER TABLE `jugador`
  ADD CONSTRAINT `fk_Jugador_Equipo1` FOREIGN KEY (`idEquipo`) REFERENCES `equipo` (`idEquipo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `partido`
--
ALTER TABLE `partido`
  ADD CONSTRAINT `fk_Partido_Equipo1` FOREIGN KEY (`idEquipoLocal`) REFERENCES `equipo` (`idEquipo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Partido_Equipo2` FOREIGN KEY (`idEquipoVisitante`) REFERENCES `equipo` (`idEquipo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Partido_Jornada1` FOREIGN KEY (`idJornada`) REFERENCES `jornada` (`idJornada`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Partido_TipoPartido1` FOREIGN KEY (`TipoPartido_idTipoPartido`) REFERENCES `tipopartido` (`idTipoPartido`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
