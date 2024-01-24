import socket
import datetime

BUFSIZE = 1024
def client(host, port):
    sock = socket.socket(type=socket.SOCK_DGRAM)
    addr = (host, port)
    sock.sendto(b"ceci est un test", addr)
    data = sock.recv(BUFSIZE)
    print(data.decode(), end="")

host = input("Entrer l'adresse IP du serveur : ")
client(host, 5555)