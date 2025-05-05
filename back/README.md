# Yoga App - Backend

## Prérequis

* Java 11 ou plus
* Maven 3.6+
* IDE recommandé : IntelliJ IDEA ou VS Code
* MySQL ou MariaDB installé localement

## Installation

1. Clonez le dépôt Git :

   ```bash
   git clone https://github.com/Shishifoxy/Testez-une-application-full-stack
   cd back
   ```

2. Installez les dépendances :

   ```bash
   mvn clean install
   ```

3. Créez une base de données `test` et exécutez le script SQL disponible dans :

   ```
   src/main/resources/sql/script.sql
   ```

## Utilisation

Pour lancer l'application localement :

```bash
mvn spring-boot:run
```

Par défaut, l'application sera accessible sur :

```
http://localhost:8080
```

## Lancement des tests

```bash
mvn test
```

Tous les tests unitaires et d'intégration seront exécutés automatiquement.

## Génération du rapport de couverture

```bash
mvn jacoco:prepare-agent test jacoco:report
```

Le rapport HTML sera disponible dans :

```
./target/site/jacoco/index.html
```

## Remarques

* Le dossier `dto` ne contient pas de tests, comme requis.
* Les autres packages (controllers, services, models) disposent d'une couverture suffisante (>=80%).
