<?php
declare(strict_types=1);
require 'Classes/autoloader.php'; 
Autoloader::register();

// Importation des domaines de nom à utiliser
use Provider\JsonLoader;

$chemin_fichier = "./Data/quizz.json";
if (!file_exists($chemin_fichier)){
    echo "Le fichier de chemin $chemin_fichier n'existe pas\n";
}
$loader = new JsonLoader($chemin_fichier);
$quizz = $loader->createQuizz();

foreach ($quizz->getQuestions() as $question){
    echo $question->render();
}