# 🏥 Cabinet Plus

**Cabinet Plus** est une application Java de gestion d’un cabinet médical, réalisée dans le cadre d’un projet éducatif.

L’objectif du projet est de développer une application permettant de **centraliser et organiser les informations d’un cabinet**, notamment les patients, les médecins, les rendez-vous et les consultations.

## 🎯 Objectif du projet

L’application permet de simplifier la gestion quotidienne d’un cabinet médical en offrant des fonctionnalités pour :

- 👤 Gérer les informations des patients
- 👨‍⚕️ Gérer les médecins
- 📅 Organiser les rendez-vous
- 🩺 Enregistrer les consultations
- 📋 Consulter les informations et l’historique des patients
- 💾 Stocker les données dans une base de données PostgreSQL

## 🛠️ Technologies utilisées

- **Java** — langage principal
- **Maven** — gestion du projet et des dépendances
- **PostgreSQL** — système de gestion de base de données
- **JDBC** — connexion entre Java et PostgreSQL
- **Docker** — conteneurisation de la base de données
- **Git / GitHub** — gestion et partage du code source

## 🏗️ Organisation du projet

Le projet est organisé autour de plusieurs couches afin de séparer les responsabilités :

```text
src/
├── main/
│   └── java/
│       ├── model/
│       ├── dao/
│       ├── service/
│       └── ...
└── docker/
    └── configuration PostgreSQL
```

Cette organisation permet de mieux structurer l'application et de faciliter sa maintenance et son évolution.

## 🗃️ Principales données gérées

La base de données contient notamment les tables :

- **Patient** : informations personnelles et dossier du patient
- **Medecin** : informations et authentification du médecin
- **RendezVous** : date, heure, motif et statut du rendez-vous
- **Consultation** : informations relatives aux consultations

Les relations entre ces données permettent notamment d'associer les rendez-vous et les consultations aux patients concernés.

## 🐳 Base de données avec Docker

PostgreSQL peut être lancé dans un conteneur Docker afin de fournir un environnement de développement reproductible.

Exemple :

```bash
docker compose up -d
```

Puis, pour accéder à PostgreSQL :

```bash
docker exec -it cabinet_postgress psql -U cabinet -d cabinet_db
```

## 🎓 Contexte pédagogique

Ce projet a été réalisé dans un objectif pédagogique afin de mettre en pratique plusieurs notions étudiées en développement logiciel, notamment :

- Programmation orientée objet avec Java
- Architecture en couches
- Accès aux données avec JDBC
- Gestion d'une base de données relationnelle
- Requêtes SQL
- Gestion des dépendances avec Maven
- Conteneurisation avec Docker
- Utilisation de Git et GitHub

## 🚀 Perspectives d'amélioration

Le projet peut être enrichi avec plusieurs fonctionnalités :

- Interface graphique plus complète
- Gestion des rôles et des utilisateurs
- Recherche et filtrage des patients
- Gestion des prescriptions médicales
- Génération de rapports
- Validation renforcée des données
- Sécurisation des mots de passe
- Tests unitaires et tests d'intégration

## 👩‍💻 Auteur

**Meryem Ibrahimi**

Étudiante en **Informatique Appliquée — Ingénierie Logicielle**

---

⭐ Projet éducatif réalisé dans le but de mettre en pratique les concepts de développement d'applications Java, de bases de données et de Docker.

connexion medecin : 

login : medecin 
pass : 1234

pour connecter la base : docker exec -it cabinet_postgress psql -U cabinet -d cabinet_db
pour cree utilisatuer par default : 
docker exec -it cabinet_postgress psql -U cabinet -d cabinet_db -c "INSERT INTO medecin (nom, prenom, username, password) VALUES ('Médecin', 'Test', 'medecin', '1234');"
