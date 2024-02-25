export default class Post{
    id = 0;
    title = "";
    views = 0;

    constructor(id, title, views) {
        this.id = id;
        this.title = title;
        this.views = views;
    }

    getId() {
        return this.id;
    }

    getTitle() {
        return this.title;
    }

    getViews() {
        return this.views;
    }

    toString() {
        return "Post d'id "+ this.id + "(" + this.views + " vues): "+ this.title;
    }
}