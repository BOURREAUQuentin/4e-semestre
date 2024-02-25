export default class Comment{
    id = 0;
    text = "";
    postId = 0;

    constructor(id, text, postId) {
        this.id = id;
        this.text = text;
        this.postId = postId;
    }

    getId() {
        return this.id;
    }

    getText() {
        return this.text;
    }

    getPostId() {
        return this.postId;
    }

    toString() {
        return "Commentaire d'id " + this.id + " : "+ this.text;
    }
}