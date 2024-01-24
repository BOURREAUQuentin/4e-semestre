<?php
require_once '_inc/data/products.php';
$products = getProducts();

include '_inc/template/filter.php';

// Instantiate product
$product = [];

// Get product by id
if (!empty($_REQUEST['id'])) {
    // foreach or array_filter
    $products = array_filter(
        $products, 
        fn($p) => $p['id'] == $_REQUEST['id']
    );

    if (count($products) !== 1) {
        throw new Exception(sprintf('None or not only one product for id "%d".', $_REQUEST['id']), 1);
    }

    $product = array_shift($products); // depiler le premier élément du tableau filtré
}

if (empty($product)) {
    throw new Exception(sprintf('Poduct with id "%d" not found.', $_REQUEST['id']), 1);
}

// Template
echo '<h1>'.$product['title'].'</h1>';
echo '<p>'.number_format($product['price'], 2, ',').' €</p>';
echo '<p>'.$product['brand'].'</p>';
echo '<p>'.$product['description'].'</p>';
echo '<a href="/detail.php?id='.$product['id'].'">ajout panier</a>';

echo '<form action="cart.php" method="post">';
echo 'Qte <input type="number" name="cart[quantity]" value="1" />';
echo '<input type="hidden" name="cart[product_id]" value="'.$product['id'].'" />';
echo '<button>Ajout</button>';
echo '</form>';

echo '<ul>';
foreach ($product['images'] as $src) {
    echo '<li><img src="'.$src.'" /></li>';
}
echo '</ul>';


echo '<h2>Toutes les informations</h2>';
$informations = [];
foreach ($product as $key => $value) {
    if (is_array($value)) {
        continue;
    }
    $informations[] = sprintf('<strong>%s</strong> : %s', $key, $value);
}
echo implode('<br />', $informations);

echo "<form action='/detail.php?id={$product['id']}' method='post'>";
echo '<input type="hidden" name="id" value="'.$product['id'].'" />';

echo '<ul>';
foreach ($product['images'] as $src) {
    echo '<li><img src="'.$src.'" /></li>';
}
echo '</ul>';