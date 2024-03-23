<script>
import TodoItem from './components/TodoItem.vue'

let data = {
  todos:[
    { id: 1, text: 'Apprendre ES6', checked: true},
    { id: 2, text: 'Apprendre Vue', checked: false},
    { id: 3, text: 'Faire un projet magnifique', checked: false},
  ],
  addModalVisible: false
};

export default {
  data(){
    return data;
  },
  components: { TodoItem },
  methods: {
    removeItem(todo) {
      this.todos = this.todos.filter(t => t.id !== todo.id);
    },
    showAddTodo(){
      this.addModalVisible = !this.addModalVisible;
      this.newText = '';
    },
    addTodo(newText) {
      let maxId = 0;
      for (const todo of this.todos) {
        if (todo.id > maxId) {
          maxId = todo.id;
        }
      }
      const newTodo = { id: maxId + 1, text: newText, checked: false };
      this.todos.push(newTodo);
      this.showAddTodo();
      console.log(this.todos);
    }
  }
}
</script>

<template>
  <h2>Liste de taches</h2>
  <TodoItem v-for="tache of todos" :todo="tache" :key="tache.id" @remove="removeItem"></TodoItem>

  <input type="button"
    class="btn"
    value="Ajouter une tâche"
    @click="showAddTodo">
  <div v-if="addModalVisible">
    <input type="button" class="btn" value="Annuler" @click="showAddTodo">
    <form @submit.prevent="addTodo(newText)">
      <input type="text" v-model="newText" required>
      <input type="submit" value="Enregistrer">
    </form>
  </div>
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
