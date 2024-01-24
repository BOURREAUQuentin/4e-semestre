<?php


class MyClass{
    public $value;
    private $max = 20;
    private const MIN = 0;

    public function __construct($value){
        $this->value = $value;
    }

    public function getValue(){
        return $this->value;
    }

    public function isInteger(){
        return is_int($this->value);
    }

    public function square(){
        if ($this->isInteger()){
            if ($this->valeur_bonne()){
                return "Le carré de ".$this->value." est ".$this->value * $this->value;
            }
            else{
                return "Le carré n'est pas possible car ".$this->value." n'est pas compris entre ".self::MIN." et ".$this->max;
            }
        }
        else{
            return "Le carré n'est pas possible car ".$this->value." n'est pas un nombre";
        }
    }

    public function valeur_bonne(){
        if ($this->value < $this->max && $this->value > self::MIN){
            return true;
        }
        return false;
    }
}

$myClass = new MyClass(15);
echo $myClass->square().PHP_EOL;

$myClass = new MyClass("iut");
echo $myClass->square().PHP_EOL;

$myClass = new MyClass(21);
echo $myClass->square().PHP_EOL;

$myClass = new MyClass(-15);
echo $myClass->square().PHP_EOL;

$dt = new DateTime();
echo $dt->format("Y-m-d").PHP_EOL;
$dt->add(new DateInterval("P10D"));
echo $dt->format("Y-m-d").PHP_EOL;
