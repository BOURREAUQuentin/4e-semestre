import socket

BUFFSIZE = 1024

def server(port):
    sock = socket.socket(type=socket.SOCK_DGRAM)
    sock.bind(("0.0.0.0", port))
    while True:
        data , addr = sock.recvfrom(BUFFSIZE)
        print(data.decode(), end="")

server(5556)