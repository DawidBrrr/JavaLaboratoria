-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Maj 20, 2025 at 08:20 PM
-- Wersja serwera: 10.4.32-MariaDB
-- Wersja PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `quiz_java`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `quiz_answers`
--

CREATE TABLE `quiz_answers` (
  `id` int(11) NOT NULL,
  `1` varchar(255) DEFAULT NULL,
  `2` varchar(255) DEFAULT NULL,
  `3` varchar(255) DEFAULT NULL,
  `4` varchar(255) DEFAULT NULL,
  `5` varchar(255) DEFAULT NULL,
  `6` varchar(255) DEFAULT NULL,
  `7` varchar(255) DEFAULT NULL,
  `8` varchar(255) DEFAULT NULL,
  `9` varchar(255) DEFAULT NULL,
  `10` varchar(255) DEFAULT NULL,
  `11` varchar(255) DEFAULT NULL,
  `12` varchar(255) DEFAULT NULL,
  `13` varchar(255) DEFAULT NULL,
  `14` varchar(255) DEFAULT NULL,
  `15` varchar(255) DEFAULT NULL,
  `16` varchar(255) DEFAULT NULL,
  `17` varchar(255) DEFAULT NULL,
  `18` varchar(255) DEFAULT NULL,
  `19` varchar(255) DEFAULT NULL,
  `20` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `quiz_answers`
--

INSERT INTO `quiz_answers` (`id`, `1`, `2`, `3`, `4`, `5`, `6`, `7`, `8`, `9`, `10`, `11`, `12`, `13`, `14`, `15`, `16`, `17`, `18`, `19`, `20`) VALUES
(1, 'EXTENDS', 'NEW', 'IM,PLEM,ENTS', 'RETURN', 'BREAK', '', '', 'FINAL', 'ABSTRACT', 'RUNNABLE', 'SYNCHRONIZED', 'MAIN', 'CHAR', 'BOOLEAN', 'IF', 'SWITCH', 'PUBLIC', 'STATIC', 'CLASS', 'RETURN');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `quiz_questions`
--

CREATE TABLE `quiz_questions` (
  `id` int(11) NOT NULL,
  `question` text NOT NULL,
  `answer_a` varchar(255) NOT NULL,
  `answer_b` varchar(255) NOT NULL,
  `answer_c` varchar(255) NOT NULL,
  `answer_d` varchar(255) NOT NULL,
  `correct_answer` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `quiz_questions`
--

INSERT INTO `quiz_questions` (`id`, `question`, `answer_a`, `answer_b`, `answer_c`, `answer_d`, `correct_answer`) VALUES
(1, 'Jakie słowo kluczowe służy do dziedziczenia klasy?', 'implements', 'inherits', 'extends', 'override', 'extends'),
(2, 'Jakie słowo kluczowe służy do utworzenia nowego obiektu?', 'new', 'create', 'object', 'init', 'new'),
(3, 'Jakie słowo kluczowe służy do implementacji interfejsu?', 'import', 'interface', 'extend', 'implements', 'implements'),
(4, 'Jakie słowo kluczowe oznacza, że metoda nie zwraca wartości?', 'empty', 'void', 'return', 'null', 'void'),
(5, 'Jakie słowo kluczowe służy do zakończenia działania pętli?', 'stop', 'exit', 'break', 'return', 'break'),
(6, 'Jakie słowo kluczowe służy do obsługi wyjątków?', 'handle', 'except', 'catch', 'throw', 'catch'),
(7, 'Jakie słowo kluczowe służy do zgłoszenia wyjątku?', 'catch', 'raise', 'throws', 'throw', 'throw'),
(8, 'Jakie słowo kluczowe zarezerwuje wartość jako stałą?', 'const', 'fixed', 'final', 'static', 'final'),
(9, 'Jakie słowo kluczowe definiuje klasę abstrakcyjną?', 'base', 'virtual', 'abstract', 'interface', 'abstract'),
(10, 'Jak nazywa się interfejs używany do tworzenia wątków?', 'Runnable', 'Threadable', 'Executor', 'Multithread', 'Runnable'),
(11, 'Jakie słowo kluczowe służy do synchronizacji wątków?', 'lock', 'sync', 'synchronized', 'threadsafe', 'synchronized'),
(12, 'Jak nazywa się domyślna metoda startowa w Javie?', 'start', 'launch', 'main', 'init', 'main'),
(13, 'Jaki typ zmiennej przechowuje pojedynczy znak?', 'string', 'char', 'letter', 'text', 'char'),
(14, 'Jak nazywa się typ logiczny w Javie?', 'logic', 'bool', 'boolean', 'bit', 'boolean'),
(15, 'Jakie słowo kluczowe sprawdza warunek logiczny?', 'case', 'condition', 'if', 'test', 'if'),
(16, 'Jakie słowo kluczowe służy do wielu opcji wyboru?', 'choose', 'switch', 'select', 'caseof', 'switch'),
(17, 'Jakie słowo kluczowe oznacza klasę dostępną globalnie?', 'static', 'final', 'global', 'public', 'public'),
(18, 'Jakie słowo kluczowe wskazuje, że coś należy do klasy, a nie obiektu?', 'global', 'static', 'final', 'const', 'static'),
(19, 'Jakie słowo kluczowe tworzy anonimową klasę wewnętrzną?', 'class', 'new', 'extends', 'this', 'new'),
(20, 'Jakie słowo kluczowe natychmiast kończy metodę?', 'exit', 'stop', 'return', 'end', 'return');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `quiz_results`
--

CREATE TABLE `quiz_results` (
  `id` int(11) NOT NULL,
  `wynik` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `quiz_results`
--

INSERT INTO `quiz_results` (`id`, `wynik`) VALUES
(1, 15);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `quiz_answers`
--
ALTER TABLE `quiz_answers`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `quiz_questions`
--
ALTER TABLE `quiz_questions`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `quiz_results`
--
ALTER TABLE `quiz_results`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `quiz_answers`
--
ALTER TABLE `quiz_answers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `quiz_questions`
--
ALTER TABLE `quiz_questions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `quiz_results`
--
ALTER TABLE `quiz_results`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
