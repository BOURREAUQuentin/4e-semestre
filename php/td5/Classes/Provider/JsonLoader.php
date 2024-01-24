<?php
declare(strict_types=1);
namespace Provider;
use Quizz\Type\Quizz;
class JsonLoader
{
    private $data;

    public function __construct($jsonFilePath)
    {
        $jsonString = file_get_contents($jsonFilePath);
        $this->data = json_decode($jsonString, true);
    }

    public function createQuizz(): Quizz{
        $quizz = new Quizz();
        foreach ($this->data as $question) {
            $quizz->addQuestion($question);
        }
        return $quizz;
    }
}