<script>

export default{
    props: {
        todo: Object
    },
    data() {
        return {
            editModalVisible: false,
            addModalVisible: false,
            newText: ''
        };
    },
    methods: {
        suppr: function(){
            this.$emit("remove", {id:this.todo.id});
        },
        showEditModal: function(){
            this.editModalVisible = ! this.editModalVisible;
        },
        editTodo: function(newText){
            this.todo.text = newText;
            this.editModalVisible = false;
            this.newText = "";
            // est-ce que faut faire un emit pour modifier au parent (serveur) ???
            //this.$emit("edit", {id:this.todo.id, newText: newText});
        },
        showAddModal: function(){
            this.addModalVisible = !this.addModalVisible;
        },
        addTodo: function(newText){
            this.$emit("add", {text: newText});
        }
    },
    emits : ["remove", "add"]
}
</script>
<template>
    <div class="checkbox">
        <label>
            <input type="checkbox" checked="checked" v-model="todo.checked">
            {{ todo.text}}
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
            <form @submit.prevent="editTodo(newText)">
                <input type="text" v-model="newText" required>
                <input type="submit" value="Enregistrer">
            </form>
        </div>
    </div>
</template>