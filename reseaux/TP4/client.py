import socket

BUFSIZE = 1024

def client(host, port):
    sock = socket.socket(type=socket.SOCK_DGRAM)
    sock.setsockopt(socket.SOL_SOCKET,socket.SO_BROADCAST,1)
    addr = (host, port)
    while True:
        txt = "Message de noa :"
        txt += input()
        txt += "\n"
        sock.sendto(txt.encode(), addr)
        data = sock.recv(BUFSIZE)
        print(data.decode(), end="")

client("192.168.28.138", 5556)