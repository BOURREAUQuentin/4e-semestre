from .app import db

class Questionnaire(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(100))

    def __init__ (self, id, name):
        self.id = id
        self.name = name
    
    def __repr__ (self):
        return "<Questionnaire (%d) %s>"%(self.id, self.name)

    def to_json(self):
        json={
            'id': self.id,
            'name': self.name,
        }
        return json

def get_les_questionnaires():
    return Questionnaire.query.all()

def get_le_questionnaire(id_questionnaire):
    return Questionnaire.query.get(id_questionnaire)

def get_les_questions_questionnaire(id_questionnaire):
    return Question.query.filter(Question.questionnaire_id == id_questionnaire)

def add_questionnaire(id_questionnaire, nom_questionnaire):
    nouveau_questionnaire = Questionnaire(id=id_questionnaire, name=nom_questionnaire)
    db.session.add(nouveau_questionnaire)
    db.session.commit()

class Question(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(120))
    answer = db.Column(db.String(120))
    questionnaire_id = db.Column(db.Integer, db.ForeignKey('questionnaire.id'))
    questionnaire = db.relationship("Questionnaire", backref = db.backref("questions", lazy="dynamic"))

    def __init__ (self, id, title, answer, questionnaire_id):
        self.id = id
        self.title = title
        self.answer = answer
        self.questionnaire_id = questionnaire_id
    
    def to_json(self):
        json={
            'id': self.id,
            'title': self.title,
            'answer': self.answer,
            'questionnaire_id': self.questionnaire_id,
        }
        return json