from .app import app, db
from flask import render_template, url_for, redirect, request
from .models import *
from flask_wtf import FlaskForm
from wtforms import StringField, HiddenField, PasswordField, TextAreaField, FloatField
from wtforms.validators import DataRequired
from hashlib import sha256
from flask_login import login_user, current_user, logout_user, login_required

@app.route("/")
def home():
    return render_template(
        "home.html",
        title="My Books !",
        books=get_sample())
    
@app.route("/detail/<id>", methods=("GET", "POST",))
def detail(id):
    print(id)
    book = get_book(id)
    print(book)
    average_rating = book.calculate_average_rating()
    comment_form = CommentForm()
    if comment_form.validate_on_submit() and current_user.is_authenticated:
        text = comment_form.text.data
        new_comment = Comment(text=text, username=current_user.username, book_id=id)
        db.session.add(new_comment)
        db.session.commit()
        # Redirige vers la même page après avoir ajouté le commentaire
        return redirect(url_for('detail', id=id))
    return render_template(
        "detail.html",
        book=book, average_rating=average_rating, comment_form=comment_form)

@app.route('/add_note/<int:book_id>', methods=['POST'])
def add_note(book_id):
    score = int(request.form['score'])
    new_note = Note(book_id=book_id, score=score)
    db.session.add(new_note)
    db.session.commit()
    new_average_rating = get_book(book_id).calculate_average_rating()
    if new_average_rating is not None:
        return render_template(
                "detail.html",
                book=get_book(book_id), average_rating=new_average_rating, comment_form=CommentForm())

class AuthorForm(FlaskForm):
    id = HiddenField('id')
    name = StringField('Nom', validators=[DataRequired()])

class CommentForm(FlaskForm):
    text = TextAreaField('Commentaire', validators=[DataRequired()])
    
class BookForm(FlaskForm):
    id = HiddenField('id')
    name = StringField('Nom', validators=[DataRequired()])
    price = FloatField('Prix', validators=[DataRequired()])

@app.route("/edit/author/<int:id>")
@login_required
def edit_author(id):
    a = get_author(id)
    f = AuthorForm(id=a.id ,name=a.name)
    return render_template(
        "edit-author.html",
        author=a, form=f)

@app.route("/edit/book/<int:id>")
@login_required
def edit_book(id):
    b = get_book(id)
    f = BookForm(id=b.id ,name=b.title,price=b.price)
    return render_template(
        "edit-book.html",
        book=b, form=f)

@app.route("/save/book/", methods =("POST",))
def save_book():
    b = None
    f = BookForm()
    name_book = f.name.data
    price_book = f.price.data
    find_error = False
    if f.validate_on_submit():
        for lettre in name_book:
            if lettre.isnumeric():
                # Ajoute une erreur au champ 'name'
                f.name.errors.append('Le nom doit pas contenir de chiffres!')
                find_error = True
                break
        try:
            int(price_book)  # Tente de convertir la valeur en entier
            if price_book <= 1:
                f.name.errors.append('Le prix doit être un nombre positif et supérieur à 1 euros.')
                find_error = True
        except (ValueError, TypeError):
            f.name.errors.append('Le prix doit être un nombre entier.')
            find_error = True
        if not find_error:
            id = int(f.id.data)
            b = get_book(id)
            b.title = f.name.data
            b.price = f.price.data
            db.session.commit()
            return redirect(url_for('detail', id=b.id))
    b = get_book(int(f.id.data))
    return render_template (
        "edit-book.html",
        book=b, form=f)

@app.route("/search/")
def search_book():
    print(request.args.get("query"))
    list_books = get_books_by_search(request.args.get("query"))
    return render_template(
        "search.html",
        books=list_books)

