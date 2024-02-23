class Task {
    constructor(title, description, done, uri) {
        this.title = title;
        this.description = description;
        this.done = done;
        this.uri = uri;
    }

    // Méthode pour créer l'élément HTML représentant la tâche
    createTaskElement() {
        const divTache = document.createElement("div");
        const numeroTache = document.createElement("h4");
        numeroTache.textContent = "Tache";
        divTache.appendChild(numeroTache);
    
        const h5Titre = document.createElement("h5");
        h5Titre.textContent = this.title;
        divTache.appendChild(h5Titre);
    
        const pDescription = document.createElement("p");
        pDescription.style.display = "none"; // Masque par défaut
        pDescription.textContent = this.description;
        divTache.appendChild(pDescription);
    
        // Création du conteneur pour les boutons et la checkbox
        const buttonsContainer = document.createElement("div");
        buttonsContainer.className = "buttons-container";
    
        // Création des boutons et de la checkbox
        const boutonVoir = this.createButton("Voir", () => this.voirTache(pDescription));
        const imgVoir = document.createElement("img");
        imgVoir.src = "./img/voir.png";
        boutonVoir.appendChild(imgVoir);
        const boutonModifier = this.createButton("Modifier", () => this.toggleEditForm(divTache));
        const imgModifier = document.createElement("img");
        imgModifier.src = "./img/modifier.png";
        boutonModifier.appendChild(imgModifier);
        const boutonSupprimer = this.createButton("Supprimer", this.supprimerTache.bind(this));
        const imgSupprimer = document.createElement("img");
        imgSupprimer.src = "./img/supprimer.png";
        boutonSupprimer.appendChild(imgSupprimer);
        const checkboxRealisee = this.createCheckbox("Réalisée", this.toggleDone.bind(this));
    
        // Ajout des boutons et de la checkbox au conteneur
        buttonsContainer.appendChild(boutonVoir);
        buttonsContainer.appendChild(boutonModifier);
        buttonsContainer.appendChild(boutonSupprimer);
        buttonsContainer.appendChild(checkboxRealisee);
    
        // Ajout du conteneur au div de la tâche
        divTache.appendChild(buttonsContainer);

        // Ajout d'une bordure inférieure
        divTache.style.paddingBottom = "20px";
        divTache.style.borderBottom = "1px solid black";
    
        return divTache;
    }    

    // Méthode pour créer un bouton avec un texte et un gestionnaire d'événements
    createButton(text, onClickHandler) {
        const bouton = document.createElement("button");
        bouton.textContent = text;
        bouton.addEventListener("click", onClickHandler);
        return bouton;
    }

    // Méthode pour créer une checkbox avec un label et un gestionnaire d'événements
    createCheckbox(label, onChangeHandler) {
        // Créer l'élément label
        const labelElement = document.createElement("label");
        labelElement.className = "container";
        
        // Créer l'élément input (checkbox)
        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.checked = this.done;
        checkbox.addEventListener("change", onChangeHandler);
        
        // Créer l'élément div pour le style personnalisé
        const checkmark = document.createElement("div");
        checkmark.className = "checkmark";
        
        // Ajouter l'élément input et l'élément div au label
        labelElement.appendChild(checkbox);
        labelElement.appendChild(checkmark);
        
        return labelElement;
    }

    // Méthode pour voir la tache
    voirTache(pDescription) {
        fetch(this.uri)
            .then(response => {
                if (response.ok) return response.json();
                else throw new Error("Problème ajax: " + response.status);
            })
            .then(data => {
                // Afficher ou cacher la description en fonction de son état actuel
                if (pDescription.style.display === "none") {
                    pDescription.style.display = "block";
                }
                else {
                    pDescription.style.display = "none";
                }
            })
            .catch(error => {
                console.error("Une erreur est survenue lors de la récupération de la tâche: ", error);
            });
    }

    // Méthode pour modifier la tâche dans le serveur
    modifierTache(newTitle, newDescription) {
        // Envoyer les nouvelles informations au serveur
        fetch(this.uri, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                title: newTitle,
                description: newDescription,
                done: this.done
            })
        })
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            else {
                throw new Error("Problème ajax: " + response.status);
            }
        })
        .then(data => {
            // Mise à jour de l'affichage après avoir modifié les informations de la tâche
            majAffichageTaches();
        })
        .catch(error => {
            console.error("Une erreur est survenue lors de la mise à jour de la tâche: ", error);
        });
    }

    // Méthode pour créer le formulaire de modification de la tâche
    createEditForm() {
        // Créer le formulaire
        const form = document.createElement("form");
        form.setAttribute("id", "editForm");

        // Créer les éléments du formulaire
        const titleLabel = document.createElement("label");
        titleLabel.textContent = "Titre:";
        const titleInput = document.createElement("input");
        titleInput.setAttribute("type", "text");
        titleInput.setAttribute("name", "title");
        titleInput.setAttribute("value", this.title);
        const descriptionLabel = document.createElement("label");
        descriptionLabel.textContent = "Description:";
        const descriptionTextarea = document.createElement("textarea");
        descriptionTextarea.setAttribute("name", "description");
        descriptionTextarea.textContent = this.description;
        const submitButton = document.createElement("input");
        const imgSauvegarder = document.createElement("img");
        imgSauvegarder.src = "./img/sauvegarder.png";
        submitButton.appendChild(imgSauvegarder);
        submitButton.setAttribute("type", "submit");
        submitButton.setAttribute("value", "Modifier");

        // Ajouter les éléments au formulaire
        form.appendChild(titleLabel);
        form.appendChild(titleInput);
        form.appendChild(descriptionLabel);
        form.appendChild(descriptionTextarea);
        form.appendChild(submitButton);

        // Ajouter un gestionnaire d'événements pour soumettre le formulaire
        form.addEventListener("submit", (event) => {
            event.preventDefault();
            const formData = new FormData(form);
            const newTitle = formData.get("title");
            const newDescription = formData.get("description");
            this.modifierTache(newTitle, newDescription);
            form.remove(); // Supprimer le formulaire après la soumission
        });

        return form;
    }

    // Méthode pour afficher ou masquer le formulaire de modification de la tâche
    toggleEditForm(taskElement) {
        const editForm = taskElement.querySelector("#editForm");
        if (editForm) {
            // Si le formulaire est déjà affiché, basculer son style pour le cacher ou le rendre visible
            editForm.style.display = editForm.style.display === "none" ? "block" : "none";
        } else {
            // Sinon, créer et afficher le formulaire dans la div de la tâche
            const form = this.createEditForm();
            taskElement.appendChild(form);
        }
    }

    // Méthode pour supprimer la tâche
    supprimerTache() {
        console.log("Supprimer la tâche:", this);
        fetch(this.uri, {
            method: 'DELETE',
        })
        .then(response => {
            if (response.ok) {
                // Mettre à jour l'affichage après avoir supprimé la tâche
                majAffichageTaches();
            }
            else {
                throw new Error("Problème ajax: " + response.status);
            }
        })
        .catch(error => {
            console.error("Une erreur est survenue lors de la suppression de la tâche: ", error);
        });
    }

    // Méthode pour basculer l'état de la tâche (réalisée ou non)
    toggleDone(event) {
        fetch(this.uri, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                title: this.title,
                description: this.description,
                done: !this.done // Inverse l'état done actuel
            })
        })
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            else {
                throw new Error("Problème ajax: " + response.status);
            }
        })
        .then(data => {
            // Mise à jour de l'affichage après avoir modifié l'état de la tâche
            majAffichageTaches();
        })
        .catch(error => {
            console.error("Une erreur est survenue lors de la mise à jour de l'état de la tâche: ", error);
        });
    }
}

