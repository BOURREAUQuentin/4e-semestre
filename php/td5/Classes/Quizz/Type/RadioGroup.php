<?php
declare(strict_types=1);

namespace Quizz\Type;
use Quizz\Form\GenericFormElement;
use Quizz\Type\RadioButton;
class RadioGroup extends GenericFormElement{
    private array $choices = array();

    public function addRadioButton(string $valueRadioButton): void{
        array_push($this->choices, new RadioButton($valueRadioButton));
    }

    public function getRadioButtons(): array{
        return $this->choices;
    }

    public function render(): string{
        $radio = sprintf("<h1>%s<h1/>", $this->getLabel());
        foreach ($this->choices as $choice){
            $radio .= sprintf("<input type='radio' name='%s'/>", $this->getLabel());
            $radio .= sprintf("<label>%s</label><br><br>", $choice->getValue());
        }
        return $radio;
    }
}