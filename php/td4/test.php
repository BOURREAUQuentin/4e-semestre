<?php

class Input{
    protected bool $required;

    protected string $type;
    protected string $defaultValue;
    protected readonly string $name;
    protected int $id;

    public function __construct(bool $required, string $type, string $defaultValue, string $name, int $id)
    {
        $this->required = $required;
        $this->type = $type;
        $this->defaultValue = $defaultValue;
        $this->name = $name;
        $this->id = $id;
    }

    public function isRequired(): bool
    {
        return $this->required;
    }

    public function getType(): string
    {
        return $this->type;
    }

    public function getDefaultValue(): string
    {
        return $this->defaultValue;
    }

    public function getName(): string
    {
        return $this->name;
    }

    public function getId(): int
    {
        return $this->id;
    }

    public function render(): string
    {
        return sprintf('<input type="%s" name="%s" value="%s" id="%s" />', $this->type, $this->name, $this->defaultValue, $this->id);
    }
}

final class TextInput extends Input{
    protected string $type = "text";
}

final class HiddenInput extends Input{
    protected string $type = "hidden";
}

final class NumberInput extends Input{
    protected string $type = "number";
}

final class CheckBoxInput extends Input{
    protected string $type = "checkbox";
}

$text = new TextInput(false,"5", "textinput", 1);
var_dump($text->render());
$number = new NumberInput(true, "6", "numberinput", 3);
var_dump($number->render());