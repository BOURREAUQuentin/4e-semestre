import click
from .app import app, db
@app.cli.command()
@click.argument('filename')
def loaddb(filename):
    '''Creates the tables and populates them with data. '''
    # création de toutes les tables
    db.create_all()
    # chargement de notre jeu de données
    import yaml
    books = yaml.safe_load(open(filename))
    # import des modèles
    from .models import Author, Book
    # première passe: création de tous les auteurs
    authors = {}
    for b in books:
        for author_name in b["authors"]:
            if author_name not in authors:
                author = Author(name=author_name)
                db.session.add(author)
                authors[author_name] = author
    db.session.commit()
    # deuxième passe: création de tous les livres
    for b in books:
        authors_list = [authors[author_name] for author_name in b["authors"]]
        print(authors_list)
        book_entry = Book(
            price=b["price"],
            title=b["title"],
            url=b["url"],
            img=b["img"],
            authors=authors_list
        )
        db.session.add(book_entry)
    db.session.commit()

@app.cli.command()
def syncdb():
    '''Creates all missing tables.'''
    db.create_all()

@app.cli.command()
@click.argument ('username')
@click.argument('password')
def newuser(username, password ):
    '''Adds a new user.'''
    from .models import User
    from hashlib import sha256
    m = sha256()
    m. update(password.encode())
    u = User(username=username, password=m.hexdigest())
    db.session.add(u)
    db.session.commit()

@app.cli.command()
@click.argument('username')
@click.argument('new_password')
def passwd(username, new_password):
    '''Change password for an existing user.'''
    from .models import User
    from hashlib import sha256
    # Recherche de l'utilisateur par son nom d'utilisateur
    user = User.query.filter_by(username=username).first()
    if user:
        # Hash du nouveau mot de passe
        m = sha256()
        m.update(new_password.encode())
        user.password = m.hexdigest()
        # Enregistrement des modifications dans la base de données
        db.session.commit()
        print(f"Mot de passe changé pour l'utilisateur {username}")
    else:
        print(f"Utilisateur {username} non trouvé.")