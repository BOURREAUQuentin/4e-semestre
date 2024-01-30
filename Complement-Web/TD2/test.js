// function User(username, access, isAdmin) {
//     this.username = username;
//     this.access = access;
//     this.isAdmin = isAdmin;
//     this.profile = function() {
//         return "Username is "+ this.username +" and acces are " + this.access;
//     }
//     this.hasAccessOf = function(valeur) {
//         return this.access.includes(valeur);
//     }
// }

// let user1 = new User('quentin', ["user", "edito"], true);
// console.log("Admin : " + user1.isAdmin);
// console.log(user1.profile());
// console.log(user1.hasAccessOf("edito"));

// function say(message) {
//     return `je veux dire ${message}`;
//   }
//   console.log(typeof say); // function
//   console.log(Object.getPrototypeOf(say));
  
//   let tab = ['one', 'two', 'three'];
//   console.log(typeof tab); // Array []
//   console.log(Object.getPrototypeOf(tab));
  
//   let message = 'un string';
//   console.log(typeof message); // string
//   console.log(Object.getPrototypeOf(message));
  
//   let user = {
//     firstname: 'geoffroy',
//     lastname: 'cochard',
//   }
//   console.log(typeof user); // object
//   console.log(Object.getPrototypeOf(user));

// function Person() {
//     this.whoIs = function () {
//     return `${this.firstname} ${this.lastname}`;
//     }
// }

// function Client(firstname, lastname) {
//     this.__proto__ = new Person();
//     this.firstname = firstname;
//     this.lastname = lastname;
// }

// console.log(new Client('moi', 'moi'));

// class User{
//     username = "";
//     access = [];
//     isAdmin = false;

//     constructor(username, access, isAdmin) {
//         this.username = username;
//         this.access = access;
//         this.isAdmin = isAdmin;
//     }

//     getProfile() {
//         return "Username is "+ this.username +" and acces are " + this.access;
//     }

//     contientAccess(valeur) {
//         return this.access.includes(valeur);
//     }
// }

// let userClass = new User("quentin", ["write", "read"], false);
// console.log(userClass.contientAccess("write"));

class Clock{
    timer = 0;
    template = "";

    constructor(template) {
        this.timer = 0;
        this.template = template;
    }

    render(){
        let date = new Date();
        let hours = date.getHours();
        if (hours < 10) hours = '0' + hours;
        let mins = date.getMinutes();
        if (mins < 10) mins = '0' + mins;
        let secs = date.getSeconds();
        if (secs < 10) secs = '0' + secs;
        let output = this.template
            .replace('h', hours)
            .replace('m', mins)
            .replace('s', secs);
        console.log(output);
    }

    stop() {
        clearInterval(timer);
    }
    
    start() {
        this.render();
        this.timer = setInterval(this.render(), 1000);
    }
}
let clock = new Clock('h:m:s');
clock.start();

class Person{
    constructor(firstname, lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    whoIs(){
        return `${this.firstname} ${this.lastname}`;
    }
}

class Employee extends Person{
    position = "";

    constructor(firstname, lastname, position){
        super(firstname, lastname);
        this.position = position;
    }

    set position(position){
        this.position = position;
    }

    get position(){
        return this.position;
    }
}

let employeClass = new Employee("quentin", "le meilleur", "cadre supérieur");
console.log(employeClass.whoIs());
console.log("Avant promotion : " + employeClass.position);
employeClass.position = "chef de projet";
console.log("Après promotion : " + employeClass.position);