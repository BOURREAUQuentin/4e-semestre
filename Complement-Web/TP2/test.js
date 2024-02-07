console.log(document.body.children);
console.log(document.body.firstChild);
console.log(document.body.parentNode);
console.log( document.body.parentNode === document.documentElement ); // true
console.log( document.head.nextElementSibling ); // HTMLBodyElement

// avant <body> vient <head>
console.log( document.body.previousElementSibling ); // HTMLHeadElement

enfants = document.body.children;
for (let i=0; i<enfants.length; i++){
    if (enfants[i].nodeName == "UL"){
        console.log(enfants[i].lastElementChild.outerText);
    }
    if (enfants[i].nodeName == "P"){
        console.log(enfants[i]);
    }
}

console.log(document.querySelector("li:last-child").outerText);
console.log(document.querySelector("p").outerText);

document.body.sayTagName = function() {
    alert(this.tagName);
  };
  document.body.sayTagName();