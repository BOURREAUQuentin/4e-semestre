#!/usr/bin/python3
# -*- coding: utf-8 -*-
"""
[Ce bloc est la documentation du module]
Un Tetris avec Pygame.
Ce code est basee sur le code de Sébastien CHAZALLET, auteur du livre "Python 3,
les fondamentaux du language"
"""

__author__ = "Quentin Bourreau"
__copyright__ = "Copyright 2022"
__credits__ = ["Sébastien CHAZALLET", "Vincent NGUYEN", "Quentin Bourreau"]
__license__ = "GPL"
__version__ = "1.0"
__maintainer__ = "Quentin Bourreau"
__email__ = "quentin.bourreau.etu@gmail.com"

# importation des bibliothèques nécessaires pour le jeu Tetris en pygame
import pygame.locals as loc
import random
import time
import pygame
import sys
import constantes as const

class Jeu:
    """
        Cette classe représente le jeu Tetris
    """

    def __init__(self) -> None:
        """
        Initialise la fenêtre du jeu
        """
        pygame.init()
        self.clock = pygame.time.Clock()
        self.surface = pygame.display.set_mode(const.TAILLE_FENETRE)
        self.fonts = {
            "defaut": pygame.font.Font("freesansbold.ttf", 18),
            "titre": pygame.font.Font("freesansbold.ttf", 100),
        }
        pygame.display.set_caption("Application Tetris")

    def start(self) -> None:
        """
        Lance le jeu et affiche l'écran de départ
        """
        self.afficher_texte("Tetris", const.CENTRE_FENETRE, font="titre")
        self.afficher_texte("Appuyer sur une touche...", const.POS)
        self.attente()

    def stop(self) -> None:
        """
        Affiche l'écran de défaite lorsque la partie est perdue
        """
        self.afficher_texte("Perdu", const.CENTRE_FENETRE, font="titre")
        self.attente()
        self.quitter()

    def afficher_texte(self, text : str, position : (int, int), couleur=9, font="defaut") -> None:
        """
        Affiche un texte (données à droite sur le jeu) à l'écran suivant une position définie

        Args:
            text (str): Le texte à afficher
            position (tuple): La position (x, y) où afficher le texte
            couleur (int): La couleur du texte
            font (str): Le nom de la police de caractères
        """
        font = self.fonts.get(font, self.fonts["defaut"])
        couleur = const.COULEURS.get(couleur, const.COULEURS[9])
        rendu = font.render(text, True, couleur)
        rect = rendu.get_rect()
        rect.center = position
        self.surface.blit(rendu, rect)

    def get_event(self) -> None:
        """
        Gère les événements du clavier et la fermeture du jeu
        """
        for event in pygame.event.get():
            if event.type == loc.QUIT:
                self.quitter()
            if event.type == loc.KEYUP:
                if event.key == loc.K_ESCAPE:
                    self.quitter()
            if event.type == loc.KEYDOWN:
                if event.key == loc.K_ESCAPE:
                    continue
                return event.key

    def quitter(self) -> None:
        """
        Quitte (ferme) le jeu
        """
        print("Quitter")
        pygame.quit()
        sys.exit()

    def rendre(self) -> None:
        """
        Rafraîchit la fenêtre du jeu
        """
        pygame.display.update()
        self.clock.tick()

    def attente(self) -> None:
        """
        Gère le système d'attente d'une touche de l'utilisateur 
        (soit au lancement du jeu, soit lorsque le jeu est en pause, soit à la fin de partie)
        """
        print("Attente")
        while self.get_event(
        ) is None:
            self.rendre()

    def get_piece(self) -> None:
        """
        Récupère une pièce du jeu au hasard
        """
        return const.PIECES.get(random.choice(const.PIECES_KEYS))

    def get_current_piece_color(self) -> int:
        """
        Récupère la couleur de la pièce courante
        
        Returns:
            int : la couleur de la pièce courante
        """
        for l in self.current[0]:
            for c in l:
                if c != 0:
                    return c
        return 0

    def calculer_donnees_piece_courante(self) -> None:
        """
        Calcule les données (coordonnées) de la pièce courante
        """
        m = self.current[self.position[2]]
        coords = []
        for index_ligne, ligne in enumerate(m):
            for index_colonne, colonne in enumerate(ligne):
                if colonne != 0:
                    coords.append([index_ligne + self.position[0],
                                   index_colonne + self.position[1]])
        self.coordonnees = coords

    def est_valide(self, x=0, y=0, r=0) -> bool:
        """
        Vérifie si une certaine position (ou rotation) est valide pour la pièce actuelle

        Args:
            x (int): Déplacement horizontal de la pièce
            y (int): Déplacement vertical de la pièce
            r (int): Rotation de la pièce

        Returns:
            bool: True si la position est valide, False sinon
        """
        max_x, max_y = const.DIM_PLATEAU
        if r == 0:
            coordonnees = self.coordonnees
        else:
            m = self.current[(self.position[2] + r) % len(self.current)]
            coords = []
            for index_ligne, ligne in enumerate(m):
                for index_colonne, colonne in enumerate(ligne):
                    if colonne != 0:
                        coords.append(
                            [index_ligne + self.position[0], index_colonne + self.position[1]])
            coordonnees = coords
