// //let arr = new Array();
// //let arr = [];

// let shopping = ["pain", "lait", "fromage", "houmous", "nouilles"];
// console.log(shopping);

// // Tableau imbriqué
// let shoppingI = ["pain", "lait", ["tome de savoie", "brie", "camenbert"], "houmous", "nouilles"];

// // mélange de valeurs
// let arr = [ 'Apple', { name: 'John' }, true, function() { alert('hello'); } ];

// // récupère l'objet à l'index 1 et montre ensuite son nom
// alert( arr[1].name ); // John

// // affiche la fonction à l'index 3 et l'exécute la
// arr[3](); // hello

// console.log(shopping[0]);
// // renvoie "pain"

// // Modification
// shopping[0] = "crème de sésame";
// console.log(shopping);
// // shopping renvoie maintenant [ "crème de sésame", "lait", "fromage", "houmous", "nouilles" ]

// // Pop
// let fruits = ["Apple", "Orange", "Pear"];
// console.log( fruits.pop() ); // supprime "Pear" et l'alerte
// console.log( fruits ); // Apple, Orange

// // push
// let fruits2 = ["Apple", "Orange"];
// fruits2.push("Pear");
// console.log( fruits2 ); // Apple, Orange, Pear

// // shift
// let fruits3 = ["Apple", "Orange", "Pear"];
// console.log( fruits3.shift() ); // supprime "Apple" et l'alerte
// console.log( fruits3 ); // Orange, Pear


// boucles :
// in ou i -> index
// of -> valeur

// let arr = ["Apple", "Orange", "Pear"];
// for (let i = 0; i < arr.length; i++) {
//   console.log( arr[i] );
// }

// let fruits4 = ["Apple", "Orange", "Plum"];
// // itère sur des éléments de tableau
// for (let fruit of fruits4) {
//   console.log( fruit + " c'est bon" );
// }

// let fruits5 = ["Apple", "Orange", "Plum"];
// // itère sur des index de tableau
// for (let fruit in fruits5) {
//   console.log( fruit + " c'est bon" );
// }

// let langages = ["Javascript", "CSS", "Python", "C#"];
// langages.push("PHP");
// console.log(langages);

// langages[(langages.length / 2)-0.5] = "SCSS";
// console.log(langages);

// console.log(langages[0]);


let tableau = [];
function calculSumInput(){
    while (true){
        let input = +prompt("Please enter a number", "");
        if (Number.isInteger(input)){
            tableau.push(input);
        }
        else{
            let somme = 0;
            for (let entier of tableau){
                somme += entier
            }
            return somme;
        }
    }
}

let somme = calculSumInput();
alert(somme);