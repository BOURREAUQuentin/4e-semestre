from .app import app, db

@app.cli.command()
def loaddb():
    '''Creates the tables and populates them with data. '''
    # création de toutes les tables
    db.create_all()
    # chargement de notre jeu de données
    from .models import Question, Questionnaire
    questionnaire_1 = Questionnaire(1, "Questionnaire simple")
    questionnaire_2 = Questionnaire(2, "Questionnaire dur")
    question_capitale_france = Question(1, "Quelle est la capitale de la France ?", "Paris", 1)
    question_capitale_madrid = Question(2, "Quelle est la capitale de l'Espagne ?", "Madrid", 1)
    question_tracteur = Question(3, "Est-ce qu'il faut détruire tout avec les tracteurs ?", "Oui", 2)
    question_attal = Question(4, "Est-ce que Attal est gay ?", "Oui", 2)
    db.session.add(questionnaire_1)
    db.session.add(questionnaire_2)
    db.session.add(question_capitale_france)
    db.session.add(question_capitale_madrid)
    db.session.add(question_tracteur)
    db.session.add(question_attal)
    db.session.commit()

@app.cli.command()
def syncdb():
    '''Creates all missing tables.'''
    db.create_all()

@app.cli.command()
def dropdb():
    '''Drops all tables.'''
    db.drop_all()