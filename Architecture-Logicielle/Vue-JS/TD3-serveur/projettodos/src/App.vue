<script>
import axios from 'axios';
import TodoItem from './components/TodoItem.vue'

let data = {
  todos:[],
  addModalVisible: false,
  newTitle: "",
  newDescription: ""
};

export default {
  data(){
    return data;
  },
  mounted() {
    this.loadTasks();
  },
  components: { TodoItem },
  methods: {
    async loadTasks() {
        try {
            const response = await axios.get('http://localhost:5000/todo/api/v1.0/tasks');
            this.todos = this.mapTasks(response.data.tasks);
        } catch (error) {
            console.error('Error loading tasks:', error);
        }
    },
    mapTasks(tasks) {
        return tasks.map(task => ({
            title: task.title,
            description: task.description,
            checked: task.done,
            uri: task.uri
        }));
    },
    editTodo(todo) {
        console.log(todo.uri);
        axios.put(todo.uri, {
            title: todo.text,
            description: todo.description,
            done: todo.checked
        })
        .then(response => {
            console.log(response);
        })
        .catch(error => {
            console.error('Error updating task:', error);
        });
    },
    removeItem(todo) {
      console.log(todo);
      axios.delete(todo.uri)
      .then(response => {
        this.todos = this.todos.filter(item => item.uri !== todo.uri);
        console.log('Task deleted:', todo.uri);
      })
      .catch(error => {
        console.error('Error deleting task:', error);
      });
    },
    showAddTodo(){
      this.addModalVisible = !this.addModalVisible;
    },
    addTodo(newTitle, newDescription) {
      const newTask = {
        title: newTitle,
        description: newDescription,
      };
      axios.post('http://localhost:5000/todo/api/v1.0/tasks', newTask)
        .then(response => {
          const addedTask = response.data.task;
          this.todos.push({
            title: addedTask.title,
            description: addedTask.description,
            checked: addedTask.done,
            uri: addedTask.uri
          });
          this.newTitle = '';
          this.newDescription = '';
          this.showAddTodo();
        })
        .catch(error => {
          console.error('Error adding task:', error);
        });
    }
  }
}
</script>

<template>
  <h2>Liste de taches</h2>
  <TodoItem v-for="tache of todos" :todo="tache" :key="tache.id" @remove="removeItem(tache)" @edit="editTodo"></TodoItem>

  <input type="button"
    class="btn"
    value="Ajouter une tâche"
    @click="showAddTodo">
  <div v-if="addModalVisible">
    <input type="button" class="btn" value="Annuler" @click="showAddTodo">
    <form @submit.prevent="addTodo(newTitle, newDescription)">
      <label>New title</label>
      <input type="text" v-model="newTitle" required>
      <label>New description</label>
      <input type="text" v-model="newDescription" required>
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
