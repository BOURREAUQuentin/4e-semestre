from .app import db
from sqlalchemy import desc
from flask_login import UserMixin
from .app import login_manager

class Author(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(100))
    
    def __repr__(self):
        return "%s" % (self.name)

book_author_association = db.Table('book_author_association',
    db.Column('book_id', db.Integer, db.ForeignKey('book.id')),
    db.Column('author_id', db.Integer, db.ForeignKey('author.id'))
)

class Book(db.Model):
    id = db.Column(db.Integer, primary_key =True)
    price = db.Column(db.Float)
    title = db.Column(db.String(100))
    url = db.Column(db.String(100))
    img = db.Column(db.String(100))
    authors = db.relationship('Author', secondary=book_author_association, backref=db.backref('books', lazy='dynamic'))

    def add_author(self, author):
        if author not in self.authors:
            self.authors.append(author)
            db.session.commit()

    def add_note(self, score):
        try:
            new_note = Note(book_id=self.id, score=score)
            db.session.add(new_note)
            db.session.commit()
            return "Note ajoutée avec succès"
        except Exception as e:
            db.session.rollback()
            return f"Erreur lors de l'ajout de la note : {str(e)}"

    def calculate_average_rating(self):
        total_score = 0
        num_notes = 0
        notes = Note.query.filter_by(book_id=self.id).all()
        for note in notes:
            total_score += note.score
            num_notes += 1
        if num_notes > 0:
            average_rating = total_score / num_notes
            return average_rating
        else:
            return 0  # Aucune note pour ce livre

    def __repr__(self):
        return "<Book : (%d) %s>" % (self.id, self.title)

class Note(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    score = db.Column(db.Integer)
    book_id = db.Column(db.Integer, db.ForeignKey("book.id"))
    book = db.relationship("Book", backref=db.backref("notes", lazy="dynamic"))

    def __repr__(self):
        return "<Note : (%d) %d>" % (self.id, self.score)

class Comment(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    text = db.Column(db.Text)  # Le texte du commentaire
    username = db.Column(db.String(50), db.ForeignKey('user.username'))  # L'utilisateur qui a laissé le commentaire
    book_id = db.Column(db.Integer, db.ForeignKey('book.id'))  # Le livre sur lequel porte le commentaire
    book = db.relationship('Book', backref=db.backref('comments', lazy='dynamic'))
    user = db.relationship('User', backref=db.backref('comments', lazy='dynamic'))

class User(db.Model, UserMixin):
    username = db.Column(db.String(50), primary_key=True)
    password = db. Column(db.String(64))

    def get_id(self):
        return self.username

@login_manager.user_loader
def load_user(username):
    return User.query.get(username)

def get_sample():
    return Book.query.limit(30).all()

def get_sample_author(id):
    return Book.query.filter_by(author_id=id).all()

def get_book(id):
    return Book.query.get(id)

def get_author(id):
    return Author.query.get(id)

def get_books_by_search(search):
    return Book.query.filter(Book.title.contains(search))

def get_max_id_author():
    max_id = db.session.query(Author).order_by(desc(Author.id)).first()
    if max_id:
        return max_id.id
    else:
        return 0  # valeur par défaut si aucun author

def add_author(id, name):
    try:
        new_author = Author(id=id, name=name)
        db.session.add(new_author)
        db.session.commit()
        return "Auteur ajouté avec succès"
    except Exception as e:
        db.session.rollback()  # annule toute opération en cours dans la session
        return f"Erreur lors de l'ajout de l'auteur : {str(e)}"

def add_user(username, password):
    try:
        new_user = User(username=username, password=password)
        db.session.add(new_user)
        db.session.commit()
        return "Utilisateur ajouté avec succès"
    except Exception as e:
        db.session.rollback()  # annule toute opération en cours dans la session
        return f"Erreur lors de l'ajout de l'utilisateur : {str(e)}"