# Mini TMS - Backend

## Présentation
Ce dépôt contient le backend de l'application Mini TMS, une solution de gestion pour une petite entreprise de transport. Le backend est développé en **Spring Boot** et utilise une base de données PostgreSQL pour stocker les données.

## Fonctionnalités

- **Gestion des conducteurs** : Ajout, modification, suppression et recherche de conducteurs.
- **Gestion des véhicules** : Ajout, modification, suppression et recherche de véhicules.
- **Gestion des trajets** : Ajout, modification, suppression et recherche de trajets avec filtres.
- **Tableau de bord** : Affichage des statistiques de base.
- **Authentification** : Gestion des utilisateurs avec permissions.
- **Notifications** : Système de notifications pour les trajets (e-mail ou SMS).
- **Intégration API externe** : Calcul des distances avec Google Maps API.

## Technologies utilisées

- **Spring Boot** : Framework Java pour le développement d'applications web.
- **PostgreSQL** : Base de données relationnelle.
- **Docker** : Conteneurisation de l'application.
- **GitHub Actions** : Automatisation des pipelines CI/CD.
- **JUnit & Mockito** : Tests unitaires et d'intégration.

## Structure du projet

- **`com.entreprise.transport.model`** : Contient les entités (Driver, Vehicle, Trip, Utilisateur).
- **`com.entreprise.transport.repository`** : Interfaces de repository pour accéder aux données.
- **`com.entreprise.transport.service`** : Services métier pour la gestion des conducteurs, véhicules, trajets et utilisateurs.
- **`com.entreprise.transport.serviceTest`** : Tests unitaires pour les services.

## Installation et Exécution
### Prérequis
- Java 17
- Maven
- Docker

### Étapes
1. Cloner le dépôt et naviguer dans le répertoire du projet :
   ```sh
   git clone git@github.com:Ouagaljr2/entrepriseDeTransport.git
   cd entrepriseDeTransport
   ```
   Cette commande clone le dépôt depuis GitHub et te place dans le répertoire racine du projet.

2. Construire et exécuter le projet localement :
   Pour construire le projet avec Maven et lancer le backend, utilise les commandes suivantes :
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```  
   Ces commandes vont télécharger les dépendances et démarrer le serveur Spring Boot.

3. Lancer l'application avec Docker :
   Pour exécuter l'application avec Docker, utilise :
   ```sh
   docker-compose up -d
   ```
   Cette commande fait plusieurs choses :
   -  Elle télécharge l'image Docker du backend depuis DockerHub si elle n'est pas déjà présente sur ta machine.
   -  Elle lance les conteneurs nécessaires pour la base de données PostgreSQL et le backend Java.
   -  L'option -d permet de démarrer les conteneurs en mode "détaché", ce qui signifie que les processus tournent en arrière-plan.
   
   Une fois ces étapes terminées, l'application sera prête à être utilisée.

## CI/CD avec GitHub Actions
Le pipeline CI/CD est défini dans `.github/workflows/ci-cd.yml`. Il inclut :
- Build et tests automatisés
- Construction et push de l'image Docker
- Déploiement automatique

## Déploiement avec Docker
L'application est conteneurisée avec **Docker**. L'image du backend est poussée sur DockerHub et peut être récupérée avec :
```sh
docker pull ouagaljr/mini-tms-backend
```

## API et Base de Données
L'API expose plusieurs endpoints pour gérer les trajets, conducteurs et véhicules. La base de données utilisée est PostgreSQL.

## Auteur
- **Ouagal Mahamat**
