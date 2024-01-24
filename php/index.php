<?php
$chemin_fichier = "./product.json";
if (!file_exists($chemin_fichier)){
    echo "Le fichier de chemin $chemin_fichier n'existe pas\n";
}
$fichier = file_get_contents($chemin_fichier);
$donnees = json_decode($fichier, true);
if (count($donnees) == 0){
    echo "Aucune donnée trouvée dans le fichier\n";
}

if (!empty($_REQUEST['filter-brand'])){
    $products = array_filter(
        $donnees,
        function ($v){
            return $v['brand'] === $_REQUEST('filter-brand');
        }
    );
}

foreach ($donnees as $product) {
    if ($product["brand"] == "Apple"){
        echo "<h1> ". $product["title"]. "</h1>";
        echo "<p>" . $product["brand"] . " au prix de " . $product["price"] . "euros</p>";
        echo "<br></br>";
    }
}