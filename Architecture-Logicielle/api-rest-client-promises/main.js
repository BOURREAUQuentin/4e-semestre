class Task {
    constructor(title, description, done, uri) {
        this.title = title;
        this.description = description;
        this.done = done;
        this.uri = uri;
        console.log(this.uri);
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

        const boutonVoir = this.createButton("Voir", () => this.voirTache(pDescription));
        const boutonModifier = this.createButton("Modifier", this.modifierTache.bind(this));
        const boutonSupprimer = this.createButton("Supprimer", this.supprimerTache.bind(this));
        const checkboxRealisee = this.createCheckbox("Réalisée", this.toggleDone.bind(this));

        divTache.appendChild(boutonVoir);
        divTache.appendChild(boutonModifier);
        divTache.appendChild(boutonSupprimer);
        divTache.appendChild(checkboxRealisee);

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
        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.checked = this.done;
        checkbox.addEventListener("change", onChangeHandler);
        return checkbox;
    }

    // Méthode pour voir la tache
    voirTache(pDescription) {
        fetch(this.uri)
            .then(response => {
                if (response.ok) return response.json();
                else throw new Error("Problème ajax: " + response.status);
            })
            .then(data => {
                console.log("Données de la tâche:", data);
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

    // Méthode pour modifier la tâche
    modifierTache() {
        console.log("Modifier la tâche:", this);
    }

    // Méthode pour supprimer la tâche
    supprimerTache() {
        console.log("Supprimer la tâche:", this);
    }

    // Méthode pour basculer l'état de la tâche (réalisée ou non)
    toggleDone(event) {
        console.log("Checkbox changée pour la tâche:", this);
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
                console.log("État de la tâche mis à jour avec succès.");
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

majAffichageTaches();