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

function createComment(postId, text) {
    fetch('http://localhost:3000/comments', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: 5, // modifier par un id unique
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

function createCommentElement(comment) {
    const liComment = document.createElement("li");
    liComment.textContent = comment.id + " : " + comment.text;

    // Bouton Supprimer
    const deleteButton = document.createElement("button");
    deleteButton.textContent = "Supprimer";
    deleteButton.addEventListener("click", () => {
        deleteComment(comment.id);
        // Supprimer le commentaire de l'interface utilisateur
        liComment.remove();
    });
    liComment.appendChild(deleteButton);

    return liComment;
}

function deleteComment(commentId) {
    fetch(`http://localhost:3000/comments/${commentId}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (response.ok) {
            console.log("Commentaire supprimé avec succès");
        } else {
            throw new Error("Erreur lors de la suppression du commentaire");
        }
    })
    .catch(error => {
        console.error("Erreur:", error);
    });
}

function createPostElement(post) {
    const ulPost = document.createElement("ul");
    const liPost = document.createElement("li");
    liPost.textContent = post.id + " comme titre " + post.title;

    // Créez une sous-liste pour les commentaires de ce post
    const ulComments = document.createElement("ul");

    // Ajoutez chaque commentaire correspondant à ce post
    getComments(post.id, ulComments);

    ulPost.appendChild(liPost);
    ulPost.appendChild(ulComments);

    // Ajoute un bouton "Ajouter un commentaire" à la fin des commentaires
    const addCommentButton = document.createElement("button");
    addCommentButton.textContent = "Ajouter un commentaire";
    addCommentButton.addEventListener("click", () => {
        toggleCommentForm(post.id);
    });
    ulPost.appendChild(addCommentButton);

    // Ajoute un formulaire de création de commentaire
    ulPost.appendChild(createCommentForm(post.id));

    return ulPost;
}

function getComments(postId, ulComments) {
    fetch('http://localhost:3000/comments')
        .then(response => response.json())
        .then(json => {
            json.forEach(comment => {
                if (comment.postId === postId) {
                    const commentElement = createCommentElement(comment);
                    ulComments.appendChild(commentElement);
                }
            });
        });
}

function toggleCommentForm(postId) {
    const commentForm = document.getElementById("comment-form-" + postId);
    if (commentForm.style.display === "none") {
        commentForm.style.display = "block";
    } else {
        commentForm.style.display = "none";
    }
}

function getPosts() {
    fetch('http://localhost:3000/posts')
        .then(response => response.json())
        .then(json => {
            const divPosts = document.getElementById("posts");
            divPosts.innerHTML = "";

            json.forEach(post => {
                const postElement = createPostElement(post);
                divPosts.appendChild(postElement);
            });
        });
}

getPosts();
