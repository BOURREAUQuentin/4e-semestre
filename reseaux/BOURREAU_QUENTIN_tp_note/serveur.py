import socket
import struct

BUFFSIZE = 1024
NOM_REQUETE = 0
NOM_LIVRE = 1
NB_VENTES = 2
LIVRE_1_COMPARAISON = 1
LIVRE_2_COMPARAISON = 2

def serveur(port):
        sock = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
        sock.setsockopt(socket.SOL_SOCKET,socket.SO_REUSEADDR,1)
        sock.bind(("224.0.0.1", port))
        mreq = struct.pack("4sl",socket.inet_aton("224.0.0.1"),socket.INADDR_ANY)
        sock.setsockopt(socket.IPPROTO_IP,socket.IP_ADD_MEMBERSHIP,mreq)
        dico_ventes = {}
        while True:
            data , _ = sock.recvfrom(BUFFSIZE)
            requete = data.decode()
            liste_infos_requete = requete.split(" ")
            if liste_infos_requete[NOM_REQUETE] == "NOUVEAU":
                try:
                    print(ajoute_livre(dico_ventes, liste_infos_requete[NOM_LIVRE]))
                except:
                    print("ERR")
            elif liste_infos_requete[NOM_REQUETE] == "VENTE":
                try:
                    print(ajoute_ventes(dico_ventes, liste_infos_requete[NOM_LIVRE], liste_infos_requete[NB_VENTES]))
                except:
                    print("ERR")
            elif liste_infos_requete[NOM_REQUETE] == "RESULTAT":
                try:
                    print(meilleur_vendeur(dico_ventes))
                except:
                    print("ERR")
            elif liste_infos_requete[NOM_REQUETE] == "COMPARE":
                try:
                    print(get_gagnant_livre(dico_ventes, liste_infos_requete[LIVRE_1_COMPARAISON], liste_infos_requete[LIVRE_2_COMPARAISON]))
                except:
                    print("ERR")
            else:
                print("COMMANDE INTROUVABLE")

def ajoute_livre(dico_ventes, nom_livre):
    """
    Ajoute le livre au dictionnaire s'il n'est déjà pas dedans et retourne OK, sinon ERR

    Args:
        dico_ventes (dict): le dictionnaire des ventes
        nom_livre (str): le nom du livre à ajouter

    Returns:
        str: OK si le livre au dictionnaire s'il n'est déjà pas dedans, sinon ERR
    """
    if nom_livre not in dico_ventes:
        dico_ventes[nom_livre] = 0
        return "OK"
    else:
        return "ERR"

def ajoute_ventes(dico_ventes, nom_livre, nb_ventes):
    """
    Ajoute le nombre de ventes au livre s'il existe dans le dictionnaire et retourne OK, sinon ERR

    Args:
        dico_ventes (dict): le dictionnaire des ventes
        nom_livre (str): le nom du livre à modifier
        nb_ventes (int): le nombre de ventes à ajouter

    Returns:
        str: OK si le livre existe dans le dictionnaire, sinon ERR
    """
    if nom_livre in dico_ventes:
        dico_ventes[nom_livre] += int(nb_ventes)
        return "OK"
    else:
        return "ERR"

def meilleur_vendeur(dico_ventes):
    """
    Retourne le meilleur livre actuel (suivant nombre de ventes)

    Args:
        dico_ventes (dict): le dictionnaire des ventes

    Returns:
        str: le meilleur livre actuel s'il existe des livres dans le dictionnaire, sinon ERR
    """
    if len(dico_ventes) == 0:
        return "ERR"
    else:
        max_ventes_livre = None
        nom_meilleur_vendeur = ""
        for nom_livre, nb_ventes in dico_ventes.items():
            if max_ventes_livre is None or nb_ventes > max_ventes_livre:
                max_ventes_livre = nb_ventes
                nom_meilleur_vendeur = nom_livre
        return "VENDEUR " + nom_meilleur_vendeur

def get_gagnant_livre(dico_ventes, livre_1, livre_2):
    """
    Retourne le gagnant entre deux livres (le meilleur nombre de ventes) si les deux livres sont bien dans le dictionnaire des ventes, sinon ERR

    Args:
        dico_ventes (dict): le dictionnaire des ventes
        livre_1 (str): le premier livre à comparer
        livre_2 (str): le deuxième livre à comparer

    Returns:
        str: le gagnant entre deux livres (le meilleur nombre de ventes) si les deux livres sont bien dans le dictionnaire des ventes, sinon ERR
    """
    if livre_1 in dico_ventes and livre_2 in dico_ventes:
        nb_ventes_livre_1 = dico_ventes[livre_1]
        nb_ventes_livre_2 = dico_ventes[livre_2]
        if nb_ventes_livre_1 > nb_ventes_livre_2:
            return "GAGNANT " + livre_1
        elif nb_ventes_livre_1 < nb_ventes_livre_2:
            return "GAGNANT " + livre_2
        else:
            return "EGALITE"
    else:
        return "ERR"

serveur(5555)