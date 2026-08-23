# 🚀 Projet de Gestion des Employés (Spring Boot & Thymeleaf)

Une application web complète développée avec **Spring Boot**, **Spring Security**, **Thymeleaf** et **MySQL** pour la gestion des employés, des départements, des postes et le suivi des journaux d'audit.

---

## 📋 Prérequis

Avant de commencer, assurez-vous d'avoir installé :

- **Java JDK 17** ou supérieur
- **MySQL Server** (ou via XAMPP / WampServer)
- **Maven**
- **Git**

---

## 🛠️ Configuration et Installation

### 1. Cloner le projet
`git clone https://github.com/siham-1-11/gestion-employes.git`  
`cd gestion-employes`

### 2. Configuration de la Base de Données
1. Ouvrez **MySQL Workbench** ou **phpMyAdmin**.
2. Exécutez le script SQL situé dans le projet sous :  
   `src/main/resources/schema.sql`  
   *(Ce script crée la base de données `gestion_employes_db` ainsi que l'ensemble des tables nécessaires).*

3. Mettez à jour vos identifiants MySQL dans le fichier `src/main/resources/application.properties` si nécessaire :

- **URL :** `jdbc:mysql://localhost:3306/gestion_employes_db?useSSL=false&serverTimezone=UTC`
- **Username :** `root`
- **Password :** *(laissez vide si pas de mot de passe)*

---

## ⚙️ Exécution de l'Application

### Via le Terminal :
- Sous Linux / Mac : `./mvnw spring-boot:run`
- Sous Windows PowerShell : `.\mvnw spring-boot:run`

L'application sera accessible sur : **`http://localhost:8080`**

---

## ✨ Fonctionnalités Principales

- **Authentification & Sécurité :** Connexion sécurisée avec Spring Security et gestion des rôles (Admin / User).
- **Gestion des Employés :** Ajout, modification, suppression et affichage des employés.
- **Gestion des Départements & Postes :** Organisation de la structure de l'entreprise.
- **Tableau de Bord & Audit :** Consultation des statistiques et des journaux d'audit (Audit Logs).
