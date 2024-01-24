<?php
// Connection en utlisant la connexion PDO avec le moteur en prefix
$pdo = new PDO('sqlite:db.sqlite');
// Permet de gérer le niveau des erreurs
$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

$stmt = $pdo->query($sql); // retour un état que l'on peut éxécuter
$rows = $stmt->fetchAll();

