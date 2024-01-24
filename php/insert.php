<?php
require_once("./_connection.php");

$sql = <<<EOF
insert into QUIZZ values
(1, "Geographie"),
(2, "Python"),
(3, "Java");
EOF;

try{
    $pdo->exec($sql);
}
catch (PDOException $e){
    var_dump($e->getMessage());
}