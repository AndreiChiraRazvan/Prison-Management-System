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

DELIMITER //
CREATE PROCEDURE IF NOT EXISTS `get_data_detinut_from_vizitor`(
    IN in_fk_id_vizitator INT
)
BEGIN
    SELECT DISTINCT 
        fk_id_prizioner, 
        detinut.nume,
        functie_sentinta_ramasa(fk_id_prizioner) AS sentence_remained
    FROM 
        programari
    JOIN 
        detinut 
        ON detinut.id_detinut = programari.fk_id_prizioner
    WHERE 
        programari.tip_programari = 1
        AND programari.fk_id_vizitator = in_fk_id_vizitator;
END//
DELIMITER ;

-- Change the delimiter to $$
DELIMITER $$

-- Drop the procedure if it exists
DROP PROCEDURE IF EXISTS `get_sentence_remained`$$

-- Create the procedure
CREATE PROCEDURE `get_sentence_remained`(
    IN in_fk_id_vizitator INT
)
BEGIN
    SELECT DISTINCT 
        fk_id_prizioner, 
        detinut.nume,
        functie_sentinta_ramasa(fk_id_prizioner) AS sentence_remained
    FROM 
        programari
    JOIN 
        detinut 
        ON detinut.id_detinut = programari.fk_id_prizioner
    WHERE 
        programari.tip_programari = 1
        AND programari.fk_id_vizitator = in_fk_id_vizitator;
END$$

-- Reset the delimiter back to ;
DELIMITER ;
-- Change the delimiter to $$
DELIMITER $$

-- Drop the procedure if it exists
DROP PROCEDURE IF EXISTS `GetColegiiGardianului`$$

-- Create the procedure
CREATE PROCEDURE `GetColegiiGardianului`(
    IN idGardian INT
)
BEGIN
    DECLARE idEtajGardian TINYINT;
    DECLARE idBlocGardian TINYINT;

    -- Retrieve the floor and block IDs associated with the given guardian
    SELECT e.id_etaj, e.fk_id_bloc
    INTO idEtajGardian, idBlocGardian
    FROM Gardian g
    INNER JOIN shift s ON g.fk_id_shift = s.id_shift
    INNER JOIN etaj e ON s.fk_id_etaj = e.id_etaj
    WHERE g.id = idGardian;

    -- Select colleagues of the guardian in the same block but different from the given guardian
    SELECT g.id AS id_gardian, u.username, e.nr_etaj AS etaj, b.descriere_bloc AS bloc
    FROM Gardian g
    INNER JOIN shift s ON g.fk_id_shift = s.id_shift
    INNER JOIN etaj e ON s.fk_id_etaj = e.id_etaj
    INNER JOIN Bloc b ON e.fk_id_bloc = b.id_bloc
    INNER JOIN Utilizator u ON g.fk_id_utilizator = u.id_utilizator
    WHERE e.fk_id_bloc = idBlocGardian
      AND g.id != idGardian;
END$$

-- Reset the delimiter back to ;
DELIMITER ;

-- Change the delimiter to $$
DELIMITER $$

-- Drop the procedure if it exists
DROP PROCEDURE IF EXISTS `GetProgramariByVizitator`$$

-- Create the procedure
CREATE PROCEDURE `GetProgramariByVizitator`(
    IN id_vizitator INT
)
BEGIN
    SELECT 
        programari.start_time AS startTime, 
        programari.end_time AS endTime, 
        detinut.nume AS nume
    FROM 
        programari
    JOIN 
        detinut ON detinut.id_detinut = programari.fk_id_prizioner
    WHERE 
        programari.tip_programari = 1 
        AND programari.fk_id_vizitator = id_vizitator
    ORDER BY 
        programari.end_time
    LIMIT 10;
END$$

-- Reset the delimiter back to ;
DELIMITER ;

-- Change the delimiter to $$
DELIMITER $$

-- Drop the procedure if it exists
DROP PROCEDURE IF EXISTS `GetTaskuriDetinut`$$

-- Create the procedure
CREATE PROCEDURE `GetTaskuriDetinut`(
    IN idDetinut INT
)
BEGIN
    SELECT 
        t.id_task AS ID_Task,
        t.description AS Descriere,
        t.difficulty AS Dificultate,
        t.start_time AS Inceput,
        t.end_time AS Sfarsit
    FROM Inscriere_task it
    INNER JOIN Task_inchisoare t ON it.fk_id_task = t.id_task
    WHERE it.fk_id_detinut = idDetinut;
END$$

-- Reset the delimiter back to ;
DELIMITER ;

-- Change the delimiter to $$
DELIMITER $$

-- Drop the procedure if it exists
DROP PROCEDURE IF EXISTS `remaining_time_based_on_id_inmate`$$

-- Create the procedure
CREATE PROCEDURE `remaining_time_based_on_id_inmate`(
    IN idPrizonier INT,
    OUT sentinta_ramasa NVARCHAR(155)
)
BEGIN
    SET sentinta_ramasa = (
        SELECT (
            IF(SUM(TIMESTAMPDIFF(YEAR, sentinta.start_time, sentinta.end_time)) > 2, 
                CONCAT(SUM(TIMESTAMPDIFF(YEAR, sentinta.start_time, sentinta.end_time)), ' years'),
            IF(SUM(TIMESTAMPDIFF(MONTH, sentinta.start_time, sentinta.end_time)) > 2,
                CONCAT(SUM(TIMESTAMPDIFF(MONTH, sentinta.start_time, sentinta.end_time)), ' months'),
            IF(SUM(TIMESTAMPDIFF(DAY, sentinta.start_time, sentinta.end_time)) > 1, 
                CONCAT(SUM(TIMESTAMPDIFF(DAY, sentinta.start_time, sentinta.end_time)), ' days'),
                CONCAT(SUM(TIMESTAMPDIFF(HOUR, sentinta.start_time, sentinta.end_time)), ' hours')
            )))
        )
        FROM sentinta
        WHERE sentinta.fk_id_detinut = idPrizonier
    );
END$$

-- Reset the delimiter back to ;
DELIMITER ;


-- Change the delimiter to $$
DELIMITER $$

-- Drop the procedure if it exists
DROP PROCEDURE IF EXISTS `UpdateOreVizita`$$

-- Create the procedure
CREATE PROCEDURE `UpdateOreVizita`(
    IN vizitaID INT,            
    IN nouStartTime DATETIME,   
    IN nouEndTime DATETIME   
)
BEGIN
    IF EXISTS (SELECT 1 FROM Programari WHERE id_programare = vizitaID) THEN
        UPDATE Programari
        SET start_time = nouStartTime,
            end_time = nouEndTime
        WHERE id_programare = vizitaID;
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Vizita specificată nu există!';
    END IF;
END$$

-- Reset the delimiter back to ;
DELIMITER ;


-- Change the delimiter to $$
DELIMITER $$

-- Drop the function if it exists
DROP FUNCTION IF EXISTS `functie_sentinta_ramasa`$$

-- Create the function
CREATE FUNCTION `functie_sentinta_ramasa`(id INT) 
RETURNS VARCHAR(255) CHARSET utf8mb4
DETERMINISTIC
BEGIN
    DECLARE sentinta_ramasa VARCHAR(255);

    -- Directly query the value instead of calling a procedure
    CALL penitenciar.remaining_time_based_on_id_inmate(id, sentinta_ramasa);

    RETURN sentinta_ramasa;
END$$

-- Reset the delimiter back to ;
DELIMITER ;









