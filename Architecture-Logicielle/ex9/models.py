from .app import db

class Questionnaire(db.Model):
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    name = db.Column(db.String(100))

    def __init__ (self, name):
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

def add_questionnaire(nom_questionnaire):
    nouveau_questionnaire = Questionnaire(name=nom_questionnaire)
    db.session.add(nouveau_questionnaire)
    db.session.commit()

def update_questionnaire(id_questionnaire, new_name):
    questionnaire = Questionnaire.query.get(id_questionnaire)
    questionnaire.name = new_name
    db.session.commit()

class Question(db.Model):
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    title = db.Column(db.String(120))
    question_type = db.Column(db.String(12))
    questionnaire_id = db.Column(db.Integer, db.ForeignKey('questionnaire.id'))
    questionnaire = db.relationship("Questionnaire", backref = db.backref("questions", lazy="dynamic"))

    def __init__ (self, title, question_type, questionnaire_id):
        self.title = title
        self.question_type = question_type
        self.questionnaire_id = questionnaire_id
    
    __mapper_args__ = {
        'polymorphic_identity': 'question',
        'polymorphic_on': question_type,
        'with_polymorphic': '*'
    }
    
    def to_json(self):
        json={
            'id': self.id,
            'title': self.title,
            'question_type': self.question_type,
            'questionnaire_id': self.questionnaire_id,
        }
        return json

class QuestionSimple(Question):
    id = db.Column(db.Integer, db.ForeignKey('question.id'), primary_key=True)
    first_answer = db.Column(db.String(120))
    second_answer = db.Column(db.String(120))
    
    __mapper_args__ = {
        'polymorphic_identity': 'questionsimple',
        'polymorphic_load': 'inline',
        'with_polymorphic': '*'
    }

    def __init__ (self, title, question_type, first_answer, second_answer, questionnaire_id):
        super().__init__(title, question_type, questionnaire_id)
        self.first_answer = first_answer
        self.second_answer = second_answer
    
    def to_json(self):
        json = super().to_json()
        json.update({
            'first_answer': self.first_answer,
            'second_answer': self.second_answer,
        })
        return json

class QuestionMultiple(Question):
    id = db.Column(db.Integer, db.ForeignKey('question.id'), primary_key=True)
    first_answer = db.Column(db.String(120))
    second_answer = db.Column(db.String(120))
    third_answer = db.Column(db.String(120))
    four_answer = db.Column(db.String(120))

    __mapper_args__ = {
        'polymorphic_identity': 'questionmultiple',
        'polymorphic_load': 'inline',
        'with_polymorphic': '*'
    }

    def __init__ (self, title, question_type, first_answer, second_answer, third_answer, four_answer, questionnaire_id):
        super().__init__(title, question_type, questionnaire_id)
        self.first_answer = first_answer
        self.second_answer = second_answer
        self.third_answer = third_answer
        self.four_answer = four_answer
    
    def to_json(self):
        json = super().to_json()
        json.update({
            'first_answer': self.first_answer,
            'second_answer': self.second_answer,
            'third_answer': self.third_answer,
            'four_answer': self.four_answer,
        })
        return json