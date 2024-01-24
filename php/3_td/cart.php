<?php
require_once "_inc/data/products.php";
include "_inc/template/filter.php";

session_start();
if (!isset($_SESSION["cart"])){
    $_SESSION["cart"] = [];
}

$products = getProducts();

if ($_SERVER["REQUEST_METHOD"] === "POST" && isset($_POST["cart"])){
    $product_id = $_POST['product_id'];
    $quantity = $_POST["cart"]['quantity'];
    if (isset($_SESSION["cart"][$product_id])) {
        $_SESSION["cart"][$product_id] = $quantity;
    }
    else {
        $_SESSION["cart"][$product_id] += $quantity;
    }
}
echo "<table border='1'>";
echo "<tr><th>ID Produit</th>";
echo "<th>Quantité</th>";
echo "<th>Prix</th></tr>";
foreach ($_SESSION["cart"] as $product_id => $quantity) {
    echo "<tr>";
    echo "<td>".$product_id."</td>";
    echo "<td>".$quantity."</td>";
    echo "</tr>";
}
echo "</table>";