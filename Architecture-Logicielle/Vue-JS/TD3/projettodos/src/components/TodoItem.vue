<script>

export default{
    props: {
        todo: Object
    },
    methods: {
        suppr: function(){
            this.$emit("remove", {id:this.todo.id});
        }
    },
    emits : ["remove"]
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
    </div>
</template>