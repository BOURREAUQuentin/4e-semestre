<?php
for ($i=0; $i<10; $i++) {
    if ($i % 2 == 0){
        echo "The number $i est pair\n";
    }
    else{
        echo "The number $i est impair \n";
    }
}


$colors = array("Red", "Green", "Blue");

foreach ($colors as $value) {
  echo "$value \n";
}


// Example of an indexed array
$fruits = array("apple", "banana", "cherry");

// Example of an associative array
$fruits = array("apple" => "red", "banana" => "yellow", "cherry" => "red");

// Creating an array using square brackets
$fruits = ["apple", "banana", "cherry"];

$langages = array("php", "html", "css", "python");
$langagesServeurs = array("php", "python");
foreach ($langages as $lang){
    if (in_array($lang, $langagesServeurs)){
        echo "langage serveur : " . $lang . "\n";
    }
}

function getCarreNombre(int $entier) : int{
    return $entier**2;
}

echo getCarreNombre(5) . "\n";