#			print("Rotation testée: %s" % coordonnees)
        for cx, cy in coordonnees:
            if not 0 <= x + cx < max_x:
                #				print("Non valide en X: cx=%s, x=%s" % (cx, x))
                return False
            elif cy < 0:
                continue
            elif y + cy >= max_y:
                #				print("Non valide en Y: cy=%s, y=%s" % (cy, y))
                return False
            else:
                if self.plateau[cy + y][cx + x] != 0:
                    #					print("Position occupée sur le plateau")
                    return False


#		print("Position testée valide: x=%s, y=%s" % (x, y))
        return True

    def poser_piece(self) -> None:
        """
        Pose la pièce actuelle sur le plateau de jeu
        lorsque la pièce ne peut plus descendre plus bas
        """
        print("La pièce est posée")
        if self.position[1] <= 0:
            self.perdu = True
        # Ajout de la pièce parmi le plateau
        couleur = self.get_current_piece_color()
        for cx, cy in self.coordonnees:
            self.plateau[cy][cx] = couleur
        completees = []
        # calculer les lignes complétées
        for i, line in enumerate(self.plateau[::-1]):
            for case in line:
                if case == 0:
                    break
            else:
                print(self.plateau)
                print(f">>> {const.DIM_PLATEAU[1] - 1 - i}")
                completees.append(const.DIM_PLATEAU[1] - 1 - i)
        lignes = len(completees)
        for i in completees:
            self.plateau.pop(i)
        for i in range(lignes):
            self.plateau.insert(0, [0] * const.DIM_PLATEAU[0])
        # calculer le score et autre
        self.lignes += lignes
        self.score += lignes * self.niveau
        self.niveau = int(self.lignes / 10) + 1
        if lignes >= 4:
            self.tetris += 1
            self.score += self.niveau * self.tetris
        # Travail avec la pièce courante terminé
        self.current = None

    def first(self) -> None:
        """
        Initialise le jeu au début d'une nouvelle partie en réinitialisant le plateau,
        le score, le nombre de pièces, le nombre de lignes complétées,
        le nombre de Tetris, et le niveau
        """
        self.plateau = [[0] * const.DIM_PLATEAU[0] for i in range(const.DIM_PLATEAU[1])]
        self.score, self.pieces, self.lignes, self.tetris, self.niveau = 0, 0, 0, 0, 1
        self.current, self.next_piece, self.perdu = None, self.get_piece(), False

    def next(self) -> None:
        """
        Prépare la pièce suivante à être jouée dans le jeu
        """
        print("Piece suivante")
        self.current, self.next_piece = self.next_piece, self.get_piece()
        self.pieces += 1
        self.position = [int(const.DIM_PLATEAU[0] / 2) - 2, -4, 0]
        self.calculer_donnees_piece_courante()
        self.dernier_mouvement = self.derniere_chute = time.time()

    def gerer_evenements(self) -> None:
        """
        Gère les événements clavier (touches pressées par le joueur) dans le jeu,
        notamment les mouvements de la pièce courante
        """
        event = self.get_event()
        if event == loc.K_p:
            print("Pause")
            self.surface.fill(const.COULEURS.get(0))
            self.afficher_texte("Pause", const.CENTRE_FENETRE, font="titre")
            self.afficher_texte("Appuyer sur une touche...", const.POS)
            self.attente()
        elif event == loc.K_LEFT:
            print("Mouvement vers la gauche")
            if self.est_valide(x=-1):
                self.position[0] -= 1
        elif event == loc.K_RIGHT:
            print("Mouvement vers la droite")
            if self.est_valide(x=1):
                self.position[0] += 1
        elif event == loc.K_DOWN:
            print("Mouvement vers le bas")
            if self.est_valide(y=1):
                self.position[1] += 1
        elif event == loc.K_UP:
            print("Mouvement de rotation")
            if self.est_valide(r=1):
                self.position[2] = (self.position[2] + 1) % len(self.current)
        elif event == loc.K_SPACE:
            # print(f"Mouvement de chute {self.position, self.coordonnees}
            # / {self.position, self.coordonnees}")
            if self.position[1] <= 0:
                self.position[1] = 1
                self.calculer_donnees_piece_courante()
            a = 0
            while self.est_valide(y=a):
                a += 1
            self.position[1] += a - 1
        self.calculer_donnees_piece_courante()

    def gerer_gravite(self) -> None:
        """
        Gère la gravité dans le jeu,
        soit la descente automatique de la pièce courante vers le bas à intervalles réguliers
        """
        if time.time() - self.derniere_chute > 0.35:
            self.derniere_chute = time.time()
            if not self.est_valide():
                print("On est dans une position invalide")
                self.position[1] -= 1
                self.calculer_donnees_piece_courante()
                self.poser_piece()
            elif self.est_valide() and not self.est_valide(y=1):
                self.calculer_donnees_piece_courante()
                self.poser_piece()
            else:
                print("On déplace vers le bas")
                self.position[1] += 1
                self.calculer_donnees_piece_courante()

    def dessiner_plateau(self) -> None:
        """
        Gère l'affichage graphique du plateau de jeu, y compris les informations sur le score,
        le nombre de pièces, le nombre de lignes complétées,
        le nombre de Tetris (quand quatre lignes sont complétées en même temps) et le niveau actuel
        """
        self.surface.fill(const.COULEURS.get(0))
        pygame.draw.rect(self.surface, const.COULEURS[8],
                         const.START_PLABORD + const.TAILLE_PLABORD, const.BORDURE_PLATEAU)
        for index_ligne, ligne in enumerate(self.plateau):
            for index_case, case in enumerate(ligne):
                couleur = const.COULEURS[case]
                position = index_case, index_ligne
                coordonnees = tuple(
                    const.START_PLATEAU[k] + position[k] * const.TAILLE_BLOC[k]
                    for k in range(2)
                )
                pygame.draw.rect(self.surface, couleur,
                                 coordonnees + const.TAILLE_BLOC)
        if self.current is not None:
            for position in self.coordonnees:
                couleur = const.COULEURS.get(self.get_current_piece_color())
                coordonnees = tuple(
                    const.START_PLATEAU[k] + position[k] * const.TAILLE_BLOC[k]
                    for k in range(2)
                )
                pygame.draw.rect(self.surface, couleur,
                                 coordonnees + const.TAILLE_BLOC)
        self.afficher_texte(f"Score: >{self.score}", const.POSITION_SCORE)
        self.afficher_texte(f"Pièces: {self.pieces}", const.POSITION_PIECES)
        self.afficher_texte(f"Lignes: {self.lignes}", const.POSITION_LIGNES)
        self.afficher_texte(f"Tetris: {self.tetris}", const.POSITION_TETRIS)
        self.afficher_texte(f"Niveau: {self.niveau}", const.POSITION_NIVEAU)
        self.rendre()

    def play(self) -> None:
        """
        Gère le déroulement du jeu en continu, c'est-à-dire la boucle principale du jeu Tetris
        """
        print("Jouer")
        self.surface.fill(const.COULEURS.get(0))
        self.first()
        while not self.perdu:  # s'exécute jusqu'à ce que l'utilisateur perde la partie
            if self.current is None:
                self.next()
            self.gerer_evenements()
            self.gerer_gravite()
            self.dessiner_plateau()

if __name__ == "__main__":
    j = Jeu()
    print("Jeu prêt")
    j.start()
    print("Partie démarée")
    j.play()
    print("Partie terminée")
    j.stop()
    print("Arrêt du programme")
