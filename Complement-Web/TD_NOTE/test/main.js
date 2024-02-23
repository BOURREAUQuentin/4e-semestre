// Pas besoin d'accolades, juste le nom de la classe
import Comment from './comment.js';
import Post from './post.js';

function createCommentForm(postId) {
    const commentForm = document.createElement("form");
    commentForm.id = "comment-form-" + postId;
    commentForm.required = true;
    commentForm.style.display = "none";

    // Champ de texte pour le commentaire
    const commentInput = document.createElement("input");
    commentInput.type = "text";
    commentInput.placeholder = "Entrez votre commentaire...";
    commentInput.required = true;
    commentForm.appendChild(commentInput);

    // Bouton pour créer le commentaire
    const createCommentButton = document.createElement("button");
    const imgSauvegarder = document.createElement("img");
    imgSauvegarder.src = "./img/sauvegarder.png";
    createCommentButton.appendChild(imgSauvegarder);
    createCommentButton.textContent = "Créer";
    createCommentButton.addEventListener("click", () => {
        createComment(postId, commentInput.value);
    });
    commentForm.appendChild(createCommentButton);

    return commentForm;
}

function createEditCommentForm(commentId, currentText) {
    const editCommentForm = document.createElement("form");
    editCommentForm.id = "edit-comment-form-" + commentId;
    editCommentForm.style.display = "none";

    // Champ de texte pour la modification du commentaire
    const commentInput = document.createElement("input");
    commentInput.type = "text";
    commentInput.value = currentText;
    commentInput.required = true;
    editCommentForm.appendChild(commentInput);

    // Bouton pour valider la modification du commentaire
    const saveButton = document.createElement("button");
    const imgSauvegarder = document.createElement("img");
    imgSauvegarder.src = "./img/sauvegarder.png";
    saveButton.appendChild(imgSauvegarder);
    saveButton.textContent = "Enregistrer";
    saveButton.addEventListener("click", () => {
        updateComment(commentId, commentInput.value);
    });
    editCommentForm.appendChild(saveButton);

    return editCommentForm;
}

function createCommentElement(comment) {
    const liComment = document.createElement("li");
    liComment.textContent = comment.toString();

    // Création du conteneur pour les boutons et la checkbox
    const buttonsContainer = document.createElement("div");
    buttonsContainer.className = "buttons-container";

    // Bouton Modifier
    const boutonModifier = document.createElement("button");
    const imgModifier = document.createElement("img");
    imgModifier.src = "./img/modifier.png";
    boutonModifier.appendChild(imgModifier);
    boutonModifier.textContent = "Modifier";
    boutonModifier.addEventListener("click", () => {
        toggleEditCommentForm(comment.getId());
    });
    buttonsContainer.appendChild(boutonModifier);

    // Bouton Supprimer
    const boutonSupprimer = document.createElement("button");
    const imgSupprimer = document.createElement("img");
    imgSupprimer.src = "./img/supprimer.png";
    boutonSupprimer.appendChild(imgSupprimer);
    boutonSupprimer.textContent = "Supprimer";
    boutonSupprimer.addEventListener("click", () => {
        deleteComment(comment.getId());
        // Supprimer le commentaire de l'interface utilisateur
        liComment.remove();
    });
    buttonsContainer.appendChild(boutonSupprimer);
    liComment.appendChild(buttonsContainer);

    return liComment;
}

function toggleEditCommentForm(commentId) {
    const editCommentForm = document.getElementById("edit-comment-form-" + commentId);
    if (editCommentForm.style.display === "none") {
        editCommentForm.style.display = "block";
    } else {
        editCommentForm.style.display = "none";
    }
}

function createComment(postId, text) {
    fetch('http://localhost:3000/comments', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            text: text,
            postId: postId
        })
    })
    .then(response => {
        if (response.ok) {
            console.log(response.json());
            getPosts();
        }
        else {
            throw new Error('Erreur lors de la création du commentaire');
        }
    })
    .catch(error => {
        console.error('Erreur:', error);
    });
}

// Fonction pour créer un nouveau post
function createPost(title, views) {
    fetch('http://localhost:3000/posts', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            title: title,
            views: views
        })
    })
    .then(response => {
        // Vérification de la réponse de la requête
        if (response.ok) {
            // Si la réponse est ok, rechargement des posts pour afficher le nouveau post
            getPosts();
        }
        else {
            // Sinon, affichage d'une erreur
            throw new Error('Erreur lors de la création du post');
        }
    })
    .catch(error => {
        // Gestion des erreurs
        console.error('Erreur:', error);
    });
}

