from flask import jsonify, abort, make_response, request, url_for
from .app import app
from .models import tasks

def make_public_task(task):
    new_task = {}
    for field in task:
        if field == "id":
            new_task["uri"] = url_for("get_task", task_id=task["id"], _external=True)
        else:
            new_task[field] = task[field]
    return new_task

@app.route("/todo/api/v1.0/tasks", methods = ["GET"])
def get_tasks():
    return jsonify(tasks = [make_public_task(t) for t in tasks])

@app.route("/todo/api/v1.0/tasks/<int:task_id>", methods = ["GET"])
def get_task(task_id):
    print(task_id)
    if len(tasks) >= task_id and task_id != 0:
        return jsonify(task = tasks[task_id-1])
    abort(404)

@app.errorhandler(404)
def not_found(error):
    return make_response(jsonify({"error": "Not found"}), 404)

@app.errorhandler(400)
def not_found(error):
    return make_response(jsonify({"error": "Bad request"}), 400)

@app.route("/todo/api/v1.0/tasks", methods=["POST"])
def create_task():
    if not request.json or not 'title' in request.json:
        abort(404)
    task = {
        "id": tasks[-1]["id"] + 1,
        "title": request.json["title"],
        "description": request.json.get("description", ""),
        "done": False
    }
    tasks.append(task)
    return jsonify({"task": make_public_task(task)}), 201

@app.route("/todo/api/v1.0/tasks/<int:task_id>", methods=["PUT"])
def update_task(task_id):
    task = [task for task in tasks if task["id"] == task_id]
    if len(task) == 0:
        abort(404)
    if not request.json:
        abort(400)
    if "title" in request.json and type(request.json["title"]) != str:
        abort(400)
    if "description" in request.json and type(request.json["description"]) is not str:
        abort(400)
    if "done" in request.json and type(request.json["done"]) is not bool:
        abort(404)
    task[0]["title"] = request.json.get('title', task[0]['title'])
    task[0]['description'] = request.json.get('description', task[0]['description'])
    task[0]['done'] = request.json.get('done', task[0]['done'])
    return jsonify({'task': make_public_task(task[0])})

@app.route("/todo/api/v1.0/tasks/<int:task_id>", methods=["DELETE"])
def delete_task(task_id):
    task = [task for task in tasks if task["id"] == task_id]
    print(len(task))
    if len(task) == 0:
        abort(404)
    tasks.remove(task[0])
    return jsonify({'message': 'Task supprimée correctement'})