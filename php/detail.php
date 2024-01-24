<?php
//require_once = "";

if (empty($_REQUEST["id"])){
    throw new Exception("id is required");
}

$chemin_fichier = "./product.json";
if (!file_exists($chemin_fichier)){
    echo "Le fichier de chemin $chemin_fichier n'existe pas\n";
}
$fichier = file_get_contents($chemin_fichier);
$products = json_decode($fichier, true);
if (count($products) == 0){
    echo "Aucune donnée trouvée dans le fichier\n";
}

echo "<pre>";
$pos = var_dump(
    array_search(
        $_REQUEST["id"],
        array_column($products, "id")
    )
);
$product = $products[$pos];
echo "<h1> ". $product["title"]. "</h1>";
echo "<p>". $product["brand"]. " au prix de ". $product["price"]. "euros";