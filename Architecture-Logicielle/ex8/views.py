from flask import jsonify, abort, make_response, request, url_for
from .app import app, db
from .models import *

def make_public_questionnaire(questionnaire):
    new_questionnaire = {}
    questionnaire_json = questionnaire.to_json()
    for field in questionnaire_json:
        if field == "id":
            new_questionnaire["uri"] = url_for("get_questionnaire", questionnaire_id=questionnaire_json["id"], _external=True)
        else:
            new_questionnaire[field] = questionnaire_json[field]
    return new_questionnaire

@app.route("/ex8/api/v2.0/questionnaires", methods = ["GET"])
def get_questionnaires():
    questionnaires = get_les_questionnaires()
    return jsonify(questionnaires = [make_public_questionnaire(questionnaire) for questionnaire in questionnaires])

@app.route("/ex8/api/v2.0/questionnaires/<int:questionnaire_id>", methods = ["GET"])
def get_questionnaire(questionnaire_id):
    questionnaire_trouve = get_le_questionnaire(questionnaire_id)
    if questionnaire_trouve is not None:
        les_questions = get_les_questions_questionnaire(questionnaire_id)
        questionnaire_json = questionnaire_trouve.to_json()
        questionnaire_json["questions"] = [question.to_json() for question in les_questions]
        return jsonify(questionnaire = questionnaire_json)
    abort(404)

@app.errorhandler(404)
def not_found(error):
    return make_response(jsonify({"error": "Not found"}), 404)

@app.errorhandler(400)
def not_found(error):
    return make_response(jsonify({"error": "Bad request"}), 400)

@app.route("/ex8/api/v2.0/questionnaires", methods=["POST"])
def create_questionnaire():
    questionnaires = get_les_questionnaires()
    if not request.json or not 'name' in request.json:
        abort(404)
    # revoir le create -> à ajouter dans la bd
    questionnaire = {
        "id": questionnaires[-1]["id"] + 1,
        "name": request.json["name"]
    }
    questionnaires.append(questionnaire)
    return jsonify({"questionnaire": make_public_questionnaire(questionnaire)}), 201