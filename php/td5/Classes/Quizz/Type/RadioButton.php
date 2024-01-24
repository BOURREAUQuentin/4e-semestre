<?php
declare(strict_types=1);
namespace Quizz\Type;
class RadioButton{
    private string $value;

    public function __construct($value)
    {
        $this->value = $value;
    }

    public function getValue(): string{
        return $this->value;
    }
}