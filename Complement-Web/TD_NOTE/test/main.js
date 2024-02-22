// Pas besoin d'accolades, juste le nom de la classe
import Comment from './comment.js';
import Post from './post.js';

function createCommentForm(postId) {
    const commentForm = document.createElement("form");
    commentForm.id = "comment-form-" + postId;
    commentForm.style.display = "none";

    // Champ de texte pour le commentaire
    const commentInput = document.createElement("input");
    commentInput.type = "text";
    commentInput.placeholder = "Entrez votre commentaire...";
    commentForm.appendChild(commentInput);

    // Bouton pour créer le commentaire
    const createCommentButton = document.createElement("button");
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
    editCommentForm.appendChild(commentInput);

    // Bouton pour valider la modification du commentaire
    const saveButton = document.createElement("button");
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

    // Bouton Modifier
    const editButton = document.createElement("button");
    editButton.textContent = "Modifier";
    editButton.addEventListener("click", () => {
        toggleEditCommentForm(comment.getId());
    });
    liComment.appendChild(editButton);

    // Bouton Supprimer
    const deleteButton = document.createElement("button");
    deleteButton.textContent = "Supprimer";
    deleteButton.addEventListener("click", () => {
        deleteComment(comment.getId());
        // Supprimer le commentaire de l'interface utilisateur
        liComment.remove();
    });
    liComment.appendChild(deleteButton);

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
            id: 5, // changer par un id automatique
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
        });
}

getPosts();
