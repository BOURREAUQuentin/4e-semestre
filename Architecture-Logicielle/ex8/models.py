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

class Question(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(120))
    questionType = db.Column(db.String(120))
    questionnaire_id = db.Column(db.Integer, db.ForeignKey('questionnaire.id'))
    questionnaire = db.relationship("Questionnaire", backref = db.backref("questions", lazy="dynamic"))

    def __init__ (self, id, title, questionType, questionnaire_id):
        self.id = id
        self.title = title
        self.questionType = questionType
        self.questionnaire_id = questionnaire_id