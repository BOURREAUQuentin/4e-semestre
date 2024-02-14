class Task {
    constructor(title, description, done, uri) {
        this.title = title;
        this.description = description;
        this.done = done;
        this.uri = uri;
        console.log(this.uri);
    }
}

// Récupération des boutons de la page
const buttonGET = document.getElementById("buttonGET");
const addButton = document.getElementById("add");
const addNewTaskButton = document.getElementById("buttonNewTask");
const divAddTask = document.getElementById("addtask");
const inputTitle = document.getElementById("title_input");
const inputDescription = document.getElementById("description_input");

buttonGET.addEventListener("click", refreshTaskList);
addButton.addEventListener("click", displayNewTaskForm);
addNewTaskButton.addEventListener("click", () => {
    if (inputTitle.value != "" && inputDescription.value != ""){
        const newTask = new Task(inputTitle.value, inputDescription.value, false, "");
        saveNewTask(newTask);
        displayNewTaskForm();
    }
});

// Fonction pour récupérer les tâches
function refreshTaskList() {
    const divCurrentTask = document.getElementById("currenttask");
    divCurrentTask.innerHTML = ''; // Vide le contenu de divCurrentTask

    const requete = "http://localhost:5000/todo/api/v1.0/tasks";
    fetch(requete)
        .then(response => {
            if (response.ok) return response.json();
            else throw new Error('Problème ajax: ' + response.status);
        })
        .then(remplirTaches)
        .catch(error => {
            console.error("Une erreur est survenue lors de la récupération des tâches:", error);
        });
}

// Fonction pour remplir les tâches récupérées dans la liste des tâches
function remplirTaches(tasks) {
    const divTasks = document.getElementById("taches");
    divTasks.innerHTML = ''; // Vide le contenu de divTasks

    const ulTasks = document.createElement("ul");
    divTasks.appendChild(ulTasks);

    for (let task of tasks["tasks"]) {
        const liTask = document.createElement("li");
        liTask.textContent = task.title;

        // Ajouter un événement de clic sur chaque tâche pour afficher les détails de la tâche
        liTask.addEventListener("click", () => {
            displayTaskDetails(task);
        });

        ulTasks.appendChild(liTask);
    }
}

// Fonction pour afficher/cacher le formulaire de création d'une nouvelle tâche
function displayNewTaskForm() {
    if (inputTitle.type == "hidden"){
        inputTitle.type = "text";
        inputDescription.type = "text";
        addNewTaskButton.style.display = "block";
    }
    else{ // on veut cacher le formulaire
        inputTitle.type = "hidden";
        inputTitle.value = "";
        inputDescription.type = "hidden";
        inputDescription.value = "";
        addNewTaskButton.style.display = "none";
    }
}

// Fonction pour enregistrer une nouvelle tâche sur le serveur
function saveNewTask(task) {
    fetch("http://localhost:5000/todo/api/v1.0/tasks", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(task)
    })
    .then(response => {
        if (response.ok) {
            console.log("La nouvelle tâche a été créée");
            // Actualise la liste des tâches après avoir créé une nouvelle tâche
            refreshTaskList();
        } else {
            throw new Error("Problème ajax : " + response.status);
        }
    })
    .catch(error => {
        console.error("Une erreur est survenue lors de la création de la nouvelle tâche : ", error);
    });
}

function displayTaskDetails(){
    divCurrentTask.innerHTML = "";
    const titreSpan = document.createElement("span");
    const inputTitre = document.createElement("input");
    titreSpan.textContent = "Titre";
    inputTitre.type = "text";

    const descriptionSpan = document.createElement("span");
    const inputDescription = document.createElement("input");
    descriptionSpan.textContent = "Titre";
    inputDescription.type = "text";

    const DoneSpan = document.createElement("span");
    const inputDone = document.createElement("input");
    DoneSpan.textContent = "Titre";
    inputDone.type = "checkbox";
    // TODO
}

// function formTask(isnew){
//     $("#currenttask").empty();
//     $("#currenttask")
//         .append($('<span>Titre<input type="text" id="titre"><br></span>'))
//         .append($('<span>Description<input type="text" id="descr"><br></span>'))
//         .append($('<span>Done<input type="checkbox" id="done"><br></span>'))
//         .append($('<span><input type="hidden" id="turi"><br></span>'))
//         .append(isnew?$('<span><input type="button" value="Save Task"><br></span>').on("click", saveNewTask)
//                      :$('<span><input type="button" value="Modify Task"><br></span>').on("click", saveModifiedTask)
//             );
//     }

// function fillFormTask(t){
//     $("#currenttask #titre").val(t.title);
//     $("#currenttask #descr").val(t.description);
//      t.uri=(t.uri == undefined)?"http://localhost:5000/todo/api/v1.0/tasks/"+t.id:t.uri;
//      $("#currenttask #turi").val(t.uri);
//     t.done?$("#currenttask #done").prop('checked', true):
//     $("#currenttask #done").prop('checked', false);
// }