function updateComment(commentId, newText) {
    fetch('http://localhost:3000/comments/' + commentId, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            text: newText
        })
    })
    .then(response => {
        if (response.ok) {
            console.log(response.json());
            getPosts();
        }
        else {
            throw new Error('Erreur lors de la modification du commentaire');
        }
    })
    .catch(error => {
        console.error('Erreur:', error);
    });
}

function deleteComment(commentId) {
    fetch('http://localhost:3000/comments/' + commentId, {
        method: 'DELETE',
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erreur lors de la suppression du commentaire');
        }
    })
    .catch(error => {
        console.error('Erreur:', error);
    });
}

function createPostElement(post) {
    const ulPost = document.createElement("ul");
    const liPost = document.createElement("li");
    liPost.textContent = post.toString();

    // Créez une sous-liste pour les commentaires de ce post
    const ulComments = document.createElement("ul");

    // Ajoutez chaque commentaire correspondant à ce post
    getComments(post.getId(), ulComments);

    ulPost.appendChild(liPost);
    ulPost.appendChild(ulComments);

    // Ajoute un bouton "Ajouter un commentaire" à la fin des commentaires
    const addCommentButton = document.createElement("button");
    addCommentButton.textContent = "Ajouter un commentaire";
    addCommentButton.addEventListener("click", () => {
        toggleCommentForm(post.getId());
    });
    ulPost.appendChild(addCommentButton);

    // Ajoute un formulaire de création de commentaire
    ulPost.appendChild(createCommentForm(post.getId()));

    // Ajout d'une bordure inférieure
    ulPost.style.paddingBottom = "20px";
    ulPost.style.borderBottom = "1px solid black";

    return ulPost;
}

function toggleCommentForm(postId) {
    const commentForm = document.getElementById("comment-form-" + postId);
    if (commentForm.style.display === "none") {
        commentForm.style.display = "block";
    } else {
        commentForm.style.display = "none";
    }
}

// Fonction pour créer un nouveau post
function createPostForm() {
    const postForm = document.createElement("form");
    postForm.id = "post-form";
    postForm.style.display = "none";
    
    // Champ de texte pour le titre du post
    const titleInput = document.createElement("input");
    titleInput.type = "text";
    titleInput.placeholder = "Entrez le titre du post...";
    titleInput.required = true;
    postForm.appendChild(titleInput);

    // Champ de texte pour le nombre de vues du post
    const viewsInput = document.createElement("input");
    viewsInput.type = "number";
    viewsInput.placeholder = "Entrez le nombre de vues...";
    viewsInput.required = true;
    postForm.appendChild(viewsInput);

    // Bouton pour créer le post
    const createPostButton = document.createElement("button");
    const imgSauvegarder = document.createElement("img");
    imgSauvegarder.src = "./img/sauvegarder.png";
    createPostButton.appendChild(imgSauvegarder);
    createPostButton.textContent = "Créer";
    createPostButton.addEventListener("click", () => {
        const title = titleInput.value;
        const views = parseInt(viewsInput.value);
        createPost(title, views);
    });
    postForm.appendChild(createPostButton);

    return postForm;
}

function getComments(postId, ulComments) {
    fetch('http://localhost:3000/comments')
        .then(response => response.json())
        .then(json => {
            json.forEach(comment => {
                var commentActuel = new Comment(comment.id, comment.text, comment.postId);
                if (commentActuel.getPostId() === postId) {
                    const commentElement = createCommentElement(commentActuel);
                    ulComments.appendChild(commentElement);
                    // Ajoute un formulaire de modification de commentaire pour chaque commentaire
                    ulComments.appendChild(createEditCommentForm(commentActuel.getId(), commentActuel.getText()));
                }
            });
        });
}

function getPosts() {
    fetch('http://localhost:3000/posts')
        .then(response => response.json())
        .then(json => {
            const divPosts = document.getElementById("posts");
            divPosts.innerHTML = "";

            json.forEach(post => {
                var postActuel = new Post(post.id, post.title, post.views);
                const postElement = createPostElement(postActuel);
                divPosts.appendChild(postElement);
            });

            // ajout de la création du formulaire d'ajout d'un post
            const divContenu = document.querySelector(".div-posts");
            divContenu.appendChild(createPostForm());
        });
}

// Appel de la fonction getPosts au chargement de la page
getPosts();

// Sélection du bouton "Ajouter un post"
const addButtonPost = document.getElementById("addButtonPost");

// Ajout d'un écouteur d'événements sur le bouton "Ajouter un post"
addButtonPost.addEventListener("click", () => {
    const postForm = document.getElementById("post-form");
    if (postForm.style.display === "none") {
        postForm.style.display = "block";
    }
    else {
        postForm.style.display = "none";
    }
});