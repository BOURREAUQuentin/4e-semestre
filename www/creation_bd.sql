CREATE TABLE QUIZZ(
   idQuizz INT PRIMARY KEY NOT NULL,
   typeQuizz TEXT NOT NULL
);

CREATE TABLE QUESTION(
   uuidQuestion INT PRIMARY KEY NOT NULL,
   labelQuestion TEXT NOT NULL,
   correctAnswerQuestion TEXT NOT NULL
);

CREATE TABLE ANSWER(
   anwser TEXT NOT NULL,
   uuidQuestion INT NOT NULL,
   PRIMARY KEY (anwser, uuidQuestion),
   FOREIGN KEY (uuidQuestion) REFERENCES QUESTION (uuidQuestion)
);

CREATE TABLE CONTENIR(
    idQuizz INT NOT NULL,
    uuidQuestion INT NOT NULL,
    PRIMARY KEY (idQuizz, uuidQuestion),
    FOREIGN KEY (idQuizz) REFERENCES QUIZZ (idQuizz),
    FOREIGN KEY (uuidQuestion) REFERENCES QUESTION (uuidQuestion)
);

CREATE TABLE TYPE_QUESTION(
    idTypeQuestion INT PRIMARY KEY NOT NULL,
    nomTypeQuestion TEXT NOT NULL
);

CREATE TABLE EST_UNE(
    idTypeQuestion INT NOT NULL,
    uuidQuestion INT NOT NULL,
    PRIMARY KEY (idTypeQuestion, uuidQuestion),
    FOREIGN KEY (idTypeQuestion) REFERENCES TYPE_QUESTION (idTypeQuestion),
    FOREIGN KEY (uuidQuestion) REFERENCES QUESTION (uuidQuestion)
);