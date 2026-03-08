# Task Tracker CLI
https://roadmap.sh/projects/task-tracker

Application en ligne de commande pour gérer vos tâches quotidiennes.

## Technologies

- **Java 17+**
- **Maven** - Gestion des dépendances et build
- **JSON** - Stockage des données dans `tasks.json`

## Fonctionnalités

- ✅ Ajouter une nouvelle tâche
- ✅ Mettre à jour une tâche existante
- ✅ Supprimer une tâche
- ✅ Marquer une tâche comme en cours ou terminée
- ✅ Lister toutes les tâches
- ✅ Filtrer les tâches par statut (done, todo, in-progress)

## Structure des données

Chaque tâche contient :
- `id` : Identifiant unique
- `description` : Description de la tâche
- `status` : Statut (todo, in-progress, done)
- `createdAt` : Date de création (ISO 8601)
- `updatedAt` : Date de dernière modification (ISO 8601)

## Prérequis

- Java 17 ou supérieur
- Maven 3.6 ou supérieur

## Installation

```bash
# Cloner le projet
git clone https://github.com/leo09-art/task_tracker_cli.git
cd task_tracker_cli

# Compiler le projet
mvn clean package

Utilisation

# Ajouter une tâche
task-cli add "Acheter du pain"

# Mettre à jour une tâche
task-cli update <id> "Nouvelle description"

# Supprimer une tâche
task-cli delete <id>

# Marquer une tâche comme en cours
task-cli mark-in-progress <id>

# Marquer une tâche comme terminée
task-cli mark-done <id>

# Lister toutes les tâches
task-cli list

# Lister les tâches par statut
task-cli list done
task-cli list todo
task-cli list in-progress


Stockage

# Ajouter une tâche
$ task-cli add "Faire les courses"


# Lister les tâches
$ task-cli list


# Marquer comme terminée
$ task-cli mark-done <id>

