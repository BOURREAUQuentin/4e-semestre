<?php
declare(strict_types=1);

spl_autoload_register(static function(string $fqcn) {
    // $fqcn contient Model\Thread\Message par exemple
    // remplaçons les \ par des / et ajoutons .php à la fin.
    // on obtient Model/Thread/Message.php
    $path = str_replace('\\', '/', $fqcn).'.php';
 
    // puis chargeons le fichier
    require_once('./Classes/'.$path);
});

use Type\TextArea;
use Type\Text;
use Type\Checkbox;
use Type\Hidden;

$text = new Text('myinput', false, 'coucou');
echo $text->render().PHP_EOL;

$checkbox = new Checkbox('mycheckbox', true);
echo $checkbox->render().PHP_EOL;

$hidden = new Hidden('myhidden');
echo $hidden->render().PHP_EOL;

echo new Text('mytexttostring').PHP_EOL;

echo new Textarea('mytextarea', true, 'default value').PHP_EOL;

