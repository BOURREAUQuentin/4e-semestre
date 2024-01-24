import socket

def client(port):
    sock = socket.socket(type=socket.SOCK_DGRAM)
    while True:
        for i in range(1,255):
            addr = ("192.168.13."+str(i), port)
            sock.sendto(b"ceci est un test", addr)
            data = sock.recv(128)
            print(data.decode(), end="")

client(5555)