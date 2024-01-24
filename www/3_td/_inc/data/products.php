<?php

function getProducts()
{
    $source = 'data/product.json';
    $content = file_get_contents($source);
    $products = json_decode($content, true);

    if (empty($products)) {
        throw new Exception('No product :(', 1);
    }

    return $products;
}