// Fonction pour afficher les tâches récupérées dans la liste des tâches
function afficherTaches(tasks) {
    const divTachesAFaire = document.getElementById('taches-a-faire');
    const divTachesRealisees = document.getElementById("taches-realisees");
    const divAddTache = document.getElementById("add-tache");
    divTachesAFaire.innerHTML = ''; // Vide le contenu de la div des taches a faire
    divTachesRealisees.innerHTML = ''; // Vide le contenu de la div des taches réalisées

    for (let i = 0; i < tasks["tasks"].length; i++) {
        let taskData = tasks["tasks"][i];
        let task = new Task(taskData.title, taskData.description, taskData.done, taskData.uri);
        const taskElement = task.createTaskElement();

        if (task.done) {
            divTachesRealisees.appendChild(taskElement);
        }
        else {
            divTachesAFaire.appendChild(taskElement);
        }
    }

    // Ajout d'un gestionnaire d'événements au bouton "Ajouter"
    const addButtonTache = document.getElementById("addButtonTache");
    addButtonTache.addEventListener('click', () => toggleAddForm(divAddTache));
}

// Fonction pour afficher ou masquer le formulaire d'ajout de tâche
function toggleAddForm(divAddTache) {
    const form = divAddTache.querySelector('form');
    if (form) {
        // Si le formulaire est déjà affiché, le cacher
        form.style.display = form.style.display === "none" ? "block" : "none";
    } else {
        // Sinon créer et afficher le formulaire dans la div "add-tache"
        const newForm = createAddForm(divAddTache);
        divAddTache.appendChild(newForm);
    }
}

