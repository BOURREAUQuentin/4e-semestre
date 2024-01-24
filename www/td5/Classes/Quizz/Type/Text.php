<?php

namespace Quizz\Type;
use Quizz\Form\GenericFormElement;

class Text extends GenericFormElement{
    public function render(): string{
        $radio = sprintf("<h1>%s<h1/>", $this->getLabel());
        $radio .= "<input type='text' required minlength='1' maxlength='1' size='10' />";
        return $radio;
    }
}