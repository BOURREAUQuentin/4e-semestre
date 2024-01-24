<?php
declare(strict_types=1);

namespace Quizz\Form;
use Quizz\Form\InputRenderInterface;

abstract class GenericFormElement implements InputRenderInterface
{
    protected readonly string $uuid; // Readonly car uuid est immutable

    protected string $type;
    protected string $label;

    protected string $correctAnswer;

    public function __construct(string $uuid, string $type, string $label, string $correctAnswer)
    {
        $this->uuid = $uuid;
        $this->type = $type;
        $this->label = $label;
        $this->correctAnswer = $correctAnswer;
    }

    public function __toString(): string
    {
        return $this->render();
    }

    function getUuid(): string 
    {
        return $this->uuid;
    }

    function getType(): string{
        return $this->type;
    }

    function getLabel(): string{
        return $this->label;
    }

    function getCorrectAnswer(): string{
        return $this->correctAnswer;
    }
}
