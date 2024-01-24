<?php
require_once("./_connection.php");

class Requetes{
    public static function get_all_quizz($pdo) {
        $sql = <<<EOF
        select * from QUIZZ;
        EOF;
        try{
            $stmt = $pdo->query($sql);
            $rows = $stmt->fetchAll();
            var_dump($rows);
        }
        catch (PDOException $e){
            var_dump($e->getMessage());
        }
    }

    public static function get_all_questions_quizz($pdo, $idQuizz) {
        $sql = <<<EOF
        select * from QUESTION natural join CONTENIR where idQuizz = :id;
        EOF;
        try{
            $stmt = $pdo->prepare($sql);
            $stmt->bindParam("id", $idQuizz, PDO::PARAM_INT);
            var_dump($stmt->debugDumpParams());
            $stmt->execute();
            $questionsQuizz = $stmt->fetch();
            var_dump($questionsQuizz);
        }
        catch (PDOException $e){
            var_dump($e->getMessage());
        }
    }

    public static function get_all_answers_question($pdo, $uuidQuestion) {
        $sql = <<<EOF
        select * from ANSWER natural join QUESTION where uuidQuestion = :id;
        EOF;
        try{
            $stmt = $pdo->prepare($sql);
            $stmt->bindParam("id", $uuidQuestion, PDO::PARAM_INT);
            var_dump($stmt->debugDumpParams());
            $stmt->execute();
            $reponsesQuestion = $stmt->fetch();
            var_dump($reponsesQuestion);
        }
        catch (PDOException $e){
            var_dump($e->getMessage());
        }
    }

    public static function get_correct_answer_question($pdo, $uuidQuestion) {
        $sql = <<<EOF
        select correctAnswerQuestion from QUESTION where uuidQuestion = :id;
        EOF;
        try{
            $stmt = $pdo->prepare($sql);
            $stmt->bindParam("id", $uuidQuestion, PDO::PARAM_INT);
            var_dump($stmt->debugDumpParams());
            $stmt->execute();
            $reponsesQuestion = $stmt->fetch();
            var_dump($reponsesQuestion);
        }
        catch (PDOException $e){
            var_dump($e->getMessage());
        }
    }
}

// Appel de la méthode statique
$pdo = new PDO('sqlite:db.sqlite');
$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
Requetes::get_all_quizz($pdo);