function majAffichageTaches() {
    const requete = "http://localhost:5000/todo/api/v1.0/tasks";
    fetch(requete)
        .then(response => {
            if (response.ok) return response.json();
            else throw new Error("Problème ajax: " + response.status);
        })
        .then(afficherTaches)
        .catch(error => {
            console.error("Une erreur est survenue lors de la récupération des tâches: ", error);
        });
}

// Création du formulaire d'ajout de tâche
function createAddForm(divAddTache) {
    const form = document.createElement("form");

    // Titre
    const titleLabel = document.createElement("label");
    titleLabel.textContent = "Titre:";
    const titleInput = document.createElement("input");
    titleInput.setAttribute("type", "text");
    titleInput.setAttribute("name", "title");
    titleLabel.appendChild(titleInput);

    // Description
    const descriptionLabel = document.createElement("label");
    descriptionLabel.textContent = "Description:";
    const descriptionTextarea = document.createElement("textarea");
    descriptionTextarea.setAttribute("name", "description");
    descriptionLabel.appendChild(descriptionTextarea);

    // Bouton d'ajout
    const submitButton = document.createElement("input");
    const imgSauvegarder = document.createElement("img");
    imgSauvegarder.src = "./img/sauvegarder.png";
    submitButton.appendChild(imgSauvegarder);
    submitButton.setAttribute("type", "submit");
    submitButton.setAttribute("value", "Ajouter");

    // Ajout des éléments au formulaire
    form.appendChild(titleLabel);
    form.appendChild(descriptionLabel);
    form.appendChild(submitButton);

    // Gestionnaire d'événements pour soumettre le formulaire
    form.addEventListener("submit", (event) => {
        event.preventDefault();
        const formData = new FormData(form);
        const title = formData.get("title");
        const description = formData.get("description");
        // Envoi des données au serveur pour créer une nouvelle tâche
        createNewTask(title, description);
        // Suppression du formulaire après soumission
        form.remove();
    });

    console.log("Parent element:", divAddTache); // Ajout pour déboguer

    return form;
}

// Fonction pour créer une nouvelle tâche sur le serveur
function createNewTask(title, description) {
    const uri = "http://localhost:5000/todo/api/v1.0/tasks";
    fetch(uri, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            title: title,
            description: description
        })
    })
    .then(response => {
        if (response.ok) {
            console.log("Nouvelle tâche ajoutée avec succès.");
            // Mettre à jour l'affichage après avoir ajouté la nouvelle tâche
            majAffichageTaches();
        }
        else {
            throw new Error("Problème ajax: " + response.status);
        }
    })
    .catch(error => {
        console.error("Une erreur est survenue lors de l'ajout de la tâche: ", error);
    });
}

majAffichageTaches();