@app.route("/save/author/", methods =("POST",))
def save_author():
    a = None
    f = AuthorForm()
    name_author = f.name.data
    find_error = False
    if f.validate_on_submit():
        for lettre in name_author:
            if not lettre.isalpha() and not lettre.isspace():
                # Ajoute une erreur au champ 'name'
                f.name.errors.append('Le nom doit contenir uniquement des lettres!')
                find_error = True
                break
        if not find_error:
            id = int(f.id.data)
            a = get_author(id)
            a.name = f.name.data
            db.session.commit()
            return redirect(url_for('one_author', id=a.id))
    a = get_author(int(f.id.data))
    return render_template (
        "edit-author.html",
        author=a, form=f)

@app.route("/one_author/<int:id>")
def one_author(id):
    a = get_author(id)
    return render_template(
        "one-author.html",
        author=a,
        books=get_sample_author(a.id),
    )

@app.route("/add/author/")
def add_one_author():
    f = AuthorForm()
    return render_template(
        "add-bd.html", form=f)

@app.route("/add/author/validate", methods =("POST",))
def save_add_author():
    a = None
    f = AuthorForm()
    name_author = f.name.data
    find_error = False
    if f.validate_on_submit():
        for lettre in name_author:
            if not lettre.isalpha() and not lettre.isspace():
                # Ajoute une erreur au champ 'name'
                f.name.errors.append('Le nom doit contenir uniquement des lettres!')
                find_error = True
                break
        if not find_error:
            new_id = get_max_id_author() + 1
            add_author(new_id, f.name.data)
            return redirect(url_for('one_author', id=new_id))
    a = None
    return render_template (
        "add-bd.html",
        author=a, form=f)

class LoginForm(FlaskForm):
    username = StringField("Username")
    password = PasswordField("Password")
    next = HiddenField()
    def get_authenticated_user(self):
        user = User.query.get(self.username.data)
        if user is None:
            return None
        m = sha256()
        m. update(self.password.data.encode())
        passwd = m.hexdigest()
        return user if passwd == user.password else None

class RegistrationForm(FlaskForm):
    username = StringField("Nom d'utilisateur", validators=[DataRequired()])
    password = PasswordField('Mot de passe', validators=[DataRequired()])
    confirm_password = PasswordField('Confirmer le mot de passe', validators=[DataRequired()])
    next = HiddenField()
    def set_authenticated_user(self):
        m = sha256()
        m. update(self.password.data.encode())
        passwd = m.hexdigest()
        add_user(self.username.data, passwd)

@app.route("/login/", methods=("GET", "POST",))
def login():
    f = LoginForm()
    error_message = None  # Variable pour stocker le message d'erreur
    if not f.is_submitted():
        f.next.data = request.args.get("next")
    elif f.validate_on_submit():
        user = f.get_authenticated_user()
        if user:
            login_user(user)
            next = f.next.data or url_for("home")
            return redirect(next)
        else:
            error_message = "Nom d'utilisateur ou mot de passe incorrect"
            # Réinitialise le champ du username et du password
            f.username.data = ""
            f.password.data = ""
    return render_template("login.html", form=f, error_message=error_message)

@app.route("/logout/")
def logout():
    logout_user()
    return redirect(url_for('home'))

@app.route("/register/", methods=("GET", "POST",))
def register():
    f = RegistrationForm()
    error_message = None  # Variable pour stocker le message d'erreur
    if not f.is_submitted():
        f.next.data = request.args.get("next")
    elif f.validate_on_submit():
        username = f.username.data
        user = User.query.get(username)
        if user is not None:
            error_message = "Nom d'utilisateur déjà utilisé"
            # Réinitialise le champ du username
            f.username.data = ""
        else:
            password = f.password.data
            confirm_password = f.confirm_password.data
            if password == confirm_password:
                # inscription validée
                f.set_authenticated_user()
                return redirect(url_for("login"))
            else:
                error_message = "Mot de passe et confirmation différent"
                # Réinitialise le champ du password et du confirm_password
                f.password.data = ""
                f.confirm_password.data = ""
    return render_template("register.html", form=f, error_message=error_message)

@app.route('/delete_book/<int:id>', methods=['POST'])
def delete_book(id):
    book = get_book(id)
    if book:
        db.session.delete(book)
        db.session.commit()
    return redirect(url_for('home'))