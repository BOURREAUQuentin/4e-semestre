<script>

export default{
    props: {
        todo: Object
    },
    data() {
        return {
            editModalVisible: false,
            newTitle: this.todo.title,
            newDescription: this.todo.description
        };
    },
    methods: {
        suppr: function(){
            this.$emit("remove", {id:this.todo.id});
        },
        showEditModal: function(){
            this.editModalVisible = ! this.editModalVisible;
        },
        editTodo: function(newTitle, newDescription){
            this.todo.title = newTitle;
            this.todo.description = newDescription;
            this.$emit("edit", this.todo);
            this.editModalVisible = false;
        },
        toggleDone: function(){
            this.$emit("edit", this.todo);
        }
    },
    emits : ["remove", "add", "edit"]
}
</script>
<template>
    <div>
        <label>
            <h1>{{ todo.title}}</h1>
            <p>{{ todo.description }}</p>
            <input type="checkbox" checked="checked" v-model="todo.checked" @change="toggleDone">
        </label>

        <input type="button"
        class="btn btn-danger"
        value="Supprimer"
        @click="suppr">

        <input type="button"
        class="btn"
        value="Modifier"
        @click="showEditModal">

        <div class="alert alert-success" v-if="todo.checked">
            Done
        </div>

        <div v-if="editModalVisible">
            <input type="button" class="btn" value="Annuler" @click="showEditModal">
            <form @submit.prevent="editTodo(newTitle, newDescription)">
                <label>New title</label>
                <input type="text" v-model="newTitle">
                <label>New description</label>
                <input type="text" v-model="newDescription">
                <input type="submit" value="Enregistrer">
            </form>
        </div>
    </div>
</template>