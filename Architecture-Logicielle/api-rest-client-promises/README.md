# Application Client/Serveur avec une API REST

## Lancer mon projet

Pour lancer mon projet, vous devez respecter ces commandes dans l'ordre suivant.
1. Déplacer à la racine du projet
2. Lancer le serveur avec la commande suivante :
```bash
flask run
```
3. Lancer le client avec la commande suivante :
```bash
php -S localhost:8000
```
4. Redigirez vous vers le lien de la dernière commande : http://localhost:8000

## Les requêtes curl

Dans la suite du README, je vous présente une commande curl pour chaque fonctionnalité. Ainsi, ces commandes curl vous permettront de tester les différentes fonctionnalités de la classe Task en simulant les requêtes HTTP.

### Commande curl pour GET (voir les taches)

```bash
curl http://localhost:5000/todo/api/v1.0/tasks
```

### Commande curl pour GET (voir une tache spécifique)

Vous avez uniquement à remplacer <taskId> par l'identifiant de la tâche que vous souhaitez voir en détail.

```bash
curl http://localhost:5000/todo/api/v1.0/tasks/<taskId>
```

### Commande curl pour PUT (modifier une tache)

Vous avez uniquement à remplacer <taskId> par l'identifiant de la tâche que vous souhaitez modifier, <newTitle> par le nouveau titre et <newDescription> par la nouvelle description.

```bash
curl -X PUT -H "Content-Type: application/json" -d '{"title":"<newTitle>", "description":"<newDescription>", "done":false}' http://localhost:5000/todo/api/v1.0/tasks/<taskId>
```

### Commande curl pour DELETE (supprimer une tache)

Vous avez uniquement à remplacer <taskId> par l'identifiant de la tâche que vous souhaitez supprimer.

```bash
curl -X DELETE http://localhost:5000/todo/api/v1.0/tasks/<taskId>```
```

### Commande curl pour POST (ajouter une tache)

Vous avez uniquement à remplacer <taskId> par l'identifiant de la tâche que vous souhaitez valider ou annuler.

```bash
curl -X POST -H "Content-Type: application/json" -d '{"title":"<title>", "description":"<description>", "done":false, "uri":"http://localhost:5000/todo/api/v1.0/tasks"}' http://localhost:5000/todo/api/v1.0/tasks
```

### Commande curl pour valider ou annuler une tache

Vous avez uniquement à remplacer <taskId> par l'identifiant de la tâche que vous souhaitez modifier et <newState> par "true" pour valider la tâche ou "false" pour annuler la validation.

```bash
curl -X PUT -H "Content-Type: application/json" -d '{"done":<newState>}' http://localhost:5000/todo/api/v1.0/tasks/<taskId>
```

## Les fonctionnalités implémentées

Au niveau des fonctionnalités implémentées, l'utilisateur peut faire les actions suivantes :
- Voir une tache (sa description)
- Modifier une tache (son titre ou sa description)
- Supprimer une tache
- Ajouter une tache
- Valider ou annuler une tache (passe en réalisée ou non-réalisée)

#

BOURREAU Quentin - BUT informatique 2.3.B