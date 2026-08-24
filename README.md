# 🚀 Système de Gestion des Employés - DELFYO

Un système de gestion des employés moderne et complet développé avec **Spring Boot**, **Thymeleaf**, et **Bootstrap**.

---

## 🛠️ Configuration de la Base de Données

Dans le fichier `src/main/resources/application.properties` :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gestion_employes_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect