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
        return "Post d'id " + this.id + " intitulé "+ this.text +" avec un nombre de vues de " + this.postId;
    }
}