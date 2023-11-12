SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

DROP TABLE IF EXISTS `book`;
CREATE TABLE IF NOT EXISTS `book` (
  `id` bigint NOT NULL,
  `title` varchar(150) NOT NULL,
  `authors` varchar(250) NOT NULL,
  `acquisition` date NOT NULL,
  `pages` smallint NOT NULL,
  `year` smallint NOT NULL,
  `edition` tinyint NOT NULL,
  `price` float(10,0) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `book` (`id`, `title`, `authors`, `acquisition`, `pages`, `year`, `edition`, `price`) VALUES
(3, 'The Hunger Games', 'Suzanne Collins', '2023-11-08', 400, 2009, 1, 29.99),
(4, 'A Arte da Guerra', 'Sun Tzu', '2023-11-08', 128, 401 , 1, 27.49);
COMMIT;
