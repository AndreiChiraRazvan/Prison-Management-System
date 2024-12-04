CREATE TABLE `Detinut`(
    `id_detinut` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nume` VARCHAR(255) NOT NULL,
    `id_celula` BIGINT UNSIGNED NOT NULL,
    `id_utilizator` BIGINT UNSIGNED NOT NULL,
    `profesie` VARCHAR(255) NOT NULL
);
CREATE TABLE `Bloc`(
    `id_bloc` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `descriere_bloc` VARCHAR(255) NOT NULL
);
CREATE TABLE `Celula`(
    `id_celula` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `id_etaj` BIGINT UNSIGNED NOT NULL,
    `id_gardian` BIGINT UNSIGNED NOT NULL,
    `etaj_curent` BIGINT NOT NULL
);
CREATE TABLE `Gardian`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `id_shift` BIGINT UNSIGNED NOT NULL,
    `id_utilizator` BIGINT UNSIGNED NOT NULL
);
CREATE TABLE `shift`(
    `id_shift` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `id_etaj` BIGINT UNSIGNED NOT NULL,
    `tip_shift` BIGINT UNSIGNED NOT NULL,
    `zi` BIGINT UNSIGNED NOT NULL
);
CREATE TABLE `Carcera`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `id_detinut` BIGINT UNSIGNED NOT NULL,
    `end_time` DATETIME NOT NULL
);
CREATE TABLE `sentinta`(
    `id_sentita` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `categorie` VARCHAR(255) NOT NULL,
    `motiv_specific` VARCHAR(255) NOT NULL,
    `end_time` DATETIME NOT NULL,
    `id_detinut` BIGINT UNSIGNED NOT NULL
);
CREATE TABLE `vizitator`(
    `id_vizitator` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nume` VARCHAR(255) NOT NULL,
    `id_programare` BIGINT UNSIGNED NOT NULL,
    `id_utilizator` BIGINT UNSIGNED NOT NULL
);
CREATE TABLE `Programari`(
    `id_programare` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `tip_programari` BIGINT NOT NULL,
    `start_time` DATETIME NOT NULL,
    `end_time` DATETIME NOT NULL,
    `id_prizioner` BIGINT UNSIGNED NOT NULL
);
CREATE TABLE `Task_inchisoare`(
    `id_task` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `difficulty` BIGINT NOT NULL,
    `start_time` DATETIME NOT NULL,
    `end_time` DATETIME NOT NULL,
    `description` VARCHAR(255) NOT NULL
);
CREATE TABLE `Inscriere_task`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `id_detinut` BIGINT UNSIGNED NOT NULL,
    `id_task` BIGINT UNSIGNED NOT NULL
);
CREATE TABLE `Utilizator`(
    `id_utilizator` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `drept_access` BIGINT NOT NULL COMMENT '0->warden
1->gardian
2->vizitator'
);
CREATE TABLE `etaj`(
    `id_etaj` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `id_bloc` BIGINT UNSIGNED NOT NULL
);
ALTER TABLE
    `Inscriere_task` ADD CONSTRAINT `inscriere_task_id_task_foreign` FOREIGN KEY(`id_task`) REFERENCES `Task_inchisoare`(`id_task`);
ALTER TABLE
    `Celula` ADD CONSTRAINT `celula_id_gardian_foreign` FOREIGN KEY(`id_gardian`) REFERENCES `Gardian`(`id`);
ALTER TABLE
    `etaj` ADD CONSTRAINT `etaj_id_bloc_foreign` FOREIGN KEY(`id_bloc`) REFERENCES `Bloc`(`id_bloc`);
ALTER TABLE
    `Inscriere_task` ADD CONSTRAINT `inscriere_task_id_detinut_foreign` FOREIGN KEY(`id_detinut`) REFERENCES `Detinut`(`id_detinut`);
ALTER TABLE
    `Detinut` ADD CONSTRAINT `detinut_id_utilizator_foreign` FOREIGN KEY(`id_utilizator`) REFERENCES `Utilizator`(`id_utilizator`);
ALTER TABLE
    `Celula` ADD CONSTRAINT `celula_id_etaj_foreign` FOREIGN KEY(`id_etaj`) REFERENCES `etaj`(`id_etaj`);
ALTER TABLE
    `Gardian` ADD CONSTRAINT `gardian_id_utilizator_foreign` FOREIGN KEY(`id_utilizator`) REFERENCES `Utilizator`(`id_utilizator`);
ALTER TABLE
    `Detinut` ADD CONSTRAINT `detinut_id_celula_foreign` FOREIGN KEY(`id_celula`) REFERENCES `Celula`(`id_celula`);
ALTER TABLE
    `Carcera` ADD CONSTRAINT `carcera_id_detinut_foreign` FOREIGN KEY(`id_detinut`) REFERENCES `Detinut`(`id_detinut`);
ALTER TABLE
    `vizitator` ADD CONSTRAINT `vizitator_id_programare_foreign` FOREIGN KEY(`id_programare`) REFERENCES `Programari`(`id_programare`);
ALTER TABLE
    `Programari` ADD CONSTRAINT `programari_id_prizioner_foreign` FOREIGN KEY(`id_prizioner`) REFERENCES `Detinut`(`id_detinut`);
ALTER TABLE
    `sentinta` ADD CONSTRAINT `sentinta_id_detinut_foreign` FOREIGN KEY(`id_detinut`) REFERENCES `Detinut`(`id_detinut`);
ALTER TABLE
    `vizitator` ADD CONSTRAINT `vizitator_id_utilizator_foreign` FOREIGN KEY(`id_utilizator`) REFERENCES `Utilizator`(`id_utilizator`);
ALTER TABLE
    `shift` ADD CONSTRAINT `shift_id_etaj_foreign` FOREIGN KEY(`id_etaj`) REFERENCES `etaj`(`id_etaj`);
ALTER TABLE
    `Gardian` ADD CONSTRAINT `gardian_id_shift_foreign` FOREIGN KEY(`id_shift`) REFERENCES `shift`(`id_shift`);