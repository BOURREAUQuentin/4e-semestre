<?php
namespace Quizz\Type;
use Quizz\Type\RadioGroup;
use Quizz\Type\Text;
class Quizz{
    private $questions;

    public function __construct(){
        $this->questions = array();
    }

    public function addQuestion(mixed $question): void{
        $typeQuestion = $question["type"];
        $uuidQuestion = $question["uuid"];
        $labelQuestion = $question["label"];
        $correctAnswerQuestion = $question["correct"];
        switch ($typeQuestion) {
            case 'radio':
                $radioQuestion = new RadioGroup($uuidQuestion, $typeQuestion, $labelQuestion, $correctAnswerQuestion);
                $choicesRadioQuestion = $question['choices'];
                foreach ($choicesRadioQuestion as $choice) {
                    $radioQuestion->addRadioButton($choice);
                }
                array_push($this->questions, $radioQuestion);
                break;
            case 'text':
                $textQuestion = new Text($uuidQuestion, $typeQuestion, $labelQuestion, $correctAnswerQuestion);
                array_push($this->questions, $textQuestion);
                break;
        }
    }

    public function getQuestions(): array{
        return $this->questions;
    }
}