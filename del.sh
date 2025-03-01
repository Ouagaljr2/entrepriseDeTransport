#!/bin/bash

# Arrêter tous les conteneurs en cours d'exécution
echo "Arrêt de tous les conteneurs en cours d'exécution..."
docker stop $(docker ps -aq)

# Supprimer tous les conteneurs
echo "Suppression de tous les conteneurs..."
docker rm -f $(docker ps -aq)

# Supprimer tous les volumes non utilisés
echo "Suppression de tous les volumes non utilisés..."
docker volume prune -f

# Supprimer toutes les images
echo "Suppression de toutes les images..."
docker rmi -f $(docker images -aq)

# Supprimer tous les réseaux non utilisés
echo "Suppression de tous les réseaux non utilisés..."
docker network prune -f

# Nettoyer le système Docker (supprime les données inutilisées)
echo "Nettoyage du système Docker..."
docker system prune -a -f --volumes

echo "Nettoyage terminé !"
