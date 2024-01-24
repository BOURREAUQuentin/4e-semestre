<?php
require_once("./_connection.php");

echo "SELECT ALL QUIZZ".PHP_EOL;

$sql = <<<EOF
select * from QUIZZ where idQuizz = :id;
EOF;

$stmt = $pdo->prepare($sql);

$id = 1;
try{
    $stmt->bindParam("id", $id, PDO::PARAM_INT);
    var_dump($stmt->debugDumpParams());
    $stmt->execute();
    $quizz = $stmt->fetch();
}
catch (PDOException $e){
    var_dump($e->getMessage());
}

var_dump($quizz);