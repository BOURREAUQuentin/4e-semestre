import socket
import struct

BUFFSIZE = 1024

def server(port):
    dico_livres = {}
    sock=socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
    sock.setsockopt(socket.SOL_SOCKET,socket.SO_REUSEADDR,1)
    sock.bind(("224.0.0.4", port))
    mreq= struct.pack("4sl",socket.inet_aton("224.0.0.4"),socket.INADDR_ANY)
    sock.setsockopt(socket.IPPROTO_IP,socket.IP_ADD_MEMBERSHIP,mreq)
    while True:
        data , addr = sock.recvfrom(BUFFSIZE)
        a = data.decode()
        liste_mot = a.split()
        if liste_mot[0] == "NOUVEAU":
            if liste_mot[1] not in dico_livres:
                dico_livres[liste_mot[1]] = 0
                print("OK")
            else:
                print("ERR")
        elif liste_mot[0] == "VENTE":
            if liste_mot[1] in dico_livres:
                dico_livres[liste_mot[1]] += int(liste_mot[2])
                print("OK")
            else:
                print("ERR")
        elif liste_mot[0] == "RESULTAT":
            if len(dico_livres) == 0:
                print("ERR")
            else:
                maxi = 0
                nom = ""
                for livre in dico_livres:
                    if maxi is None or dico_livres[livre] > maxi:
                        maxi = dico_livres[livre]
                        nom = livre
                print("VENDEUR "+nom)
        elif liste_mot[0] == "COMPARE":
            if liste_mot[1] in dico_livres and liste_mot[2] in dico_livres:
                if liste_mot[1] > liste_mot[2]:
                    print("GAGNANT "+liste_mot[1])
                elif liste_mot[1] < liste_mot[2]:
                    print("GAGNANT "+liste_mot[2])
                else:
                    print("EGALITE")
            else:
                print("ERR")
        else:
            print(a, end="")

server(5555)