// function showMessage(from, text = "no text given") {
//     alert( from + ": " + text );
// }

// showMessage("Ann"); // Ann: no text given
// showMessage("Ann", "Test");

// let userName = 'John';
// function showMessage() {
//     userName = "Bob"; // (1) changé la variable externe
//     let message = 'Hello, ' + userName;
//     alert(message);
// }
// alert( userName ); // John avant l'appel de fonction
// showMessage();
// alert( userName ); // Bob, la valeur a été modifiée par la fonction

// function pow(x, n) {
//     return x ** n;
// }

// alert(pow(3, 2))
// alert(pow(3, 3))
// alert(pow(1, 100))

// let age = prompt("What is your age?", 18);

// let welcome = (age < 18) ?
//   () => alert("Hello!") :
//   () => alert("Greetings!");

// welcome(); // ok now
