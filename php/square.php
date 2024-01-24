<?php

$number = intval($argv[1]);
echo $number . "\n";
function getCarreNombre(int $entier) : int{
    return $entier**2;
}

echo "Le carré de " . $number . " est " . getCarreNombre($number) . "\n";