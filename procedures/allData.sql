-- Insert into Bloc
INSERT IGNORE INTO `Bloc` (`id_bloc`, `descriere_bloc`) VALUES
(1, 'Bloc A'),
(2, 'Bloc B'),
(3, 'Bloc C'),
(4, 'Bloc D'),
(5, 'Bloc E');

-- Insert into Etaj
INSERT IGNORE INTO `etaj` (`id_etaj`, `fk_id_bloc`, `nr_etaj`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 2, 1),
(4, 2, 2),
(5, 3, 1);

-- Insert into Celula
INSERT IGNORE INTO `Celula` (`id_celula`, `fk_id_etaj`, `locuri_ramase`) VALUES
(1, 1, 5),
(2, 2, 4),
(3, 3, 3),
(4, 4, 2),
(5, 5, 1);

-- Insert into Utilizator
INSERT IGNORE INTO `Utilizator` (`id_utilizator`, `username`, `password`, `drept_access`) VALUES
(1, 'warden1', 'pass123', 0),
(2, 'guard1', 'pass123', 1),
(3, 'inmate1', 'pass123', 2),
(4, 'visitor1', 'pass123', 3),
(5, 'visitor2', 'pass123', 3);

-- Insert into Gardian
INSERT IGNORE INTO `Gardian` (`id`, `fk_id_shift`, `fk_id_utilizator`) VALUES
(1, 1, 2),
(2, 2, 2);

-- Insert into Detinut
INSERT IGNORE INTO `Detinut` (`id_detinut`, `nume`, `fk_id_celula`, `fk_id_utilizator`, `profesie`) VALUES
(1, 'John Doe', 1, 3, 'Carpenter'),
(2, 'Jane Roe', 2, 3, 'Plumber');

-- Insert into Sentinta
INSERT IGNORE INTO `sentinta` (`id_sentita`, `categorie`, `motiv_specific`, `fk_id_detinut`, `start_time`, `end_time`) VALUES
(1, 'Theft', 'Robbery at bank', 1, '2022-01-01 12:00:00', '2032-01-01 12:00:00'),
(2, 'Fraud', 'Credit card fraud', 2, '2023-01-01 12:00:00', '2030-01-01 12:00:00');

-- Insert into Programari
INSERT IGNORE INTO `Programari` (`id_programare`, `tip_programari`, `start_time`, `end_time`, `fk_id_prizioner`, `fk_id_vizitator`) VALUES
(1, 1, '2024-01-01 10:00:00', '2024-01-01 11:00:00', 1, 4),
(2, 2, '2024-01-02 14:00:00', '2024-01-02 15:00:00', 2, 5);

-- Insert into Task_inchisoare
INSERT IGNORE INTO `Task_inchisoare` (`id_task`, `difficulty`, `start_time`, `end_time`, `description`) VALUES
(1, 3, '2024-01-01 08:00:00', '2024-01-01 12:00:00', 'Clean the yard'),
(2, 5, '2024-01-02 09:00:00', '2024-01-02 13:00:00', 'Fix plumbing');

-- Insert into Inscriere_task
INSERT IGNORE INTO `Inscriere_task` (`id`, `fk_id_detinut`, `fk_id_task`) VALUES
(1, 1, 1),
(2, 2, 2);

-- Insert into Inregistrare_carcera
INSERT IGNORE INTO `Inregistrare_carcera` (`id_inregistrare_carcera`, `fk_id_carcera`, `fk_id_detinut`, `end_time`) VALUES
(1, 1, 1, '2024-01-10 12:00:00'),
(2, 2, 2, '2024-01-15 12:00:00');
