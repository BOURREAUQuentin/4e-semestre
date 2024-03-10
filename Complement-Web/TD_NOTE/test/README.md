# Test des requêtes fetch avec Curl

## Créer un nouveau post

```bash
curl -X POST -H "Content-Type: application/json" -d '{"title":"Nouveau post","views":100}' http://localhost:3000/posts
```