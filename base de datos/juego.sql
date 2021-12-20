-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3307
-- Tiempo de generación: 20-12-2021 a las 08:05:38
-- Versión del servidor: 10.4.20-MariaDB
-- Versión de PHP: 8.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `juego`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `idCategoria` int(11) NOT NULL,
  `dificultadCategoria` varchar(40) NOT NULL,
  `nombreCategoria` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`idCategoria`, `dificultadCategoria`, `nombreCategoria`) VALUES
(1, 'Muy Facil', 'Peliculas'),
(2, 'Facil', 'Cultura General'),
(3, 'Intermedia', 'Biología'),
(4, 'Dificil', 'Matemática'),
(5, 'Muy dificil', 'Física');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `jugador`
--

CREATE TABLE `jugador` (
  `idJugador` int(2) NOT NULL,
  `nombreJugador` varchar(40) DEFAULT NULL,
  `idRonda` int(11) DEFAULT NULL,
  `estadoJugador` varchar(30) NOT NULL DEFAULT 'retirado',
  `acumuladoPremio` int(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `jugador`
--

INSERT INTO `jugador` (`idJugador`, `nombreJugador`, `idRonda`, `estadoJugador`, `acumuladoPremio`) VALUES
(1, 'Mary', 5, '1', 5000),
(2, 'gochi', 5, '1', 5000),
(3, 'Juanjo', 5, '1', 5000),
(4, 'Dariana', 2, '0', 1000),
(5, 'Pepito Perez', 5, '1', 5000),
(6, 'Pepita Ramirez', 5, '1', 5000),
(7, 'Ikenna Sha1', 1, '2', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `opcion`
--

CREATE TABLE `opcion` (
  `idOpcion` int(11) NOT NULL,
  `estadoRespuesta` int(1) NOT NULL,
  `idPregunta` int(11) NOT NULL,
  `contenidoOpcion` varchar(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `opcion`
--

INSERT INTO `opcion` (`idOpcion`, `estadoRespuesta`, `idPregunta`, `contenidoOpcion`) VALUES
(1, 0, 1, 'En busca del valle encantado'),
(2, 0, 1, 'King Kong'),
(3, 1, 1, 'Parque Jurasico'),
(4, 0, 1, 'Godzilla'),
(5, 1, 2, 'Star Wars'),
(6, 0, 2, 'Oblivion'),
(7, 0, 2, 'Dune'),
(8, 0, 2, 'Star Trek'),
(9, 0, 3, 'C3PO'),
(10, 1, 3, 'Han Solo'),
(11, 0, 3, 'Boba Fett'),
(12, 0, 3, 'Obi-Wan Kenobi'),
(13, 0, 4, '4'),
(14, 0, 4, '5'),
(15, 1, 4, '8'),
(16, 0, 4, '5'),
(17, 1, 5, 'Hobbit'),
(18, 0, 5, 'Enano'),
(19, 0, 5, 'Humano'),
(20, 0, 5, 'Elfo'),
(21, 0, 6, '3'),
(22, 0, 6, '3,13'),
(23, 1, 6, '3,14'),
(24, 0, 6, '3,4'),
(25, 0, 7, 'Europa'),
(26, 0, 7, 'Oceania'),
(27, 0, 7, 'Asia'),
(28, 1, 7, 'Medio Oriente'),
(29, 0, 8, '4'),
(30, 0, 8, '1'),
(31, 1, 8, '5'),
(32, 0, 8, '2'),
(33, 0, 9, 'Jirafa'),
(34, 0, 9, 'Tiburon blanco'),
(35, 0, 9, 'Orca'),
(36, 1, 9, 'Ballena Azul'),
(37, 0, 10, '4'),
(38, 1, 10, '5'),
(39, 0, 10, '6'),
(40, 0, 10, '3'),
(41, 1, 11, 'Gregor Mendel'),
(42, 0, 11, 'Albert Einstein'),
(43, 0, 11, 'Michio Kaku'),
(44, 0, 11, 'Nikola Tesla'),
(45, 0, 12, 'Helio'),
(46, 0, 12, 'Xenon'),
(47, 0, 12, 'Dioxido de Carbono'),
(48, 1, 12, 'Oxigeno'),
(49, 0, 13, 'Pronton'),
(50, 0, 13, 'Neutron'),
(51, 1, 13, 'Proton'),
(52, 0, 13, 'Electron'),
(53, 1, 14, 'Mitocondria'),
(54, 0, 14, 'Ribosoma'),
(55, 0, 14, 'Membrana Interna'),
(56, 0, 14, 'Granulos'),
(57, 0, 15, 'Procariota'),
(58, 1, 15, 'Eucariota'),
(59, 0, 15, 'Vegetal'),
(60, 0, 15, 'Fisica'),
(61, 0, 16, '1,654656'),
(62, 0, 16, '1,657890'),
(63, 0, 16, '1,6234887'),
(64, 1, 16, '1,618033'),
(65, 1, 17, 'Cuando su estructura se repite a distintas escalas'),
(66, 0, 17, 'Cuando su estructura se repite a las mismas escalas'),
(67, 0, 17, 'Cuando su estructura es simétrica'),
(68, 0, 17, 'Cuando su estructura es asimétrica'),
(69, 0, 18, 'Un séptimo'),
(70, 1, 18, 'Un doceavo'),
(71, 0, 18, '3/4 Partes'),
(72, 0, 18, '4/6 Partes'),
(73, 1, 19, '2,718'),
(74, 0, 19, '2,618'),
(75, 0, 19, '2,719'),
(76, 0, 19, '2,619'),
(77, 0, 20, '5 veces mayor'),
(78, 0, 20, '4 veces mayor'),
(79, 1, 20, '6 veces mayor'),
(80, 0, 20, '3 veces mayor'),
(81, 0, 21, 'Circunferencia de un circulo'),
(82, 0, 21, 'La teoría de la probabilidad'),
(83, 1, 21, 'Equivalencia entre masa y energía'),
(84, 0, 21, 'Volumen de un cubo'),
(85, 1, 22, 'El boson de higgs'),
(86, 0, 22, 'Fermion'),
(87, 0, 22, 'Hadron'),
(88, 0, 22, 'Quark'),
(89, 0, 23, 'Mecanica de fluidos'),
(90, 1, 23, 'Mecanica Celeste'),
(91, 0, 23, 'Mecatronica'),
(92, 0, 23, 'Mecanica cuántica '),
(93, 0, 24, 'Barómetro'),
(94, 0, 24, 'Termómetro'),
(95, 1, 24, 'Higrómetro'),
(96, 0, 24, 'Hidrometro'),
(97, 0, 25, 'Fuerza bruta'),
(98, 1, 25, 'Fuerza de cohesion'),
(99, 0, 25, 'Fuerza de adhesion'),
(100, 0, 25, 'Fuerza de gravedad');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pregunta`
--

CREATE TABLE `pregunta` (
  `idPregunta` int(1) NOT NULL,
  `contenidoPregunta` text NOT NULL,
  `idCategoria` int(1) NOT NULL,
  `idRonda` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `pregunta`
--

INSERT INTO `pregunta` (`idPregunta`, `contenidoPregunta`, `idCategoria`, `idRonda`) VALUES
(1, '¿Con que película se pusieron de moda los dinosaurios?', 1, 1),
(2, '¿De que pelicula es el peludo chewbacca?', 1, 1),
(3, '¿Como se llama el amigo fiel de chewbacca?', 1, 1),
(4, '¿Cuantas peliculas tiene el arco principal de Harry Potter?', 1, 1),
(5, '¿Que criatura es verdaderamente gollum?', 1, 1),
(6, '¿A cuanto equivale pi?', 2, 2),
(7, '¿Cual de los siguientes no es un continente?', 2, 2),
(8, '¿Cual es el siguiente numero de la siguiente sucesión: 1-1-2-3…?', 2, 2),
(9, '¿Cual es el animal mas grande de la tierra?', 2, 2),
(10, '¿Cuantos anillos tiene la bandera olímpica?', 2, 2),
(11, '¿Quien ideo las leyes de la herencia genética?', 3, 3),
(12, '¿Que gas liberan las plantas al hacer la fotosíntesis?', 3, 3),
(13, '¿Cual es la particula con carga positiva?', 3, 3),
(14, '¿Que organulo es el encargado de generar la energía química de la celula? ', 3, 3),
(15, '¿Como se llama la celula que tiene nucleo?', 3, 3),
(16, '¿Cual es el aproximado del numero aureo?', 4, 4),
(17, '¿Cuando es fractal un objeto?', 4, 4),
(18, '¿Cuanto es la cuarta parte de la tercera parte?', 4, 4),
(19, '¿A cuanto equivale el numero euler?', 4, 4),
(20, '¿Cuántas veces es mayor la gravedad de la tierra en comparación a la gravedad que existe en la luna? ', 4, 4),
(21, '¿Que representa la formula e=mc^2?', 5, 5),
(22, '¿Cual es la particula de Dios?', 5, 5),
(23, 'La ecuacion de Kepler es fundamental en:', 5, 5),
(24, '¿Como se llama el instrumento que mide y registra la humedad relativa del aire?', 5, 5),
(25, '¿Cual es la fuerza que mantiene unidas las moléculas del cuerpo?', 5, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ronda`
--

CREATE TABLE `ronda` (
  `idRonda` int(1) NOT NULL,
  `numRonda` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `ronda`
--

INSERT INTO `ronda` (`idRonda`, `numRonda`) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`idCategoria`);

--
-- Indices de la tabla `jugador`
--
ALTER TABLE `jugador`
  ADD PRIMARY KEY (`idJugador`),
  ADD KEY `idPremio` (`idRonda`);

--
-- Indices de la tabla `opcion`
--
ALTER TABLE `opcion`
  ADD PRIMARY KEY (`idOpcion`),
  ADD KEY `idPregunta` (`idPregunta`);

--
-- Indices de la tabla `pregunta`
--
ALTER TABLE `pregunta`
  ADD PRIMARY KEY (`idPregunta`),
  ADD KEY `idCategoria` (`idCategoria`),
  ADD KEY `idRonda` (`idRonda`);

--
-- Indices de la tabla `ronda`
--
ALTER TABLE `ronda`
  ADD PRIMARY KEY (`idRonda`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `idCategoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `jugador`
--
ALTER TABLE `jugador`
  MODIFY `idJugador` int(2) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `opcion`
--
ALTER TABLE `opcion`
  MODIFY `idOpcion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=101;

--
-- AUTO_INCREMENT de la tabla `pregunta`
--
ALTER TABLE `pregunta`
  MODIFY `idPregunta` int(1) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `jugador`
--
ALTER TABLE `jugador`
  ADD CONSTRAINT `jugador_ibfk_1` FOREIGN KEY (`idRonda`) REFERENCES `ronda` (`idRonda`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `opcion`
--
ALTER TABLE `opcion`
  ADD CONSTRAINT `opcion_ibfk_1` FOREIGN KEY (`idPregunta`) REFERENCES `pregunta` (`idPregunta`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `pregunta`
--
ALTER TABLE `pregunta`
  ADD CONSTRAINT `pregunta_ibfk_1` FOREIGN KEY (`idCategoria`) REFERENCES `categoria` (`idCategoria`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `pregunta_ibfk_2` FOREIGN KEY (`idRonda`) REFERENCES `ronda` (`idRonda`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
