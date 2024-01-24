<?php
require_once '_inc/data/products.php';
$products = getProducts();

include '_inc/template/filter.php';

// Go filtering when query not empty
if (!empty($_REQUEST['filter-brand'])) {
    // foreach or array_filter
    $products = array_filter(
        $products, 
        fn($p) => $p['brand'] == $_REQUEST['filter-brand']
    );
}

foreach ($products as $key => $product) {
    echo '<h1>'.$product['title'].'</h1>';
    echo '<p>'.$product['brand'].'</p>';
    echo '<a href="/detail.php?id='.$product['id'].'">+ details</a>';
    echo '<hr />';
}
