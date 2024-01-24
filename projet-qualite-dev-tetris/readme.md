# TP1 - Qualité de développement

## Récupération du repo
1- git clone https://github.com/BUT-info-IUT-d-Orleans/2023-qualitedev-projet01-QuentinBOURREAU.git

2- Entrez le username et le password (personal token) de votre compte GitHub.


## Présentation du projet
Ce projet consiste en la création d'un jeu Tetris en utilisant la bibliothèque Pygame en Python à l'aide d'une classe Tetris.


## Description du jeu Tetris
Le jeu Tetris est un jeu de puzzle dans lequel des pièces géométriques descendent depuis le haut de l'écran. Le joueur doit manipuler et tourner ces pièces pour les agencer en lignes horizontales complètes. Lorsqu'une ligne est entièrement remplie, elle disparaît, permettant ainsi au joueur de marquer des points. L'objectif est de maintenir l'écran de jeu dégagé aussi longtemps que possible tout en accumulant des points. À mesure que le jeu progresse en niveaux, il devient de plus en plus rapide et exigeant, mettant ainsi au défi les compétences du joueur.


## Installation et lancement du jeu

### Installation des dépendances
Avant de lancer le jeu, assurez-vous d'avoir installé les dépendances requises. Vous pouvez le faire en utilisant pip :

    pip install pygame

### Lancement du jeu
Pour lancer le jeu Tetris, suivez ces étapes :

1- Assurez-vous d'être dans le répertoire du projet que vous avez cloné précédemment.

2- Dans un terminal bash, exécutez le script Python tetris.py :

    python3 tetris.py


## Instructions pour jouer
Une fois le jeu lancé, voici les instructions pour jouer au jeu Tetris :

- Utilisez les touches fléchées gauche et droite pour déplacer la pièce actuelle horizontalement.
- Utilisez la touche flèche vers le bas pour accélérer la descente de la pièce.
- Utilisez la touche flèche vers le haut pour effectuer une rotation de la pièce.
- Utilisez la touche Espace pour faire chuter immédiatement la pièce jusqu'en bas.
- Utilisez la touche P pour mettre en pause le jeu.

Votre objectif est de former des lignes complètes avec les pièces pour les faire disparaître. Vous marquerez des points à chaque ligne complète et gagnerez des niveaux au fur et à mesure que vous progresserez.

Amusez-vous bien en jouant au jeu Tetris !


## Formatage du code avec yapf
YAPF (Yet Another Python Formatter) est un outil de formatage de code source Python pour maintenir un code cohérent et bien s'appuyant sur différentes règles de formatage prédéfinies. 

Par conséquent, yapf a utilisé sur mon code une indentation de 4 espaces par niveau d'indentation. Ensuite, il a configuré une longueur maximale des lignes de code en effectuant automatiquement une mise en forme pour la rendre conforme. De plus, il ajoute également des espaces autour des opérateurs binaires, avant et après les parenthèses, etc. Enfin, yapf a aussi mis en forme le code pour aligner les déclarations des méthodes et des blocs (if, while, for).


#
BUT Informatique 2.3.B - BOURREAU Quentin