<script>
import TodoItem from './components/TodoItem.vue'

let data = {
  todos:[
    { id: 1, text: 'Apprendre ES6', checked: true},
    { id: 2, text: 'Apprendre Vue', checked: false},
    { id: 3, text: 'Faire un projet magnifique', checked: false},
  ]
};

export default {
  data(){
    return data;
  },
  components: { TodoItem },
  methods: {
    removeItem(todo) {
      this.todos = this.todos.filter(t => t.id !== todo.id);
    }
  }
}
</script>

<template>
  <h2>Liste de taches</h2>
  <TodoItem v-for="tache of todos" :todo="tache" :key="tache.id" @remove="removeItem"></TodoItem>
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
