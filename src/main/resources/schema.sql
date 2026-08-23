-- Create Database
CREATE DATABASE IF NOT EXISTS `gestion_employes_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `gestion_employes_db`;

-- Table: roles
CREATE TABLE IF NOT EXISTS `roles` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: users
CREATE TABLE IF NOT EXISTS `users` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `username` VARCHAR(50) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `enabled` BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: user_roles
CREATE TABLE IF NOT EXISTS `user_roles` (
  `user_id` BIGINT NOT NULL,
  `role_id` BIGINT NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: departements
CREATE TABLE IF NOT EXISTS `departements` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `nom` VARCHAR(100) NOT NULL,
  `description` TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: postes
CREATE TABLE IF NOT EXISTS `postes` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `titre` VARCHAR(100) NOT NULL,
  `salaire_de_base` DOUBLE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: employes
CREATE TABLE IF NOT EXISTS `employes` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `nom` VARCHAR(50) NOT NULL,
  `prenom` VARCHAR(50) NOT NULL,
  `email` VARCHAR(100) NOT NULL UNIQUE,
  `telephone` VARCHAR(20),
  `date_embauche` DATE,
  `departement_id` BIGINT,
  `poste_id` BIGINT,
  FOREIGN KEY (`departement_id`) REFERENCES `departements` (`id`) ON DELETE SET NULL,
  FOREIGN KEY (`poste_id`) REFERENCES `postes` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: audit_logs
CREATE TABLE IF NOT EXISTS `audit_logs` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `action` VARCHAR(255) NOT NULL,
  `username` VARCHAR(100) NOT NULL,
  `timestamp` DATETIME NOT NULL,
  `details` TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Initial Data (Optional)
INSERT INTO `roles` (`id`, `name`) VALUES (1, 'ROLE_ADMIN'), (2, 'ROLE_USER') ON DUPLICATE KEY UPDATE `name`=`name`;