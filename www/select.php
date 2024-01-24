<?php
require_once("./_connection.php");

echo "SELECT ALL QUIZZ".PHP_EOL;

$sql = <<<EOF
select * from QUIZZ;
EOF;

try{
    $stmt = $pdo->query($sql); // retour un état que l'on peut éxécuter
    $rows = $stmt->fetchAll();
    var_dump($rows);
}
catch (PDOException $e){
    var_dump($e->getMessage());
}