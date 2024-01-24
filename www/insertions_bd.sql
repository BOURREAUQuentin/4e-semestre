insert into QUIZZ values
(1, "Geographie"),
(2, "Python"),
(3, "Java");

insert into QUESTION values
(1, "Quel est la capitale de la France ?", "Paris"),
(2, "Quel est la capitale de l'Espagne ?", "Madrid"),
(3, "Instancier la variable nombre à 10", "nombre=10"),
(4, "Que fait 10++", "11");

insert into ANSWER values
("Paris", 1),
("Marseille", 1),
("Lyon", 1),
("Barcelone", 2),
("Madrid", 2),
("nombre->10", 3),
("nombre=10", 3);

insert into CONTENIR values
(1, 1),
(1, 2),
(2, 3),
(3, 4);

insert into TYPE_QUESTION values
(1, "radio"),
(2, "text"),
(3, "checkbox");

insert into EST_UNE values
(1, 1),
(1, 2),
(2, 3),
(2, 4);