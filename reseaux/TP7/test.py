import socket
import struct

BUFFSIZE = 1024

def server(port):
    sock=socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
    sock.setsockopt(socket.SOL_SOCKET,socket.SO_REUSEADDR,1)
    sock.bind(("224.0.0.4", port))
    mreq= struct.pack("4sl",socket.inet_aton("224.0.0.4"),socket.INADDR_ANY)
    sock.setsockopt(socket.IPPROTO_IP,socket.IP_ADD_MEMBERSHIP,mreq)
    while True:
        data , addr = sock.recvfrom(BUFFSIZE)
        print(data.decode(), end="")

server(5556)