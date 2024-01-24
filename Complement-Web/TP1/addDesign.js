let buttonRemettre = document.getElementById('actionRemettre');
    buttonRemettre.addEventListener('click', function(e){
    let paragraphe = document.querySelector('p');
    paragraphe.classList.add('design');
});
const car = "Dodge Charger";
const anotherObject = { str: "Some text", id: 5 };
console.info("My first car was a", car, ". The object is:", anotherObject);